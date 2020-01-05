package de.andwari.tournamentfx.event.standingoverview;

import java.util.ArrayList;

import javax.inject.Inject;

import de.andwari.tournamentcore.event.entity.Round;
import de.andwari.tournamentcore.exception.HasOngoingMatchException;
import de.andwari.tournamentfx.event.matches.MatchesPageController;
import de.andwari.tournamentfx.event.matches.control.MatchesPageService;
import de.andwari.tournamentfx.event.rankings.converter.RankingsDvoConverter;
import de.andwari.tournamentfx.event.rankings.dvos.RankingsDvo;
import de.andwari.tournamentfx.event.standingoverview.control.RankingsCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class StandingOverviewController {

	@FXML
	private TableColumn<RankingsDvo, String> colRank, colPlayer, colScore, colOpscore, colGamescore, colOpGamescore;

	@FXML
	private TableView<RankingsDvo> tableStandings;

	@FXML
	private Button btnOk, btnDrop;

	private ObservableList<RankingsDvo> listOfStandings;

	private Round round;

	@Inject
	private MatchesPageService pageService;

	@Inject
	private RankingsDvoConverter converter;
	
	private MatchesPageController matchesPageController;

	public void initialize(Round round, MatchesPageController matchesPageController) {
		this.matchesPageController = matchesPageController;
		this.round = round;
		listOfStandings = FXCollections.observableArrayList();
		tableStandings.setItems(listOfStandings);
		initColumns();
		initList();
	}

	private void initColumns() {
		colRank.setCellValueFactory(cellData -> cellData.getValue().getRankProperty());
		colRank.setCellFactory(cb -> new RankingsCell());
		colPlayer.setCellValueFactory(cellData -> cellData.getValue().getPlayerProperty());
		colPlayer.setCellFactory(cb -> new RankingsCell());
		colScore.setCellValueFactory(cellData -> cellData.getValue().getScoreProperty());
		colScore.setCellFactory(cb -> new RankingsCell());
		colOpscore.setCellValueFactory(cellData -> cellData.getValue().getOpMatchScoreProperty());
		colOpscore.setCellFactory(cb -> new RankingsCell());
		colGamescore.setCellValueFactory(cellData -> cellData.getValue().getGameScoreProperty());
		colGamescore.setCellFactory(cb -> new RankingsCell());
		colOpGamescore.setCellValueFactory(cellData -> cellData.getValue().getOpGamesScoreProperty());
		colOpGamescore.setCellFactory(cb -> new RankingsCell());

		colRank.setSortable(false);
		colPlayer.setSortable(false);
		colScore.setSortable(false);
		colOpscore.setSortable(false);
		colOpGamescore.setSortable(false);
		colGamescore.setSortable(false);
		colOpGamescore.setSortable(false);
	}

	private void initList() {
		listOfStandings.removeAll(listOfStandings);
		ArrayList<RankingsDvo> rankings = pageService.getRankings(round.getEvent());
		rankings.stream().forEach(dvo -> listOfStandings.add(dvo));
	}

	public void dropPlayer() {
		int index = tableStandings.getSelectionModel().getSelectedIndex();
		if (index < 0)
			return;
		RankingsDvo dvo = listOfStandings.get(index);
		try {
			pageService.dropPlayer(converter.convertToEntity(dvo, round));
			dvo.setDropped(true);
			listOfStandings.set(index, dvo);
			matchesPageController.updateRankingList();
		} catch (HasOngoingMatchException ex) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("unfinished Match");
			alert.setHeaderText("Warning");
			alert.setContentText("Cannot drop this player, finish his open match first!");
			alert.showAndWait();
		}
	}

	public void closeWindow() {
		Stage stage = (Stage) btnOk.getScene().getWindow();
		stage.close();
	}
}
