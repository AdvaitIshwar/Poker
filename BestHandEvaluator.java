import java.util.ArrayList;

public class BestHandEvaluator {
	int bestHand;
	public BestHandEvaluator() {
		bestHand = HandEvaluator.NOTHING;
	}
	public ArrayList<Player> getBestHand(ArrayList<Player>players, ArrayList<Card>table) {
		ArrayList<Player>winner = new ArrayList<Player>();
		//Determines the best hand at the table
		for(Player p: players) {
			int playerHand = bestHandHelper(p, table);
			if(playerHand>=bestHand) {
				bestHand = playerHand;
			}
		}
		//List of people with the best hand at the table
		for(Player p:players) {
			int playerHand = bestHandHelper(p, table);
			if(playerHand == bestHand) {
				winner.add(p);
			}
		}
		
		//If more than one player has the best rank
		if(winner.size()>1) {
			return evaluateSameRank(winner, bestHand);
		} else {
			return winner;
		}
	}
	
	//Works when comparing Flushes, Straights, StraightFlushes and nothing against each other
	public ArrayList<Player> compareFlushesAndStraightsAndNothing(ArrayList<Player>sameRank) {
		ArrayList<Player> winnerCopy = new ArrayList<Player>();
		winnerCopy.addAll(sameRank);
		for(int i=0; i<sameRank.size(); i++) {
			for(int j=0; j<sameRank.size(); j++) {
				if(i!=j) {
					ArrayList<Card> playerOne = sameRank.get(i).getFiveCardHand();
					ArrayList<Card> playerTwo = sameRank.get(j).getFiveCardHand();
					for(int card=0; card<5; card++) {
						if(playerOne.get(card).getValue() > playerTwo.get(card).getValue()) {
							winnerCopy.remove(sameRank.get(j));
						} else if(playerOne.get(card).getValue() < playerTwo.get(card).getValue()) {
							winnerCopy.remove(sameRank.get(i));
						} else {
							continue;
						}
					}
				} else {
					continue;
				}
			}
		}
		return winnerCopy;
	}
	
	public ArrayList<Player> compareQuads(ArrayList<Player>sameRank) {
		ArrayList<Player> winnerCopy = new ArrayList<Player>();
		winnerCopy.addAll(sameRank);
		for(int i=0; i<sameRank.size(); i++) {
			for(int j=0; j<sameRank.size(); j++) {
				if(i!=j) {
					ArrayList<Card> playerOne = sameRank.get(i).getFiveCardHand();
					ArrayList<Card> playerTwo = sameRank.get(i+1).getFiveCardHand();
					if(playerOne.get(0).getValue() == playerTwo.get(0).getValue()) {
						if(playerOne.get(4).getValue() > playerTwo.get(4).getValue()) {
							winnerCopy.remove(sameRank.get(i+1));
						} else if(playerOne.get(4).getValue() < playerTwo.get(4).getValue()) {
							winnerCopy.remove(sameRank.get(i));
						} else {
							continue;
						}
					} else if(playerOne.get(0).getValue() > playerTwo.get(0).getValue()) {
						winnerCopy.remove(sameRank.get(i+1));
					} else {
						winnerCopy.remove(sameRank.get(i));
					}
				} else {
					continue;
				}
			}
		}
		return winnerCopy;
	}
	
	public ArrayList<Player> compareFullHouse(ArrayList<Player> sameRank) {
		ArrayList<Player> winnerCopy = new ArrayList<Player>();
		winnerCopy.addAll(sameRank);
		for(int i=0; i<sameRank.size(); i++) {
			for(int j=0; j<sameRank.size(); j++) {
				if(i!=j) {
					ArrayList<Card> playerOne = sameRank.get(i).getFiveCardHand();
					ArrayList<Card> playerTwo = sameRank.get(j).getFiveCardHand();
					if(playerOne.get(0).getValue() == playerTwo.get(0).getValue()) {
						if(playerOne.get(3).getValue() > playerTwo.get(3).getValue()) {
							winnerCopy.remove(sameRank.get(j));
						} else if(playerOne.get(0).getValue() < playerTwo.get(0).getValue()) {
							winnerCopy.remove(sameRank.get(i));
						} else {
							continue;
						}
					} else if(playerOne.get(0).getValue() > playerTwo.get(0).getValue()) {
						winnerCopy.remove(sameRank.get(j));
					} else {
						winnerCopy.remove(sameRank.get(i));
					}
				} else {
					continue;
				}
			}
		}
		return winnerCopy;
	}
	
