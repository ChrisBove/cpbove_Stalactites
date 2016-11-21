package cpbove;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;

public class TestCellToFoundationMove extends TestCase {
	public void testMoveToEmpty() {
		Stalactites stalactites = new Stalactites();
		GameWindow gw = Main.generateWindow(stalactites, Deck.OrderBySuit);
		
		Card topCardValue = stalactites.columns[7].peek();
		Card topCard = stalactites.columns[7].get();
		
		TableauToCellMove toCellMove = new TableauToCellMove(stalactites.columns[7], topCard, stalactites.cells[0]);
		assertTrue(toCellMove.valid(stalactites));
		
		assertTrue(toCellMove.doMove(stalactites));
		
		Card fromCell = stalactites.cells[0].get();
		StackToFoundationMove move = new StackToFoundationMove(stalactites.cells[0], fromCell, stalactites.foundations[0], stalactites.foundationBases[0]);

		assertTrue(move.valid(stalactites));
		
		assertTrue(move.doMove(stalactites));
		
		assertEquals(1, stalactites.foundations[0].count());
		assertEquals(5, stalactites.columns[7].count());
		assertEquals(topCardValue, stalactites.foundations[0].peek());
		int numLeft = stalactites.getNumLeft().getValue();
		assertEquals(47, numLeft);
		int score = stalactites.getScoreValue();
		assertEquals(1, score);
		
		// undo the move
		move.undo(stalactites);
		assertEquals(0, stalactites.foundations[0].count());
		assertEquals(1, stalactites.cells[0].count());
		assertEquals(5, stalactites.columns[7].count());
		assertEquals(topCardValue, stalactites.cells[0].peek());
		numLeft = stalactites.getNumLeft().getValue();
		assertEquals(47, numLeft);
	}
	
	public void testInvalidMoveToEmpty() {
		Stalactites stalactites = new Stalactites();
		GameWindow gw = Main.generateWindow(stalactites, Deck.OrderBySuit);
		
		Card topCardValue = stalactites.columns[0].peek();
		Card topCard = stalactites.columns[0].get();
		
		TableauToCellMove toCellMove = new TableauToCellMove(stalactites.columns[0], topCard, stalactites.cells[0]);
		
		assertTrue(toCellMove.valid(stalactites));
		
		assertTrue(toCellMove.doMove(stalactites));
		
		Card fromCell = stalactites.cells[0].get();
		StackToFoundationMove move = new StackToFoundationMove(stalactites.cells[0], fromCell, stalactites.foundations[0], stalactites.foundationBases[0]);

		assertFalse(move.valid(stalactites));
		
		assertFalse(move.doMove(stalactites));
		
		assertEquals(0, stalactites.foundations[0].count());
		// return the card to the column
		stalactites.cells[0].add(fromCell);
		assertEquals(5, stalactites.columns[0].count());
		assertEquals(topCardValue, stalactites.cells[0].peek());
		int numLeft = stalactites.getNumLeft().getValue();
		assertEquals(47, numLeft);
		int score = stalactites.getScoreValue();
		assertEquals(1, score);
	}
	
	// test moving onto non-empty foundation
	public void testConsequetiveMoves() {
		Stalactites stalactites = new Stalactites();
		GameWindow gw = Main.generateWindow(stalactites, Deck.OrderBySuit);
		
		// put a bunch of cards into the foundations from the tableau
		for (int col = 7; col >=0; col-- ) {
			Card topCard = stalactites.columns[col].get();

			StackToFoundationMove move = new StackToFoundationMove(stalactites.columns[col], topCard, stalactites.foundations[0], stalactites.foundationBases[0]);

			assertTrue(move.valid(stalactites));

			assertTrue(move.doMove(stalactites));
		}
		for (int col = 7; col >=5; col-- ) {
			Card topCard = stalactites.columns[col].get();

			StackToFoundationMove move = new StackToFoundationMove(stalactites.columns[col], topCard, stalactites.foundations[0], stalactites.foundationBases[0]);

			assertTrue(move.valid(stalactites));

			assertTrue(move.doMove(stalactites));
		}
		
		// add card to cell
		Card topCard = stalactites.columns[4].get();
		TableauToCellMove toCellMove = new TableauToCellMove(stalactites.columns[4], topCard, stalactites.cells[0]);
		assertTrue(toCellMove.doMove(stalactites));
		
		// try moving to foundation
		Card fromCell = stalactites.cells[0].get();
		StackToFoundationMove move = new StackToFoundationMove(stalactites.cells[0], fromCell, stalactites.foundations[0], stalactites.foundationBases[0]);

		assertTrue(move.valid(stalactites));

		assertTrue(move.doMove(stalactites));
		
		
		// try adding one more card than possible
		// add card to cell
		topCard = stalactites.columns[3].get();
		toCellMove = new TableauToCellMove(stalactites.columns[3], topCard, stalactites.cells[0]);
		assertTrue(toCellMove.doMove(stalactites));
		
		// try moving to foundation
		fromCell = stalactites.cells[0].get();
		move = new StackToFoundationMove(stalactites.cells[0], fromCell, stalactites.foundations[0], stalactites.foundationBases[0]);

		assertFalse(move.valid(stalactites));

		assertFalse(move.doMove(stalactites));
		
	}
	
}
