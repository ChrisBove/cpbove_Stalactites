package cpbove;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;

/**
 * Move card from top of column to cell
 * Moves the dragged top card on a column to the destination cell
pile if the cell is currently empty. Score and remaining cards numbers do not change.
 * @author Chris B
 *
 */
public class TableauToCellMove extends Move{
	Column column;
	Pile cell;
	Card draggedCard;
	
	public TableauToCellMove(Column column, Card draggedCard, Pile cell) {
		this.column = column;
		this.cell = cell;
		this.draggedCard = draggedCard;
	}

	@Override
	public boolean doMove(Solitaire game) {
		if (!valid(game)){
			return false;
		}
		
		cell.add(draggedCard);
		game.updateScore(1);
		game.updateNumberCardsLeft(-1);
		
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		Card c = cell.get();
		column.add(c);
		game.updateNumberCardsLeft(1);
		game.updateScore(-1);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		return !column.empty() && cell.empty();
	}

}
