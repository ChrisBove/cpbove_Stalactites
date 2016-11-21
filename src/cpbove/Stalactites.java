package cpbove;

import heineman.Klondike;
import heineman.klondike.BuildablePileController;
import ks.client.gamefactory.GameWindow;
import ks.common.controller.SolitaireMouseMotionAdapter;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.games.Solitaire;
import ks.common.games.SolitaireUndoAdapter;
import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Pile;
import ks.common.view.BuildablePileView;
import ks.common.view.CardImages;
import ks.common.view.ColumnView;
import ks.common.view.DeckView;
import ks.common.view.IntegerView;
import ks.common.view.PileView;
import ks.launcher.Main;

public class Stalactites extends Solitaire{
	public enum PlayStyle {ONES,TWOS};
	
	Deck deck;
	Column columns[] = new Column [8];
	Pile foundations[] = new Pile [4];
	Pile foundationBases[] = new Pile [4];
	Pile cells[] = new Pile [2];
	PlayStyle playStyle;
	
	// view classes
	ColumnView columnViews[] = new ColumnView [8];
	PileView foundationViews[] = new PileView [4];
	PileView foundationBaseViews[] = new PileView [4];
	PileView cellViews[] = new PileView [2];
	IntegerView scoreView;
	IntegerView numLeftView;
	

	@Override
	public String getName() {
		return "cpbove-Stalactites";
	}

	@Override
	public boolean hasWon() {
		return getScore().getValue() == 48;
	}

	@Override
	public void initialize() {
		// TODO
		// deck of cards to be dealt
		// initialize model
		initializeModel(getSeed());
		initializeView();
		initializeControllers();

		// prep game by dealing to four foundation bases first
		for (int baseNum = 0; baseNum < foundationBases.length; baseNum++) {
			Card c = deck.get();
			
			c.setFaceUp(true);
			foundationBases[baseNum].add(c);
		}
		
		// then deal remaining cards to columns (row by row)
		int remainingCards = deck.count();
		for (int row = 0; row < remainingCards/columns.length; row++){
			for (int col = 0; col < columns.length; col++){
				Card c = deck.get();
				
				c.setFaceUp(true);
				columns[col].add(c);
				
			}
		}

	}

	private void initializeControllers() {
		// Now for each column.
		for (int i = 0; i < columnViews.length; i++) {
			columnViews[i].setMouseAdapter (new StalactiteColumnController (this, columnViews[i]));
			columnViews[i].setMouseMotionAdapter (new SolitaireMouseMotionAdapter (this));
			columnViews[i].setUndoAdapter (new SolitaireUndoAdapter(this));
		}
		
		// now for each cell
		for (int i = 0; i < cellViews.length; i++) {
			cellViews[i].setMouseAdapter (new StalactitesCellController (this, cellViews[i]));
			cellViews[i].setMouseMotionAdapter (new SolitaireMouseMotionAdapter (this));
			cellViews[i].setUndoAdapter (new SolitaireUndoAdapter(this));
		}
		
		// for each foundation
		for (int i = 0; i < foundations.length; i++) {
			foundationViews[i].setMouseAdapter (new StalactitesFoundationController (this, foundationViews[i], foundationBaseViews[i]));
			foundationViews[i].setMouseMotionAdapter (new SolitaireMouseMotionAdapter (this));
			foundationViews[i].setUndoAdapter (new SolitaireUndoAdapter(this));
		}
		
		// Ensure that any releases (and movement) are handled by the non-interactive widgets
		numLeftView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		numLeftView.setMouseAdapter (new SolitaireReleasedAdapter(this));
		numLeftView.setUndoAdapter (new SolitaireUndoAdapter(this));

		// same for scoreView
		scoreView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		scoreView.setMouseAdapter (new SolitaireReleasedAdapter(this));
		scoreView.setUndoAdapter (new SolitaireUndoAdapter(this));
		
		//same for foundation base cards
		for (int i = 0; i < foundationBaseViews.length; i++) {
			foundationBaseViews[i].setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
			foundationBaseViews[i].setMouseAdapter (new SolitaireReleasedAdapter(this));
			foundationBaseViews[i].setUndoAdapter (new SolitaireUndoAdapter(this));
		}

		// Finally, cover the Container for any events not handled by a widget:
		getContainer().setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
		getContainer().setMouseAdapter (new SolitaireReleasedAdapter(this));
		getContainer().setUndoAdapter (new SolitaireUndoAdapter(this));

		
	}

