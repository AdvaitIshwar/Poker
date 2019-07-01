public class Card{
	int rank;
	int suit;
	private final static String[] suitNames = {"s", "h", "c", "d"};
	private final static String[] valueNames = {"Unused", "2", "3", "4", 
		"5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
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
		if (rank <= 9)
			retValue += rank;
		else if (rank == 10)
			retValue += "j";
		else if (rank == 11)
			retValue += "q";
		else if (rank == 12)
			retValue += "k";
		else if (rank == 13)
			retValue += "1";
		else 
			retValue += "Unknown!";
		return "images/" + retValue + ".gif";	
	}
	public String toString() {
		return valueNames[rank] + " of " + suitNames[suit];
	}
}