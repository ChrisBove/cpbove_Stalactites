package cpbove;

import cpbove.Stalactites.PlayStyle;
import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;

public class TestTableauToFoundationMove extends TestCase {
	public void testMoveToEmpty() {
		Stalactites stalactites = new Stalactites();
		GameWindow gw = Main.generateWindow(stalactites, Deck.OrderBySuit);
		
		Card topCardValue = stalactites.columns[7].peek();
		Card topCard = stalactites.columns[7].get();
		
		TableauToFoundationMove move = new TableauToFoundationMove(stalactites.columns[7], topCard, stalactites.foundations[0], stalactites.foundationBases[0], stalactites.playStyle);
		
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
		assertEquals(6, stalactites.columns[7].count());
		assertEquals(topCardValue, stalactites.columns[7].peek());
		numLeft = stalactites.getNumLeft().getValue();
		assertEquals(48, numLeft);
	}
	
	public void testInvalidMoveToEmpty() {
		Stalactites stalactites = new Stalactites();
		GameWindow gw = Main.generateWindow(stalactites, Deck.OrderBySuit);
		
		Card topCardValue = stalactites.columns[0].peek();
		Card topCard = stalactites.columns[0].get();
		
		TableauToFoundationMove move = new TableauToFoundationMove(stalactites.columns[0], topCard, stalactites.foundations[0], stalactites.foundationBases[0], stalactites.playStyle);
		
		assertFalse(move.valid(stalactites));
		
		assertFalse(move.doMove(stalactites));
		
		assertEquals(0, stalactites.foundations[0].count());
		// return the card to the column
		stalactites.columns[0].add(topCard);
		assertEquals(6, stalactites.columns[0].count());
		assertEquals(topCardValue, stalactites.columns[0].peek());
		int numLeft = stalactites.getNumLeft().getValue();
		assertEquals(48, numLeft);
		int score = stalactites.getScoreValue();
		assertEquals(0, score);
	}
	
	// test moving onto non-empty foundation
	public void testConsequetiveMoves() {
		Stalactites stalactites = new Stalactites();
		GameWindow gw = Main.generateWindow(stalactites, Deck.OrderBySuit);
		
		for (int col = 7; col >=0; col-- ) {
			Card topCard = stalactites.columns[col].get();

			TableauToFoundationMove move = new TableauToFoundationMove(stalactites.columns[col], topCard, stalactites.foundations[0], stalactites.foundationBases[0], stalactites.playStyle);

			assertTrue(move.valid(stalactites));

			assertTrue(move.doMove(stalactites));
		}
		for (int col = 7; col >=4; col-- ) {
			Card topCard = stalactites.columns[col].get();

			TableauToFoundationMove move = new TableauToFoundationMove(stalactites.columns[col], topCard, stalactites.foundations[0], stalactites.foundationBases[0], stalactites.playStyle);

			assertTrue(move.valid(stalactites));

			assertTrue(move.doMove(stalactites));
		}
		
		// try adding one more card than possible
				
		Card topCardValue = stalactites.columns[3].peek();
		Card topCard = stalactites.columns[3].get();
		
		TableauToFoundationMove move = new TableauToFoundationMove(stalactites.columns[7], topCard, stalactites.foundations[0], stalactites.foundationBases[0], stalactites.playStyle);
		
		assertFalse(move.valid(stalactites));
		
		assertFalse(move.doMove(stalactites));
		
	}
	
	public void testTwosMoveToEmpty() {
		Stalactites stalactites = new Stalactites();
		GameWindow gw = Main.generateWindow(stalactites, Deck.OrderBySuit);
		
		stalactites.playStyle = PlayStyle.TWOS;
		
		Card topCardValue = stalactites.columns[7].peek();
		Card topCard = stalactites.columns[7].get();
		
		TableauToFoundationMove move = new TableauToFoundationMove(stalactites.columns[7], topCard, stalactites.foundations[1], stalactites.foundationBases[1], stalactites.playStyle);
		
		assertTrue(move.valid(stalactites));
		
		assertTrue(move.doMove(stalactites));
		
		assertEquals(1, stalactites.foundations[1].count());
		assertEquals(5, stalactites.columns[7].count());
		assertEquals(topCardValue, stalactites.foundations[1].peek());
		int numLeft = stalactites.getNumLeft().getValue();
		assertEquals(47, numLeft);
		int score = stalactites.getScoreValue();
		assertEquals(1, score);
		
		// undo the move
		move.undo(stalactites);
		assertEquals(0, stalactites.foundations[1].count());
		assertEquals(6, stalactites.columns[7].count());
		assertEquals(topCardValue, stalactites.columns[7].peek());
		numLeft = stalactites.getNumLeft().getValue();
		assertEquals(48, numLeft);
	}

}