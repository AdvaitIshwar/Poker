package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HandEvaluator {

	final static int NOTHING = 0;
	final static int PAIR = 1;
	final static int TWOPAIR = 2;
	final static int SET = 3;
	final static int STRAIGHT = 4;
	final static int FLUSH = 5;
	final static int FULLHOUSE = 6;
	final static int QUADS = 7;
	final static int STRAIGHTFLUSH = 8;
	public static ArrayList<Card> straightHand = new ArrayList<Card>();
	public static ArrayList<Card> flushHand = new ArrayList<Card>();
	public static ArrayList<Card> fiveCardHand = new ArrayList<Card>();

	public static int hasPair(ArrayList<Card> table, Player p1) {
		ArrayList<Card> player = p1.getHand();
		if(hasThreeOfAKind(table, p1) == SET || hasTwoPair(table, p1) == TWOPAIR ||
				hasFullHouse(table, p1) == FULLHOUSE || hasFourOfAKind(table, p1) == QUADS) {
			return NOTHING;
		}
		fiveCardHand.clear();
		ArrayList<Card> allCards = new ArrayList<Card>();
		allCards.addAll(player);
		allCards.addAll(table);
		Collections.sort(allCards, new CardCompare());
		boolean hasPair = false;
		for(int i=0; i<allCards.size()-1; i++) {
			if(allCards.get(i).getValue()==allCards.get(i+1).getValue()) {
				fiveCardHand.add(allCards.get(i));
				fiveCardHand.add(allCards.get(i+1));
				allCards.remove(i);
				allCards.remove(i+1);
				hasPair = true;
				break;
			}
		}
		if(hasPair == true) {
			fiveCardHand.add(allCards.get(allCards.size()-1));
			fiveCardHand.add(allCards.get(allCards.size()-2));
			fiveCardHand.add(allCards.get(allCards.size()-3));
			//Pair first then next 3 best cards
			p1.setFiveCardHand(fiveCardHand);
			return PAIR;
		} else {
			fiveCardHand.add(allCards.get(6));
			fiveCardHand.add(allCards.get(5));
			fiveCardHand.add(allCards.get(4));
			fiveCardHand.add(allCards.get(3));
			fiveCardHand.add(allCards.get(2));
			p1.setFiveCardHand(fiveCardHand);
			return NOTHING;
		}
	}

	public static int hasTwoPair(ArrayList<Card> table, Player p1) {
		ArrayList<Card> player = p1.getHand();
		if (hasFullHouse(table, p1) == FULLHOUSE || hasFourOfAKind(table, p1) == QUADS) {
			return NOTHING;
		}
		fiveCardHand.clear();
		ArrayList<Card> allCards = new ArrayList<Card>();
		allCards.addAll(player);
		allCards.addAll(table);
		Collections.sort(allCards, new CardCompare());
		boolean hasTwoPair = false;
		for(int i=0; i<allCards.size()-2; i++) {
			if(allCards.get(i).getValue()==allCards.get(i+1).getValue()) {
				for(int j=i+1; j<allCards.size()-1; j++) {
					if(allCards.get(j).getValue()==allCards.get(j+1).getValue()) {
						fiveCardHand.add(allCards.get(i));
						fiveCardHand.add(allCards.get(i+1));
						fiveCardHand.add(allCards.get(j));
						fiveCardHand.add(allCards.get(j+1));
						allCards.remove(i);
						allCards.remove(i+1);
						allCards.remove(j);
						allCards.remove(j+1);
						hasTwoPair = true;
						break;
					}
				}
				if(hasTwoPair == true) {
					break;
				}
			}
		}
		if(hasTwoPair == true) {
			Collections.sort(fiveCardHand, new CardCompare());
			Collections.reverse(fiveCardHand);
			fiveCardHand.add(allCards.get(allCards.size()-1));
			//Higher pair first, then other pair then best card
			p1.setFiveCardHand(fiveCardHand);
			return TWOPAIR;
		} else {
			fiveCardHand.add(allCards.get(6));
			fiveCardHand.add(allCards.get(5));
			fiveCardHand.add(allCards.get(4));
			fiveCardHand.add(allCards.get(3));
			fiveCardHand.add(allCards.get(2));
			p1.setFiveCardHand(fiveCardHand);
			return NOTHING;
		}
	}

	public static int hasThreeOfAKind(ArrayList<Card> table, Player p1) {
		ArrayList<Card> player = p1.getHand();
		if (hasFullHouse(table, p1) == FULLHOUSE || hasFourOfAKind(table, p1) == QUADS) {
			return NOTHING;
		}
		fiveCardHand.clear();
		ArrayList<Card> allCards = new ArrayList<Card>();
		allCards.addAll(player);
		allCards.addAll(table);
		Collections.sort(allCards, new CardCompare());
		boolean hasSet = false;
		for(int i=0; i<allCards.size()-2; i++) {
			if(allCards.get(i).getValue()==allCards.get(i+1).getValue() &&
					allCards.get(i).getValue()==allCards.get(i+2).getValue()) {
				fiveCardHand.add(allCards.get(i));
				fiveCardHand.add(allCards.get(i+1));
				fiveCardHand.add(allCards.get(i+2));
				allCards.remove(i);
				allCards.remove(i+1);
				allCards.remove(i+2);
				hasSet = true;
				break;
			}
		}
		if(hasSet == true) {
			fiveCardHand.add(allCards.get(allCards.size()-1));
			fiveCardHand.add(allCards.get(allCards.size()-2));
			//Set first then 2 best cards
			p1.setFiveCardHand(fiveCardHand);
			return TWOPAIR;
		} else {
			fiveCardHand.add(allCards.get(6));
			fiveCardHand.add(allCards.get(5));
			fiveCardHand.add(allCards.get(4));
			fiveCardHand.add(allCards.get(3));
			fiveCardHand.add(allCards.get(2));
			p1.setFiveCardHand(fiveCardHand);
			return NOTHING;
		}
	}

	public static int hasStraight(ArrayList<Card> table, Player p1) {
		ArrayList<Card> player = p1.getHand();
		if (hasStraightFlush(table, p1) == STRAIGHTFLUSH) {
			return NOTHING;
		}
		// Checks straight with both cards in player's hand
		ArrayList<Card> allCards = new ArrayList<Card>();
		allCards.add(player.get(0));
		allCards.add(player.get(1));
		Collections.sort(table, new CardCompare());
		boolean hasStraight = straightHelper(table);
		if (hasStraight == true) {
			Collections.reverse(straightHand);
			//Puts straight hand in decending order of rank
			p1.setFiveCardHand(fiveCardHand);
			return STRAIGHT;
		} else {
			straightHand.add(allCards.get(6));
			straightHand.add(allCards.get(5));
			straightHand.add(allCards.get(4));
			straightHand.add(allCards.get(3));
			straightHand.add(allCards.get(2));
			p1.setFiveCardHand(straightHand);
			return NOTHING;
		}
	}

	//Pre-condition: Parameter must be sorted array containing cards on the board and player
	public static boolean straightHelper(ArrayList<Card> hand) {
		straightHand.clear();
		if (hand.get(0).getValue() + 1 == hand.get(1).getValue() && hand.get(0).getValue() + 2 == hand.get(2).getValue()
				&& hand.get(0).getValue() + 3 == hand.get(3).getValue()
				&& hand.get(0).getValue() + 4 == hand.get(4).getValue()) {
			straightHand.add(hand.get(0));
			straightHand.add(hand.get(1));
			straightHand.add(hand.get(2));
			straightHand.add(hand.get(3));
			straightHand.add(hand.get(4));
			return true;
		} else if (hand.get(1).getValue() + 1 == hand.get(2).getValue()
				&& hand.get(1).getValue() + 2 == hand.get(3).getValue()
				&& hand.get(1).getValue() + 3 == hand.get(4).getValue()
				&& hand.get(1).getValue() + 4 == hand.get(5).getValue()) {
			straightHand.add(hand.get(1));
			straightHand.add(hand.get(2));
			straightHand.add(hand.get(3));
			straightHand.add(hand.get(4));
			straightHand.add(hand.get(5));
			return true;
		} else if (hand.get(2).getValue() + 1 == hand.get(3).getValue()
				&& hand.get(2).getValue() + 2 == hand.get(4).getValue()
				&& hand.get(2).getValue() + 3 == hand.get(5).getValue()
				&& hand.get(2).getValue() + 4 == hand.get(6).getValue()) {
			straightHand.add(hand.get(2));
			straightHand.add(hand.get(3));
			straightHand.add(hand.get(4));
			straightHand.add(hand.get(5));
			straightHand.add(hand.get(6));
			return true;
		} else {
			return false;
		}
		//straightHand stores cards in straight order from lowest rank to highest rank
		//Ex 2 in a straight would be stored at index 0 and 6 at index 4
	}

	public static int hasFlush(ArrayList<Card> table, Player p1) {
		ArrayList<Card> player = p1.getHand();
		if (hasStraightFlush(table, p1) == STRAIGHTFLUSH) {
			return NOTHING;
		}
		ArrayList<Card> allCards = new ArrayList<Card>();
		allCards.addAll(player);
		allCards.addAll(table);
		Collections.sort(allCards, new CardCompare());
		int suitOne = 0;
		int suitTwo = 0;
		int suitThree = 0;
		int suitFour = 0;
		for (Card c : allCards) {
			if (c.getSuit() == 0) {
				suitOne++;
			} else if (c.getSuit() == 1) {
				suitTwo++;
			} else if (c.getSuit() == 2) {
				suitThree++;
			} else {
				suitFour++;
			}
		}
		if (suitOne >= 5) {
			getFlushHand(0, allCards, p1);
			return FLUSH;
		} else if (suitTwo >= 5) {
			getFlushHand(1, allCards, p1);
			return FLUSH;
		} else if (suitThree >= 5) {
			getFlushHand(2, allCards, p1);
			return FLUSH;
		} else if (suitFour >= 5) {
			getFlushHand(3, allCards, p1);
			return FLUSH;
		} else {
			flushHand.add(allCards.get(6));
			flushHand.add(allCards.get(5));
			flushHand.add(allCards.get(4));
			flushHand.add(allCards.get(3));
			flushHand.add(allCards.get(2));
			p1.setFiveCardHand(flushHand);
			return NOTHING;
		}
	}

	public static void getFlushHand(int suit, ArrayList<Card> table, Player p1) {
		flushHand.clear();
		ArrayList<Card> flushCards = new ArrayList<Card>();
		for (Card c : table) {
			if (c.getSuit() == suit) {
				flushCards.add(c);
			}
		}
		Collections.sort(flushCards, new CardCompare());
		flushHand.add(flushCards.get(flushCards.size()-1));
		flushHand.add(flushCards.get(flushCards.size()-2));
		flushHand.add(flushCards.get(flushCards.size()-3));
		flushHand.add(flushCards.get(flushCards.size()-4));
		flushHand.add(flushCards.get(flushCards.size()-5));
		p1.setFiveCardHand(flushHand);
		//flushHand stores best flush hand in order of greatest card rank to lowest
		//Ex Ace in flush will be stored at index 0 and 2 at index 4
	}

	public static int hasFullHouse(ArrayList<Card> table, Player p1) {
		ArrayList<Card> player = p1.getHand();
		fiveCardHand.clear();
		ArrayList<Card> allCards = new ArrayList<Card>();
		allCards.addAll(player);
		allCards.addAll(table);
		Collections.sort(allCards, new CardCompare());
		boolean hasFullHouse = false;
		for(int i=0; i<allCards.size()-2; i++) {
			if(allCards.get(i).getValue()==allCards.get(i+1).getValue() &&
					allCards.get(i).getValue()==allCards.get(i+2).getValue()) {
				for(int j=i+1; j<allCards.size()-1; j++) {
					if(allCards.get(j).getValue() == allCards.get(j+1).getValue()) {
						fiveCardHand.add(allCards.get(i));
						fiveCardHand.add(allCards.get(i+1));
						fiveCardHand.add(allCards.get(i+2));
						fiveCardHand.add(allCards.get(j));
						fiveCardHand.add(allCards.get(j+1));
						allCards.remove(i);
						allCards.remove(i+1);
						allCards.remove(i+2);
						allCards.remove(j);
						allCards.remove(j+1);
						hasFullHouse = true;
						break;
					}
				}
				if(hasFullHouse == true) {
					break;
				}
			}
		}
		if(hasFullHouse == true) {
			p1.setFiveCardHand(fiveCardHand);
			//Set first then pair
			return FULLHOUSE;
		} else {
			fiveCardHand.add(allCards.get(6));
			fiveCardHand.add(allCards.get(5));
			fiveCardHand.add(allCards.get(4));
			fiveCardHand.add(allCards.get(3));
			fiveCardHand.add(allCards.get(2));
			p1.setFiveCardHand(fiveCardHand);
			return NOTHING;
		}
	}

	public static int hasFourOfAKind(ArrayList<Card> table, Player p1) {
		ArrayList<Card> player = p1.getHand();
		fiveCardHand.clear();
		ArrayList<Card> allCards = new ArrayList<Card>();
		allCards.addAll(player);
		allCards.addAll(table);
		Collections.sort(allCards, new CardCompare());
		boolean hasQuads = false;
		for(int i=0; i<allCards.size()-3; i++) {
			if(allCards.get(i).getValue()==allCards.get(i+1).getValue() &&
					allCards.get(i).getValue()==allCards.get(i+2).getValue() &&
					allCards.get(i).getValue()==allCards.get(i+3).getValue()) {
				fiveCardHand.add(allCards.get(i));
				fiveCardHand.add(allCards.get(i+1));
				fiveCardHand.add(allCards.get(i+2));
				fiveCardHand.add(allCards.get(i+3));
				allCards.remove(i);
				allCards.remove(i+1);
				allCards.remove(i+2);
				allCards.remove(i+3);
				hasQuads = true;
				break;
			}
		}
		if(hasQuads == true) {
			fiveCardHand.add(allCards.get(allCards.size()-1));
			p1.setFiveCardHand(fiveCardHand);
			//Quads first then best card
			return QUADS;
		} else {
			fiveCardHand.add(allCards.get(6));
			fiveCardHand.add(allCards.get(5));
			fiveCardHand.add(allCards.get(4));
			fiveCardHand.add(allCards.get(3));
			fiveCardHand.add(allCards.get(2));
			p1.setFiveCardHand(fiveCardHand);
			return NOTHING;
		}
	}

	public static int hasStraightFlush(ArrayList<Card> table, Player p1) {
		if (hasFlush(table, p1) == FLUSH && hasStraight(table, p1) == STRAIGHT) {
			if (flushHand.get(0).equals(straightHand.get(0)) && flushHand.get(1).equals(straightHand.get(1))
					&& flushHand.get(2).equals(straightHand.get(2)) && flushHand.get(3).equals(straightHand.get(3))
					&& flushHand.get(4).equals(straightHand.get(4))) {
				return STRAIGHTFLUSH;
			} else {
				return NOTHING;
			}
		} else {
			return NOTHING;
		}
	}
	public static ArrayList<Card> getFlushHand() {
		return flushHand;
	}
	public static ArrayList<Card> getStraightHand() {
		return straightHand;
	}
	public static ArrayList<Card> getFiveCardHand() {
		return fiveCardHand;
	}
}
