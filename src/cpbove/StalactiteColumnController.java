package cpbove;

import java.awt.event.MouseEvent;

import heineman.klondike.FlipCardMove;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.CardView;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.Widget;

public class StalactiteColumnController extends SolitaireReleasedAdapter {
	/** The game. */
	protected Stalactites theGame;

	/** The Column of interest. */
	protected ColumnView columnView;

	/**
	 * KlondikeDeckController constructor comment.
	 */
	public StalactiteColumnController(Stalactites theGame, ColumnView columnView) {
		super(theGame);

		this.theGame = theGame;
		this.columnView = columnView;
	}

	/**
	 * Coordinate reaction to the beginning of a Drag Event.
	 */
	public void mousePressed(MouseEvent me) {
		// The container manages several critical pieces of information; namely, it
		// is responsible for the draggingObject; in our case, this would be a CardView
		// Widget managing the card we are trying to drag between two piles.
		Container c = theGame.getContainer();

		/** Return if there is no card to be chosen. */
		Column theColumn = (Column) columnView.getModelElement();
		if (theColumn.count() == 0) {
			return;
		}

		// Get the card to move from the column
		// Note that this method will alter the model for ColumnView if the condition is met.
		CardView cardView = columnView.getCardViewForTopCard(me);

		// an invalid selection
		if (cardView == null) {
			return;
		}

		// Check conditions
		Card card = (Card) cardView.getModelElement();
		if (card == null) {
			System.err.println ("ColumnController::mousePressed(): Unexpectedly encountered a CardView with no Card.");
			return; // sanity check, but should never happen.
		}

		// If we get here, then the user has indeed clicked on the top card in the PileView and
		// we are able to now move it on the screen at will. For smooth action, the bounds for the
		// cardView widget reflect the original card location on the screen.
		Widget w = c.getActiveDraggingObject();
		if (w != Container.getNothingBeingDragged()) {
			System.err.println ("ColumnController::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
			return;
		}

		// Tell container which object is being dragged, and where in that widget the user clicked.
		c.setActiveDraggingObject (cardView, me);

		// Tell container which ColumnView is the source for this drag event.
		c.setDragSource (columnView);

		// we simply redraw our source pile to avoid flicker,
		// rather than refreshing all widgets...
		columnView.redraw();
	}
}
