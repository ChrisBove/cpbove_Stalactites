package cpbove;

import java.awt.event.MouseEvent;

import heineman.klondike.FlipCardMove;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.BuildablePile;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;
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

		// Get a column of cards to move from the BuildablePileView
		// Note that this method will alter the model for BuildablePileView if the condition is met.
		ColumnView colView = columnView.getColumnView (me);

		// an invalid selection (either all facedown, or not in faceup region)
		if (colView == null) {
			return;
		}

		// Check conditions
		Column col = (Column) colView.getModelElement();
		if (col == null) {
			System.err.println ("BuildablePileController::mousePressed(): Unexpectedly encountered a ColumnView with no Column.");
			return; // sanity check, but should never happen.
		}

		// If we get here, then the user has indeed clicked on the top card in the PileView and
		// we are able to now move it on the screen at will. For smooth action, the bounds for the
		// cardView widget reflect the original card location on the screen.
		Widget w = c.getActiveDraggingObject();
		if (w != Container.getNothingBeingDragged()) {
			System.err.println ("BuildablePileController::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
			return;
		}

		// Tell container which object is being dragged, and where in that widget the user clicked.
		c.setActiveDraggingObject (colView, me);

		// Tell container which BuildablePileView is the source for this drag event.
		c.setDragSource (columnView);

		// we simply redraw our source pile to avoid flicker,
		// rather than refreshing all widgets...
		columnView.redraw();
	}
}
