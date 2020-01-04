package de.andwari.tournamentcore.matchmaking;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import de.andwari.tournamentcore.event.entity.Event;
import de.andwari.tournamentcore.event.entity.Match;
import de.andwari.tournamentcore.event.entity.MatchResult;
import de.andwari.tournamentcore.event.entity.Round;
import de.andwari.tournamentcore.event.entity.Standing;
import de.andwari.tournamentcore.matchmaking.combinator.PairingsCombinator;
import de.andwari.tournamentcore.player.entity.Player;
import de.andwari.tournamentcore.standings.StandingService;

public class Decider {

	private Event event;

	public ArrayList<Decision> decisions = new ArrayList<>();

	public ArrayList<Pool> pools = new ArrayList<>();

	private ArrayList<Player> unmatchedPlayers = new ArrayList<>();

	int currentDecision = 0;

	private boolean finished = false;

	@Inject
	private PairingsCombinator combinator;

	@Inject
	private StandingService standingService;

	@Inject
	private PossibilityRater rater;

	public boolean isFinished() {
		return finished;
	}

	public boolean makeDecision() {
		try {
			Decision decision = null;
			if (decisions.size() == currentDecision) {
				decision = createNewDecision();
				decisions.add(decision);
			} else {
				decision = decisions.get(currentDecision);
			}

			if (decision.selectNext()) {
				currentDecision++;
				unmatchedPlayers = decision.getUnmatchedPlayers();
			} else {
				unmatchedPlayers.clear();
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			unmatchedPlayers.clear();
			return false;
		}
		if (pools.size() == decisions.size()) {
			if (!resultIsValid()) {
				currentDecision--;
				return false;
			}
			finished = true;
		}
		return true;
	}

	private boolean resultIsValid() {
		return unmatchedPlayers.size() < 2;
	}

	private Decision createNewDecision() {
		Pool tmpPool = addPlayersToPool(pools.get(currentDecision));
		calculateAllPossibilities(tmpPool, event);
		Decision decision = new Decision(rater.ratePossibilities(tmpPool, unmatchedPlayers));
		return decision;
	}

	public void revokePreviousDecision() {
		decisions.remove(currentDecision);
		currentDecision--;
	}

	public Round getRound() {
		Round round = new Round();
		round.setEvent(event);
		round.setMatches(new ArrayList<>());
		decisions.stream().forEach(d -> round.getMatches().addAll(d.getDecision().matches));
		round.getMatches().stream().forEach(m -> m.setRound(round));
		if (unmatchedPlayers.size() == 1) {
			round.setBye(unmatchedPlayers.get(0));
		}
		return round;
	}

	private Pool addPlayersToPool(Pool pool) {
		Pool poolWithUnmatchedPlayers = new Pool(pool);
		poolWithUnmatchedPlayers.poolOfPlayers.addAll(unmatchedPlayers);
		return poolWithUnmatchedPlayers;
	}

	private void calculateAllPossibilities(Pool pool, Event event) {
		for (Match[] matches : combinator.getAllPairings(pool.poolOfPlayers)) {
			pool.possibilities.add(createPosibility(matches, event));
		}
		checkForWorstCase(pool);
	}

	private void checkForWorstCase(Pool pool) {
		Optional<Possibility> worstCase = pool.possibilities.stream()
				.filter(p -> p.unmatchedPlayers.size() == pool.poolOfPlayers.size()).findAny();
		if(!worstCase.isPresent()) {
			Possibility possibility = new Possibility();
			possibility.unmatchedPlayers = new ArrayList<>(pool.poolOfPlayers);
			pool.possibilities.add(possibility);
		}
	}

	private Possibility createPosibility(Match[] matches, Event event) {
		Possibility possibility = new Possibility();
		for (Match match : matches) {
			if (haveNotYetPlayed(match, event) && !match.isBye()) {
				possibility.matches.add(match);
			} else if (match.isBye()) {
				possibility.unmatchedPlayers.add(match.getPlayer1());
			} else {
				possibility.unmatchedPlayers.add(match.getPlayer1());
				possibility.unmatchedPlayers.add(match.getPlayer2());
			}
		}
		return possibility;
	}

	private boolean haveNotYetPlayed(Match match, Event event) {
		for (MatchResult result : standingService.getStandingForPlayer(match.getPlayer1(), event).getPlayedMatches()) {
			if (result.isBye()) {
				continue;
			}
			if (result.getOpponent().equals(match.getPlayer2())) {
				return false;
			}
		}
		return true;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	/*
	 * Creates this decider by calculating the possible pools for this round.
	 */
	public void create(Event event) {
		setEvent(event);
		ArrayList<Standing> orderedStandings = standingService.getRankings(event.getRankings());
		orderedStandings = (ArrayList<Standing>) orderedStandings.stream().filter(s -> !s.getDropped())
				.collect(Collectors.toList());
		Pool pool = new Pool(orderedStandings.get(0).getScore(), event);
		for (Standing standing : orderedStandings) {
			if (standing.getScore() != pool.scorePool) {
				this.pools.add(pool);
				pool = new Pool(standing.getScore(), event);
			}
			pool.poolOfPlayers.add(standing.getPlayer());
		}
		this.pools.add(pool);
	}

}
