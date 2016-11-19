package cpbove;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;

public class TestTableauToCellMove extends TestCase {
	public void testMove() {
		Stalactites stalactites = new Stalactites();
		GameWindow gw = Main.generateWindow(stalactites, Deck.OrderBySuit);
		
		Card topCard = stalactites.columns[0].peek();
		
		TableauToCellMove t2CM = new TableauToCellMove(stalactites.columns[0], stalactites.cells[0]);
		
		assertTrue(t2CM.valid(stalactites));
		
		assertTrue(t2CM.doMove(stalactites));
		
		assertEquals(1, stalactites.cells[0].count());
		assertEquals(5, stalactites.columns[0].count());
		assertEquals(topCard, stalactites.cells[0].peek());
		int numLeft = stalactites.getNumLeft().getValue();
		assertEquals(48, numLeft);
		
		// undo the move
		t2CM.undo(stalactites);
		assertEquals(0, stalactites.cells[0].count());
		assertEquals(6, stalactites.columns[0].count());
		assertEquals(topCard, stalactites.columns[0].peek());
		assertEquals(48, numLeft);
	}
	
	public void testInvalid() {
		//check if move does not work on occupied cell
		Stalactites stalactites = new Stalactites();
		GameWindow gw = Main.generateWindow(stalactites, Deck.OrderBySuit);
		
		// place one card
		Card topCard = stalactites.columns[0].peek();
		
		TableauToCellMove t2CM = new TableauToCellMove(stalactites.columns[0], stalactites.cells[0]);
		assertTrue(t2CM.valid(stalactites));
		
		assertTrue(t2CM.doMove(stalactites));
		// try placing another
		Card secondCard = stalactites.columns[0].peek();

		TableauToCellMove t2CM_2 = new TableauToCellMove(stalactites.columns[0], stalactites.cells[0]);
		assertFalse(t2CM_2.valid(stalactites));

		assertFalse(t2CM_2.doMove(stalactites));

		assertEquals(topCard, stalactites.cells[0].peek());
		assertEquals(secondCard, stalactites.columns[0].peek());
		
		
		assertEquals(1, stalactites.cells[0].count());
		assertEquals(5, stalactites.columns[0].count());
		
		int numLeft = stalactites.getNumLeft().getValue();
		assertEquals(48, numLeft);
	}
}
