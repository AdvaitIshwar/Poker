import java.util.ArrayList;

public class BestHandEvaluator {
	int bestHand;
	int numPlayers;
	public BestHandEvaluator(int numPlayers) {
		this.numPlayers = numPlayers;
		bestHand = HandEvaluator.NOTHING;
	}
	public Player getBestHand(ArrayList<Player>players, ArrayList<Card>table) {
		ArrayList<Player>winner = new ArrayList<Player>();
		//Determines the best hand at the table
		for(Player p: players) {
			int playerHand = bestHandHelper(p.getHand(), table);
			if(playerHand>=bestHand) {
				bestHand = playerHand;
			}
		}
		//List of people with the best hand at the table
		for(Player p:players) {
			int playerHand = bestHandHelper(p.getHand(), table);
			if(playerHand == bestHand) {
				winner.add(p);
			}
		}
		if(winner.size()>1) {
			
		} else {
			return winner.get(0);
		}
	}
	
	public int bestHandHelper(ArrayList<Card>hand, ArrayList<Card>table) {
		if(HandEvaluator.hasStraightFlush(table, hand)==HandEvaluator.STRAIGHTFLUSH) {
			return HandEvaluator.STRAIGHTFLUSH;
		} else if(HandEvaluator.hasFourOfAKind(table, hand)==HandEvaluator.QUADS) {
			return HandEvaluator.QUADS;
		} else if(HandEvaluator.hasFullHouse(table, hand)==HandEvaluator.FULLHOUSE) {
			return HandEvaluator.FULLHOUSE;
		} else if(HandEvaluator.hasFlush(table, hand)==HandEvaluator.FLUSH) {
			return HandEvaluator.FLUSH;
		} else if(HandEvaluator.hasStraight(table, hand)==HandEvaluator.STRAIGHT) {
			return HandEvaluator.STRAIGHT;
		} else if(HandEvaluator.hasThreeOfAKind(table, hand)==HandEvaluator.SET) {
			return HandEvaluator.SET;
		} else if(HandEvaluator.hasTwoPair(table, hand)==HandEvaluator.TWOPAIR) {
			return HandEvaluator.TWOPAIR;
		} else if(HandEvaluator.hasPair(table, hand)==HandEvaluator.PAIR) {
			return HandEvaluator.PAIR;
		} else {
			return HandEvaluator.NOTHING;
		}
	}
}