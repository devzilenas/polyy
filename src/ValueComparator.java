import java.util.Map;
import java.util.Comparator;

class ValueComparator 
	implements Comparator { 
	public int compare(Object o1, Object o2) {
		Map.Entry<Player, Integer> e1 = (Map.Entry<Player,Integer>) o1;
		Map.Entry<Player, Integer> e2 = (Map.Entry<Player,Integer>) o2;
		return e1.getValue().compareTo(e2.getValue());
	}
}
