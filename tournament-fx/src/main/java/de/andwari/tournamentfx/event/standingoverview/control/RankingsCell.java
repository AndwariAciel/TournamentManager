package de.andwari.tournamentfx.event.standingoverview.control;

import de.andwari.tournamentfx.event.rankings.dvos.RankingsDvo;
import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;

public class RankingsCell extends TableCell<RankingsDvo, String> {

	@Override
	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
			setText(null);
			return;
		}
		setText(item);
		RankingsDvo ranking = getTableRow().getItem();
		if (ranking != null && ranking.isDropped()) {
			setTextFill(Color.GRAY);
		} else if (ranking != null) {
			setTextFill(Color.BLACK);
		}
	}

}
