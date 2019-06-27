import java.util.ArrayList;

public class Player {
	public ArrayList<Card> hand;
	public Player() {
		hand = new ArrayList<Card>();
	}
	public void add(Card one) {
		hand.add(one);
	}
	public ArrayList<Card> getHand(){
		return hand;
	}
}