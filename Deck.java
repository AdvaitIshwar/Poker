import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	ArrayList<Card> deck = new ArrayList<Card>();
	public Deck() {
		for(int i=0; i<4; i++) {
			for(int j=1; j<14; j++) {
				deck.add(new Card(j,i));
			}
		}
	}
	public void dealPlayers(ArrayList<Player> players) {
		Collections.shuffle(deck);
		for(int round=0; round<2; round++) {
			for(int i=0; i<players.size(); i++) {
				Player get = players.get(i);
				get.add(deck.get(0));
				deck.remove(0);
			}
		}
	}
	public ArrayList<Card> dealFlop() {
		ArrayList<Card> table = new ArrayList<Card>();
		//Burn
		deck.remove(0);
		//Flop
		table.add(deck.get(0));
		table.add(deck.get(1));
		table.add(deck.get(2));
		
		deck.remove(0);
		deck.remove(0);
		deck.remove(0);
		return table;
	}
	public ArrayList<Card> dealTurnAndRiver(ArrayList<Card>table) {
		//Burn
		deck.remove(0);
		//Turn
		table.add(deck.get(0));
		
		deck.remove(0);
		return table;
	}
}