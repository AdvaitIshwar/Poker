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
	public void shuffle() {
		Collections.shuffle(deck);
	}
}