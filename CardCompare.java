import java.util.Comparator;

public class CardCompare implements Comparator<Card>{
	@Override
	public int compare(Card one, Card two) {
		if(one.rank > two.rank) {
			return 1;
		} else if(one.rank < two.rank) {
			return -1;
		} else {
			return 0;
		}
	}
}