	private void initializeView() {
		// TODO Auto-generated method stub
		CardImages ci = getCardImages();
		
		for (int colNum = 0; colNum < columnViews.length; colNum++) {
			columnViews[colNum] = new ColumnView(columns[colNum]);
			columnViews[colNum].setBounds (20+20*colNum + (colNum)*ci.getWidth(), 2*ci.getHeight() + 70, ci.getWidth(), 8*ci.getHeight());
			container.addWidget (columnViews[colNum]);
		}

		// create foundation views, one after the other.
		for (int foundNum = 0; foundNum < foundationViews.length; foundNum++) {
			foundationViews[foundNum] = new PileView (foundations[foundNum]);
			foundationViews[foundNum].setBounds (20+20*(foundNum+3) + (foundNum+3)*ci.getWidth(), 40+ci.getHeight(), ci.getWidth(), ci.getHeight());
			container.addWidget (foundationViews[foundNum]);
		}

		// create foundation base views, one after the other.
		for (int foundNum = 0; foundNum < foundationBaseViews.length; foundNum++) {
			foundationBaseViews[foundNum] = new PileView (foundationBases[foundNum]);
			foundationBaseViews[foundNum].setBounds (20+20*(foundNum+3) + (foundNum+3)*ci.getWidth(), 20, ci.getWidth(), ci.getHeight());
			container.addWidget (foundationBaseViews[foundNum]);
		}
		
		// create cell views, one after the other.
		for (int cellNum = 0; cellNum < cellViews.length; cellNum++) {
			cellViews[cellNum] = new PileView (cells[cellNum]);
			cellViews[cellNum].setBounds (20+20*cellNum + cellNum*ci.getWidth(), 40+ci.getHeight(), ci.getWidth(), ci.getHeight());
			container.addWidget (cellViews[cellNum]);
		}

		scoreView = new IntegerView (getScore());
		scoreView.setFontSize (24);
		scoreView.setBounds (20, 20, 2*ci.getWidth() + 20, ci.getHeight()/3);
		container.addWidget (scoreView);

		numLeftView = new IntegerView (getNumLeft());
		numLeftView.setFontSize (24);
		numLeftView.setBounds (20, 40+ci.getHeight()/2, 2*ci.getWidth() + 20, ci.getHeight()/3);
		container.addWidget (numLeftView);
		
	}

	private void initializeModel(int seed) {
		deck = new Deck("deck");
		deck.create(seed);
		model.addElement (deck);   // add to our model (as defined within our superclass).

		for (int i = 0; i < columns.length; i++){
			columns[i] = new Column("Column"+i);
			model.addElement(columns[i]);
		}
		
		
		// label these as foundations
		for (int i = 0; i < foundations.length; i++){
			foundations[i] = new Pile("Foundation"+i);
			model.addElement(foundations[i]);
		}

		// label these as first card foundations
		for (int i = 0; i < foundationBases.length; i++){
			foundationBases[i] = new Pile("FirstCardFoundation%2d"+i);
			model.addElement(foundationBases[i]);
		}


		for (int i = 0; i < cells.length; i++){
			cells[i] = new Pile("Cell"+i);
			model.addElement(cells[i]);
		}
		
		updateScore(0);
		updateNumberCardsLeft (48);
		
		playStyle = PlayStyle.ONES; // TODO default for now, need to let player pick
		
	}
	
	/** Code to launch solitaire variation. */
	public static void main (String []args) {
		// Seed is to ensure we get the same initial cards every time.
		// Here the seed is to "order by suit."
		GameWindow gw = Main.generateWindow(new Stalactites(), Deck.OrderBySuit);
		//gw.setSkin(SkinCatalog.MULTIPLE_BOUNCING_BALLS);

	}

}
