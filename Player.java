import java.util.ArrayList;

public class Player {
	public ArrayList<Card> hand;
	public ArrayList<Card> fiveCardHand;
	public Player() {
		hand = new ArrayList<Card>();
	}
	public void add(Card one) {
		hand.add(one);
	}
	public ArrayList<Card> getHand(){
		return hand;
	}
	public void setFiveCardHand(ArrayList<Card>hand) {
		fiveCardHand = hand;
	}
	public ArrayList<Card> getFiveCardHand() {
		return fiveCardHand;
	}
}