	public ArrayList<Player> compareSet(ArrayList<Player> sameRank){
		ArrayList<Player> winnerCopy = new ArrayList<Player>();
		winnerCopy.addAll(sameRank);
		for(int i=0; i<sameRank.size(); i++) {
			for(int j=0; j<sameRank.size(); j++) {
				if(i!=j) {
					ArrayList<Card> playerOne = sameRank.get(i).getFiveCardHand();
					ArrayList<Card> playerTwo = sameRank.get(j).getFiveCardHand();
					if(playerOne.get(0).getValue() == playerTwo.get(0).getValue()) {
						//Compare High Card if sets are same
						if(playerOne.get(3).getValue() == playerTwo.get(3).getValue()) {
							//Compares second high card if sets and high card are same
							if(playerOne.get(4).getValue() == playerTwo.get(4).getValue()) {
								continue;
							} else if(playerOne.get(4).getValue() > playerTwo.get(4).getValue()) {
								winnerCopy.remove(sameRank.get(j));
							} else {
								winnerCopy.remove(sameRank.get(i));
							}
						} else if(playerOne.get(3).getValue() > playerTwo.get(3).getValue()) {
							winnerCopy.remove(sameRank.get(j));
						} else {
							winnerCopy.remove(sameRank.get(i));
						}
					} else if(playerOne.get(0).getValue() > playerTwo.get(0).getValue()) {
						winnerCopy.remove(sameRank.get(j));
					} else {
						winnerCopy.remove(sameRank.get(i));
					}
				} else {
					continue;
				}
			}
		}
		return winnerCopy;
	}
	
	public ArrayList<Player> compareTwoPair(ArrayList<Player> sameRank){
		ArrayList<Player> winnerCopy = new ArrayList<Player>();
		winnerCopy.addAll(sameRank);
		for(int i=0; i<sameRank.size(); i++) {
			for(int j=0; j<sameRank.size(); j++) {
				if(i!=j) {
					ArrayList<Card> playerOne = sameRank.get(i).getFiveCardHand();
					ArrayList<Card> playerTwo = sameRank.get(j).getFiveCardHand();
					if(playerOne.get(0).getValue() == playerTwo.get(0).getValue()) {
						if(playerOne.get(2).getValue() == playerTwo.get(2).getValue()) {
							if(playerOne.get(4).getValue() == playerTwo.get(4).getValue()) {
								continue;
							} else if(playerOne.get(4).getValue() > playerTwo.get(4).getValue()) {
								winnerCopy.remove(sameRank.get(j));
							} else {
								winnerCopy.remove(sameRank.get(i));
							}
						} else if(playerOne.get(2).getValue() > playerTwo.get(2).getValue()) {
							winnerCopy.remove(sameRank.get(j));
						} else {
							winnerCopy.remove(sameRank.get(i));
						}
					} else if(playerOne.get(0).getValue() > playerTwo.get(0).getValue()) {
						winnerCopy.remove(sameRank.get(j));
					} else {
						winnerCopy.remove(sameRank.get(i));
					}
				} else {
					continue;
				}
			}
		}
		return winnerCopy;
	}
	
