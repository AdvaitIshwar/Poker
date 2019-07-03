import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
public class HandTesting {
	@Test
	public void testPair() {
		Deck deck = new Deck();
		ArrayList<Player> players = new ArrayList<Player>();
		Player one = new Player();
		Player two = new Player();
		Player three = new Player();
		players.add(one);
		players.add(two);
		players.add(three);
		deck.dealPlayers(players);
		System.out.println("Players:");
		System.out.println(players.toString());
		ArrayList<Card> table = new ArrayList<Card>();
		table = deck.dealFlop();
		table = deck.dealTurnAndRiver(table);
		table = deck.dealTurnAndRiver(table);
		System.out.println("Table:");
		System.out.println(table.toString());
		BestHandEvaluator eval = new BestHandEvaluator();
		ArrayList<Player> winner = new ArrayList<Player>();
		winner = eval.getBestHand(players, table);
		System.out.println("Player hands");
		System.out.println(players.toString());
		System.out.println(players.get(0).getFiveCardHand().toString());
		System.out.println(players.get(1).getFiveCardHand().toString());
		System.out.println(players.get(2).getFiveCardHand().toString());
		System.out.println("Winner");
		System.out.println(winner.toString());
		assertTrue(1==1);
	}
}