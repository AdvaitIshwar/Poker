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
	final static int ROYALFLUSH = 9;
	public static ArrayList<Card> straightHand = new ArrayList<Card>();
	public static ArrayList<Card> flushHand = new ArrayList<Card>();


	public static int hasPair(ArrayList<Card>table, ArrayList<Card> player) {
		ArrayList<Integer>tableNum = new ArrayList<Integer>();
		for(Card c: table) {
			tableNum.add(c.getValue());
		}
		if(tableNum.contains(player.get(0).getValue()) && !(tableNum.contains(player.get(1).getValue()))) {
			return PAIR;
		}
		return NOTHING;
	}

	public static int hasTwoPair(ArrayList<Card>table, ArrayList<Card> player) {
		ArrayList<Integer>tableNum = new ArrayList<Integer>();
		for(Card c: table) {
			tableNum.add(c.getValue());
		}
		if(tableNum.contains(player.get(0).getValue()) && tableNum.contains(player.get(1).getValue())) {
			return TWOPAIR;
		}
		return NOTHING;
	}

	public static int hasThreeOfAKind(ArrayList<Card>table, ArrayList<Card> player) {
		if(hasFullHouse(table,player) == FULLHOUSE || hasFourOfAKind(table,player)==QUADS) {
			return NOTHING;
		}


		int index = -1;


		Collections.sort(table, new CardCompare());
		ArrayList<Integer>tableNum = new ArrayList<Integer>();
		for(Card c: table) {
			tableNum.add(c.getValue());
		}

		//If player has a pocket pair
		if(player.get(0).getValue()==player.get(1).getValue()) {
			if(tableNum.contains(player.get(0).getValue())) {
				index = tableNum.indexOf(player.get(0).getValue());
			}
			tableNum.remove(index);
			if(tableNum.contains(player.get(0).getValue())) {
				return SET;
			} else {
				return NOTHING;
			}
		}

		//Check set for first card
		if(tableNum.contains(player.get(0).getValue())) {
			if(tableNum.contains(player.get(0).getValue())) {
				index = tableNum.indexOf(player.get(0).getValue());
			}
			tableNum.remove(index);
			if(tableNum.contains(player.get(0).getValue())) {
				return SET;
			} else {
				return NOTHING;
			}
		}

		//Check set for second card
		if(tableNum.contains(player.get(1).getValue())) {
			if(tableNum.contains(player.get(1).getValue())) {
				index = tableNum.indexOf(player.get(1).getValue());
			}
			tableNum.remove(index);
			if(tableNum.contains(player.get(1).getValue())) {
				return SET;
			} else {
				return NOTHING;
			}
		}
		return NOTHING;
	}

	public static int hasStraight(ArrayList<Card>table, ArrayList<Card> player) {
		if(hasStraightFlush(table,player) == STRAIGHTFLUSH) {
			return NOTHING;
		}
		//Checks straight with both cards in player's hand
		table.add(player.get(0));
		table.add(player.get(1));
		Collections.sort(table, new CardCompare());
		boolean result = straightHelper(table);
		if(result == true) {
			return STRAIGHT;
		}

		return NOTHING;
	}

	public static boolean straightHelper(ArrayList<Card>hand) {

		if(hand.get(0).getValue()+1 == hand.get(1).getValue() &&
				hand.get(0).getValue()+2 == hand.get(2).getValue() &&
				hand.get(0).getValue()+3 == hand.get(3).getValue() &&
				hand.get(0).getValue()+4 == hand.get(4).getValue()) {
			straightHand.add(hand.get(0));
			straightHand.add(hand.get(1));
			straightHand.add(hand.get(2));
			straightHand.add(hand.get(3));
			straightHand.add(hand.get(4));
			return true;
		} else if(hand.get(1).getValue()+1 == hand.get(2).getValue() &&
				hand.get(1).getValue()+2 == hand.get(3).getValue() &&
				hand.get(1).getValue()+3 == hand.get(4).getValue() &&
				hand.get(1).getValue()+4 == hand.get(5).getValue()) {
			straightHand.add(hand.get(1));
			straightHand.add(hand.get(2));
			straightHand.add(hand.get(3));
			straightHand.add(hand.get(4));
			straightHand.add(hand.get(5));
			return true;
		} else if(hand.get(2).getValue()+1 == hand.get(3).getValue() &&
				hand.get(2).getValue()+2 == hand.get(4).getValue() &&
				hand.get(2).getValue()+3 == hand.get(5).getValue() &&
				hand.get(2).getValue()+4 == hand.get(6).getValue()) {
			straightHand.add(hand.get(2));
			straightHand.add(hand.get(3));
			straightHand.add(hand.get(4));
			straightHand.add(hand.get(5));
			straightHand.add(hand.get(6));
			return true;
		}

		return false;
	}

	public static int hasFlush(ArrayList<Card>table, ArrayList<Card> player) {
		if(hasStraightFlush(table,player) == STRAIGHTFLUSH) {
			return NOTHING;
		}
		table.addAll(player);
		int suitOne = 0;
		int suitTwo = 0;
		int suitThree = 0;
		int suitFour = 0;
		for(Card c: table) {
			if(c.getSuit()==0) {
				suitOne++;
			} else if(c.getSuit()==1) {
				suitTwo++;
			} else if(c.getSuit()==2) {
				suitThree++;
			} else {
				suitFour++;
			}
		}
		if(suitOne == 5) {
			getFlushHand(0, table);
			return FLUSH;
		} else if(suitTwo == 5) {
			getFlushHand(1, table);
			return FLUSH;
		} else if(suitThree == 5) {
			getFlushHand(2, table);
			return FLUSH;
		} else if(suitFour == 5) {
			getFlushHand(3, table);
			return FLUSH;
		} else {
			return NOTHING;
		}
	}
	
	public static void getFlushHand(int index, ArrayList<Card>table) {
		int suit = index;
		for(Card c: table) {
			if(c.getSuit()==suit) {
				flushHand.add(c);
			}
		}
		Collections.sort(flushHand, new CardCompare());
	}
	
	public static int hasFullHouse(ArrayList<Card>table, ArrayList<Card> player) {
		if(hasThreeOfAKind(table,player)==SET && hasTwoPair(table,player)==TWOPAIR) {
			
			return FULLHOUSE;
		}else {
			return NOTHING;
		}
	}
	public static int hasFourOfAKind(ArrayList<Card>table, ArrayList<Card> player) {
		int index = -1;


		Collections.sort(table, new CardCompare());
		ArrayList<Integer>tableNum = new ArrayList<Integer>();
		for(Card c: table) {
			tableNum.add(c.getValue());
		}

		//If player has a pocket pair
		if(player.get(0).getValue()==player.get(1).getValue()) {
			if(tableNum.contains(player.get(0).getValue())) {
				index = tableNum.indexOf(player.get(0).getValue());
			}
			tableNum.remove(index);
			if(tableNum.contains(player.get(0).getValue())) {
				return QUADS;
			} else {
				return NOTHING;
			}
		}

		//Check quads for first card
		if(tableNum.contains(player.get(0).getValue())) {
			if(tableNum.contains(player.get(0).getValue())) {
				index = tableNum.indexOf(player.get(0).getValue());
			}
			tableNum.remove(index);
			if(tableNum.contains(player.get(0).getValue())) {
				index = tableNum.indexOf(player.get(0).getValue());
			}
			tableNum.remove(index);
			if(tableNum.contains(player.get(0).getValue())) {
				return QUADS;
			} else {
				return NOTHING;
			}
		}

		//Check quads for second card
		if(tableNum.contains(player.get(1).getValue())) {
			if(tableNum.contains(player.get(1).getValue())) {
				index = tableNum.indexOf(player.get(1).getValue());
			}
			tableNum.remove(index);
			if(tableNum.contains(player.get(1).getValue())) {
				index = tableNum.indexOf(player.get(1).getValue());
			}
			tableNum.remove(index);
			if(tableNum.contains(player.get(1).getValue())) {
				return QUADS;
			} else {
				return NOTHING;
			}
		}
		return NOTHING;
	}

	public static int hasStraightFlush(ArrayList<Card>table, ArrayList<Card> player) {
		if(hasFlush(table, player)== FLUSH && hasStraight(table, player)==STRAIGHT) {
			if(flushHand.get(0).equals(straightHand.get(0)) && 
					flushHand.get(1).equals(straightHand.get(1)) &&
					flushHand.get(2).equals(straightHand.get(2)) &&
					flushHand.get(3).equals(straightHand.get(3)) &&
					flushHand.get(4).equals(straightHand.get(4))) {
				return STRAIGHTFLUSH;
			} else {
				return NOTHING;
			}
		}else {
			return NOTHING;
		}
	}
}

