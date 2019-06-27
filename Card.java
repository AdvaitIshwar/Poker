

public class Card{
	int rank;
	int suit;
	private final static String[] suitNames = {"s", "h", "c", "d"};
	private final static String[] valueNames = {"Unused", "A", "2", "3", "4", 
		"5", "6", "7", "8", "9", "10", "J", "Q", "K"};
	public Card(int rank, int suit) {
		this.suit = suit;
		this.rank = rank;
	}
	public int getValue() {
		return rank;
	}
	public int getSuit() {
		return suit;
	}
	public String getImageFileName() {

		String retValue;
		retValue = suitNames[suit];
		if (rank <= 10)
			retValue += rank;
		else if (rank == 11)
			retValue += "j";
		else if (rank == 12)
			retValue += "q";
		else if (rank == 13)
			retValue += "k";
		else 
			retValue += "Unknown!";
		return "images/" + retValue + ".gif";	
	}
}