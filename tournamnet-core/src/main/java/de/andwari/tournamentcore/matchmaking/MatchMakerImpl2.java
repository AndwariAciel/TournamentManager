package de.andwari.tournamentcore.matchmaking;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import de.andwari.tournamentcore.event.entity.Event;
import de.andwari.tournamentcore.event.entity.Round;

public class MatchMakerImpl2 implements MatchMaker {

	@Inject
	private Instance<Decider> deciderInstance;

	@Override
	public Round createMatches(Event event) {
		Decider decider = deciderInstance.get();
		decider.create(event);
		while (!decider.isFinished()) {
			if (!decider.makeDecision()) {
				decider.revokePreviousDecision();
			}
		}
		return decider.getRound();
	}

}
