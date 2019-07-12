package poker;

import java.util.ArrayList;

public class Player {
	public ArrayList<Card> hand;
	public ArrayList<Card> fiveCardHand;
	public double money;
	public double moneyInFront = 0;
	public boolean in;
	public Player() {
		hand = new ArrayList<Card>();
		money = 0;
		in = false;
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
	public void setMoney(int amount) {
		money += amount;
	}
	public void bet(int amount) {
		if(money < amount) {
			moneyInFront += money;
			money = 0;
		}
		money -= amount;
		moneyInFront += amount;
	}
	public void fold() {
		in = false;
	}
}