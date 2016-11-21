package cpbove;

import java.awt.event.MouseEvent;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;

public class TestStalactiteControllers extends KSTestCase {
	
	public void testColToCellController() {
		Stalactites game = new Stalactites();
		GameWindow gw = Main.generateWindow(game, Deck.OrderBySuit);
		
		Card onTop = game.columns[0].peek();
		// grab card on top of column
		MouseEvent pr = createPressed (game, game.columnViews[0], 0, 19*6);
		game.columnViews[0].getMouseManager().handleMouseEvent(pr);
		
		// drop onto cell
		MouseEvent re = createReleased(game, game.cellViews[0], 0, 0);
		game.cellViews[0].getMouseManager().handleMouseEvent(re);
		Card inCell = game.cells[0].peek();
		
		assertEquals (onTop, inCell);
		
	}
	
	public void testCellToFoundController() {
		Stalactites game = new Stalactites();
		GameWindow gw = Main.generateWindow(game, Deck.OrderBySuit);
		
		Card onTop = game.columns[7].peek();
		// grab card on top of column
		MouseEvent pr = createPressed (game, game.columnViews[7], 0, 19*6);
		game.columnViews[7].getMouseManager().handleMouseEvent(pr);
		
		// drop onto cell
		MouseEvent re = createReleased(game, game.cellViews[0], 0, 0);
		game.cellViews[0].getMouseManager().handleMouseEvent(re);
		Card inCell = game.cells[0].peek();
		
		assertEquals (onTop, inCell);
		
		// try to drop the cell onto the right foundation
		// pick up cell
		MouseEvent pr2 = createPressed (game,game.cellViews[0], 0, 0);
		game.cellViews[0].getMouseManager().handleMouseEvent(pr2);
		assertEquals(0, game.cells[0].count());
		// drop onto foundation
		MouseEvent re2 = createReleased(game, game.foundationViews[0], 0, 0);
		game.foundationViews[0].getMouseManager().handleMouseEvent(re2);
		
		Card inFoundation = game.foundations[0].peek();
		assertEquals(1, game.foundations[0].count());
		assertEquals(onTop, inFoundation);
	}
	
	public void testColToFoundController() {
		Stalactites game = new Stalactites();
		GameWindow gw = Main.generateWindow(game, Deck.OrderBySuit);
		
		Card onTop = game.columns[7].peek();
		// grab card on top of column
		MouseEvent pr = createPressed (game, game.columnViews[7], 0, 19*6);
		game.columnViews[7].getMouseManager().handleMouseEvent(pr);
		assertEquals(5, game.columns[7].count());
		
		// drop onto foundation
		MouseEvent re2 = createReleased(game, game.foundationViews[0], 0, 0);
		game.foundationViews[0].getMouseManager().handleMouseEvent(re2);
		Card inFoundation = game.foundations[0].peek();
		assertEquals(1, game.foundations[0].count());
		assertEquals(onTop, inFoundation);
		
	}
	
	public void testHasWon() {
		// truly, this should try moving all the cards through in the proper solution, but the condition
		// simply checks this number any way, and we already know adding cards to the foundation works
		Stalactites game = new Stalactites();
		GameWindow gw = Main.generateWindow(game, Deck.OrderBySuit);
		
		game.updateScore(48);
		assertTrue(game.hasWon());
	}
	
}
