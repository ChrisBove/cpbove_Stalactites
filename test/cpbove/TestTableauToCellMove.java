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
		
		Card topCardValue = stalactites.columns[0].peek();
		Card topCard = stalactites.columns[0].get();
		
		// move tablea card to cell
		TableauToCellMove t2CM = new TableauToCellMove(stalactites.columns[0], topCard, stalactites.cells[0]);
		
		assertTrue(t2CM.valid(stalactites));
		assertTrue(t2CM.doMove(stalactites));
		
		assertEquals(1, stalactites.cells[0].count());
		assertEquals(5, stalactites.columns[0].count());
		assertEquals(topCardValue, stalactites.cells[0].peek());
		assertEquals(47, stalactites.getNumLeft().getValue());
		
		// undo the move
		t2CM.undo(stalactites);
		assertEquals(0, stalactites.cells[0].count());
		assertEquals(6, stalactites.columns[0].count());
		assertEquals(topCardValue, stalactites.columns[0].peek());
		assertEquals(48, stalactites.getNumLeft().getValue());
	}
	
	public void testInvalid() {
		//check if move does not work on occupied cell
		Stalactites stalactites = new Stalactites();
		GameWindow gw = Main.generateWindow(stalactites, Deck.OrderBySuit);
		
		// place one card in cell
		Card topCard = stalactites.columns[0].get();
		TableauToCellMove t2CM = new TableauToCellMove(stalactites.columns[0], topCard, stalactites.cells[0]);
		
		// make sure this is still valid
		assertTrue(t2CM.valid(stalactites));
		assertTrue(t2CM.doMove(stalactites));
		
		// try placing another in the same cell, should be false
		Card secondCardVal = stalactites.columns[0].peek();
		Card secondCard = stalactites.columns[0].get();

		TableauToCellMove t2CM_2 = new TableauToCellMove(stalactites.columns[0], secondCard, stalactites.cells[0]);
		
		assertFalse(t2CM_2.valid(stalactites));
		assertFalse(t2CM_2.doMove(stalactites));

		assertEquals(topCard, stalactites.cells[0].peek());
		assertEquals(1, stalactites.cells[0].count());
		assertEquals(4, stalactites.columns[0].count());
		assertEquals(47, stalactites.getNumLeft().getValue());
		
		// try adding that removed card back (controller normally does this)
		stalactites.columns[0].add(secondCard);
		assertEquals(secondCardVal, stalactites.columns[0].peek());
	}
}
