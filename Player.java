import java.util.ArrayList;

public class Player {
	String rank;
	public ArrayList<Card> hand;
	public ArrayList<Card> fiveCardHand;
	public Player() {
		hand = new ArrayList<Card>();
		rank = "";
	}
	public void add(Card one) {
		hand.add(one);
	}
	public ArrayList<Card> getHand(){
		return this.hand;
	}
	public void setFiveCardHand(ArrayList<Card>hand) {
		fiveCardHand = hand;
	}
	public ArrayList<Card> getFiveCardHand() {
		return fiveCardHand;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String toString() {
		String temp = "{" +hand.get(0).toString() + ", " + hand.get(1).toString() + "} "+ rank;
		return temp;
	}
}