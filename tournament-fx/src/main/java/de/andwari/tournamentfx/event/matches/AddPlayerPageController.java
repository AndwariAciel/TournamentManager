package de.andwari.tournamentfx.event.matches;

import java.util.stream.Collectors;

import javax.inject.Inject;

import de.andwari.tournamentcore.event.entity.Event;
import de.andwari.tournamentcore.event.entity.Standing;
import de.andwari.tournamentcore.player.boundary.PlayerService;
import de.andwari.tournamentcore.player.entity.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AddPlayerPageController {

	@FXML
	private Button btnAdd, btnCancel;

	@FXML
	private TextField tfSearch;

	@FXML
	private ListView<Player> listPlayers;
	private ObservableList<Player> listOfPlayers;

	@Inject
	private PlayerService playerService;
	
	private MatchesPageController matchesPageController;

	public void init(Event event, MatchesPageController controller) {
		this.matchesPageController = controller;
		listOfPlayers = FXCollections.observableArrayList(playerService.getListOfAllPlayers());
		listOfPlayers.removeAll(event.getRankings().stream().map(Standing::getPlayer).collect(Collectors.toList()));
		FilteredList<Player> filteredList = new FilteredList<>(listOfPlayers);
		SortedList<Player> sortedList = new SortedList<>(filteredList);
		sortedList.setComparator((p1, p2) -> p1.getPlayerName().compareTo(p2.getPlayerName()));

		tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredList.setPredicate(player -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (player.getPlayerName().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (player.getDci() != null && player.getDci().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});

		listPlayers.setItems(sortedList);
		listPlayers.setCellFactory(new Callback<ListView<Player>, ListCell<Player>>() {

			@Override
			public ListCell<Player> call(ListView<Player> param) {
				ListCell<Player> cell = new ListCell<Player>() {

					@Override
					protected void updateItem(Player item, boolean empty) {
						super.updateItem(item, empty);
						if (!empty) {
							setText(item.getPlayerName());
						} else {
							setText(null);
						}
					}
				};
				return cell;
			}
		});
	}

	public void addPlayer() {
		Player player = listPlayers.getSelectionModel().getSelectedItem();
		matchesPageController.addPlayerToEvent(player);
		Stage stage = (Stage) btnAdd.getScene().getWindow();
		stage.close();
	}
	
	public void cancel() {
		Stage stage = (Stage) btnCancel.getScene().getWindow();
		stage.close();
	}

}
