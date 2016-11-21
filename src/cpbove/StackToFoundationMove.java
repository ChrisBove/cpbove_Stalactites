package cpbove;

import cpbove.Stalactites.PlayStyle;
import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.model.Stack;

public class StackToFoundationMove extends Move{
	Stack src;
	Pile foundation;
	Pile foundationBase;
	Card draggedCard;
	static PlayStyle style = PlayStyle.UNKOWN;
	boolean initializedStyle;
	
	public StackToFoundationMove(Stack src, Card draggedCard, Pile foundation, Pile foundationBase) {
		this.src = src;
		this.foundation = foundation;
		this.draggedCard = draggedCard;
		this.foundationBase = foundationBase;
		initializedStyle = false;
	}
	
	// HACK override constructor to reset static class variables to deal with new hand
	// never ever try any methods on this object if you do this.
	public StackToFoundationMove() {
		this.src = null;
		this.foundation = null;
		this.draggedCard = null;
		this.foundationBase = null;
		initializedStyle = false;
		style = PlayStyle.UNKOWN;
	}

	@Override
	public boolean doMove(Solitaire game) {
		// determine play style if unknown
		if (style == PlayStyle.UNKOWN) {
			// try setting to ones
			style = PlayStyle.ONES;
			if(valid(game)){
				initializedStyle = true;
			}
			// try setting to twos
			else{
				style = PlayStyle.TWOS;
				if(valid(game)){
					initializedStyle = true;
				}
				else {
					// didn't work. return false
					style = PlayStyle.UNKOWN;
					return false;
				}
			}
		}
		else if (!valid(game)){
			return false;
		}
		
		foundation.add(draggedCard);
		if(src instanceof Column){
			game.updateScore(1);
			game.updateNumberCardsLeft(-1);
		}
		
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		Card c = foundation.get();
		src.add(c);
		if(src instanceof Column){
			game.updateScore(-1);
			game.updateNumberCardsLeft(1);
		}
		
		// handle the style switch
		if(initializedStyle){
			style = PlayStyle.UNKOWN;
			initializedStyle = false;
		}
		
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
		else if (style == PlayStyle.ONES)
			return compareVal == 1 || compareVal == -12;
		else
			System.err.println("StackToFoundationMove: Unknown PlayStyle");
		return false;
	}
}