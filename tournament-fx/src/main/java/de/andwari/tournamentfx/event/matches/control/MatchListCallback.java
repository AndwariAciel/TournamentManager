package de.andwari.tournamentfx.event.matches.control;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import de.andwari.tournamentfx.event.matches.MatchListCellView;
import de.andwari.tournamentfx.event.matches.dvos.MatchListDvo;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class MatchListCallback implements Callback<ListView<MatchListDvo>, ListCell<MatchListDvo>> {

	@Inject
	private Instance<MatchListCellView> cell;
	
    @Override
    public ListCell<MatchListDvo> call(ListView<MatchListDvo> studentListView) {
        return cell.get();
    }
}
