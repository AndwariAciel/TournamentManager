package de.andwari.tournamentfx.event.matches;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import de.andwari.tournamentfx.event.matches.dvos.MatchListDvo;
import de.andwari.tournamentfx.util.FxmlResource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class MatchListCellView extends ListCell<MatchListDvo> {

	public MatchListCellView() {

	}

	@FXML
	private Label player1;

	@FXML
	private Label player2;

	@FXML
	private Label scoreP1;

	@FXML
	private Label scoreP2;
	
	@FXML
	private Label status;

	@FXML
	private HBox hbox;

	@Inject
	private FXMLLoader loader;
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private static final String STATUS_PLAYING = "Playing";
	private static final String STATUS_FINISHED = "Finished";
	private static final String DEFAULT_SCORE = "-";

	@Override
	protected void updateItem(MatchListDvo matchItem, boolean empty) {
		super.updateItem(matchItem, empty);

		if (empty || matchItem == null) {
			setText(null);
			setGraphic(null);
		} else {

			try {
				loader.setLocation(FxmlResource.EVENT_MATCHES_MATCH_LIST.getResourceUrl());
				loader.setController(this);
				Object root = loader.getRoot();
				if (root == null) {
					loader.load();
				}
			} catch (IOException e) {
				logger.error(e);
			}

			player1.setText(matchItem.getPlayer1());
			player2.setText(matchItem.getPlayer2());
			if (!matchItem.isFinished()) {
				status.setText(STATUS_PLAYING);
				scoreP1.setText(DEFAULT_SCORE);
				scoreP2.setText(DEFAULT_SCORE);
				setColor(Color.BLACK);
			} else {
				status.setText(STATUS_FINISHED);
				scoreP1.setText(Integer.toString(matchItem.getWinsPlayer1()));
				scoreP2.setText(Integer.toString(matchItem.getWinsPlayer2()));
				setColor(Color.GREY);
			}
			setText(null);
			setGraphic(hbox);
		}
	}

	private void setColor(Color color) {
		scoreP1.setTextFill(color);
		scoreP2.setTextFill(color);
		player1.setTextFill(color);
		player2.setTextFill(color);
	}

}