	public ArrayList<Player> comparePair(ArrayList<Player> sameRank){
		ArrayList<Player> winnerCopy = new ArrayList<Player>();
		winnerCopy.addAll(sameRank);
		for(int i=0; i<sameRank.size(); i++) {
			for(int j=0; j<sameRank.size(); j++) {
				if(i!=j) {
					ArrayList<Card> playerOne = sameRank.get(i).getFiveCardHand();
					ArrayList<Card> playerTwo = sameRank.get(j).getFiveCardHand();
					if(playerOne.get(0).getValue() == playerTwo.get(0).getValue()) {
						if(playerOne.get(2).getValue() == playerTwo.get(2).getValue()) {
							if(playerOne.get(3).getValue() == playerTwo.get(3).getValue()) {
								if(playerOne.get(4).getValue() == playerTwo.get(4).getValue()) {
									continue;
								} else if(playerOne.get(4).getValue() > playerTwo.get(4).getValue()) {
									winnerCopy.remove(sameRank.get(j));
								} else {
									winnerCopy.remove(sameRank.get(i));
								}
							} else if(playerOne.get(3).getValue() > playerTwo.get(3).getValue()) {
								winnerCopy.remove(sameRank.get(j));
							} else {
								winnerCopy.remove(sameRank.get(i));
							}
						} else if(playerOne.get(2).getValue() > playerTwo.get(2).getValue()) {
							winnerCopy.remove(sameRank.get(j));
						} else {
							winnerCopy.remove(sameRank.get(i));
						}
					} else if(playerOne.get(0).getValue() > playerTwo.get(0).getValue()) {
						winnerCopy.remove(sameRank.get(j));
					} else {
						winnerCopy.remove(sameRank.get(i));
					}
				} else {
					continue;
				}
			}
		}
		return winnerCopy;
	}
	
	public ArrayList<Player> evaluateSameRank(ArrayList<Player>sameRank, int hand) {
		if(hand == HandEvaluator.STRAIGHTFLUSH) {
			return compareFlushesAndStraightsAndNothing(sameRank);
		} else if(hand == HandEvaluator.QUADS) {
			return compareQuads(sameRank);
		} else if(hand == HandEvaluator.FULLHOUSE) {
			return compareFullHouse(sameRank);
		} else if(hand == HandEvaluator.FLUSH) {
			return compareFlushesAndStraightsAndNothing(sameRank);
		} else if(hand == HandEvaluator.STRAIGHT) {
			return compareFlushesAndStraightsAndNothing(sameRank);
		} else if(hand == HandEvaluator.SET) {
			return compareSet(sameRank);
		} else if(hand == HandEvaluator.TWOPAIR) {
			return compareTwoPair(sameRank);
		} else if(hand == HandEvaluator.PAIR) {
			return comparePair(sameRank);
		} else {
			return compareFlushesAndStraightsAndNothing(sameRank);
		}
	}
	
	public int bestHandHelper(Player player, ArrayList<Card>table) {
		HandEvaluator eval = new HandEvaluator();
		if(eval.hasStraightFlush(table, player)==HandEvaluator.STRAIGHTFLUSH) {
			player.setRank("StraightFlush");
			return HandEvaluator.STRAIGHTFLUSH;
		} else if(eval.hasFourOfAKind(table, player)==HandEvaluator.QUADS) {
			player.setRank("Quads");
			return HandEvaluator.QUADS;
		} else if(eval.hasFullHouse(table, player)==HandEvaluator.FULLHOUSE) {
			player.setRank("FullHouse");
			return HandEvaluator.FULLHOUSE;
		} else if(eval.hasFlush(table, player)==HandEvaluator.FLUSH) {
			player.setRank("Flush");
			return HandEvaluator.FLUSH;
		} else if(eval.hasStraight(table, player)==HandEvaluator.STRAIGHT) {
			player.setRank("Straight");
			return HandEvaluator.STRAIGHT;
		} else if(eval.hasThreeOfAKind(table, player)==HandEvaluator.SET) {
			player.setRank("Set");
			return HandEvaluator.SET;
		} else if(eval.hasTwoPair(table, player)==HandEvaluator.TWOPAIR) {
			player.setRank("TwoPair");
			return HandEvaluator.TWOPAIR;
		} else if(eval.hasPair(table, player)==HandEvaluator.PAIR) {
			player.setRank("Pair");
			return HandEvaluator.PAIR;
		} else {
			player.setRank("Nothing");
			return HandEvaluator.NOTHING;
		}
	}
}