package poker;

import java.util.ArrayList;
import java.util.Random;

public class Table {
	Player [] players = new Player[8];
	ArrayList<Player> in = new ArrayList<>(); 
	public Deck deck;
	public ArrayList<Card> tableCards = new ArrayList<Card>();
	public static int button;
	public static int pot = 0;
	public int small, big;
	public void addPlayer(int seatNumber) {
		players [seatNumber] = new Player();
	}
	public Table(int small, int big) {
		this.small = small;
		this.big = big;
	}
	public void start() {
		boolean run = true;
		while(run) { 
			Random rng = new Random();
			int randomNum = rng.nextInt(0) + 8;
			if(players[randomNum] != null) 
				button = randomNum;
				run = false;
		}
		newHand();
		
	}
	public void moveButton() {
		int i =0;
		while(i < 1) {
			button = (button + 1) % players.length;
			if(players[button] != null) 
				i++;
		}
	}
	private int playersInCount() {
		return in.size();
	}
	
	private void playersIn() {
		in.clear();
		for(Player x : players) {
			if(x.in)
				in.add(x);
		}

	}
	public boolean roundEnd() {
		boolean a = true;
		for(int i =0; i< in.size() -1; i++) {
			for(int j =1; j < in.size(); j++) {
				if(in.get(i).money == 0 || in.get(j).money == 0) 
					continue;
				if(in.get(i).moneyInFront != in.get(j).moneyInFront)
					a = false;

			}
		}
		return a;
	}
	public void roundWin(Player winner) {
		winner.money += pot;
		for(Player x : players) {
			winner.money += x.moneyInFront;
			x.moneyInFront = 0;
			x.in = true;
		}
		tableCards.clear();
		moveButton();
		newHand();
	}
	public void newHand() {
		deck = new Deck();	
		int count = 0;
		for(int i = button+1;count<2; i++) {
			if(i >= players.length) {
				i = 0;
			}
			if(players[i] != null) {
				count++;
				if(count == 1) {
					players[i].bet(small);
					pot += small;
				}
				else {
					players[i].bet(big);
					pot += big;
				}
			}
		}
		deck.dealPlayers(players, button);
	}
	public void preflop() {
		playersIn();
		int tempButton = in.indexOf(players[button]);
		int marker = (tempButton + 3) % in.size();
		betting(marker);
		flop();
		
		
	}
	public void flop() { 
		tableCards = deck.dealFlop();
		int tempButton = in.indexOf(players[button]);
		int marker = (tempButton + 1) % in.size();
		betting(marker);


	}
	
	public void turn() { 
		tableCards = deck.dealTurnAndRiver(tableCards);
		int tempButton = in.indexOf(players[button]);
		int marker = (tempButton + 1) % in.size();
		betting(marker);
	}
	public void river() {
		tableCards = deck.dealTurnAndRiver(tableCards);
		int tempButton = in.indexOf(players[button]);
		int marker = (tempButton + 1) % in.size();
		betting(marker);
		BestHandEvaluator eval = new BestHandEvaluator(playersInCount());
		roundWin(eval.getBestHand(in, tableCards));
		
		
	}
	public void betting(int marker) {
		boolean a = roundEnd();
		while(!a) {
			for(int i = 0; i< in.size(); i++) {
				in.get(marker).doAction();
				marker = marker + 1  % in.size();
			}
			playersIn();
			if(playersInCount() == 1)
				roundWin(in.get(0));
			a = roundEnd();
		}
	}
}
