package cpbove;

import cpbove.Stalactites.PlayStyle;
import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;

public class CellToFoundationMove extends Move{
	Pile cell;
	Pile foundation;
	Pile foundationBase;
	Card draggedCard;
	PlayStyle style;
	
	public CellToFoundationMove(Pile cell, Card draggedCard, Pile foundation, Pile foundationBase, PlayStyle style) {
		this.cell = cell;
		this.foundation = foundation;
		this.draggedCard = draggedCard;
		this.foundationBase = foundationBase;
		this.style = style;
	}

	@Override
	public boolean doMove(Solitaire game) {
		if (!valid(game)){
			return false;
		}
		
		foundation.add(draggedCard);
		game.updateScore(1);
		game.updateNumberCardsLeft(-1);
		
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		Card c = foundation.get();
		cell.add(c);
		game.updateNumberCardsLeft(1);
		game.updateScore(-1);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		// 13-1 since one card is on the base pile
		if(foundation.count() == 12)
			return false;
		// if foundation is empty, check against the base foundation
		int compareVal; //If zero, then cards have the same rank. If negative, existing card lesser than target by that amount; similar for positive results.
		
		if(foundation.empty())
			compareVal = draggedCard.compareTo(foundationBase.peek());
		else
			compareVal = draggedCard.compareTo(foundation.peek());
		
		// remember the wrap around from K to A or Q to A
		if(style == PlayStyle.TWOS)
			return compareVal == 2 || compareVal == -11;
		else
			return compareVal == 1 || compareVal == -12;
	}
}