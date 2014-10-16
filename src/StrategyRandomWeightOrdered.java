import java.util.Random   ;
import java.util.ArrayList;
import java.util.Iterator ;
import java.util.Arrays   ;
import java.util.Vector   ;
import java.util.Collections;
import java.lang.Math;
import java.io.Serializable;
import java.util.Map;
import java.util.List;
import java.util.LinkedHashMap;

/**
 * Random strategy prefers fields
 * by weight.
 */
class StrategyRandomWeightOrdered
	extends StrategyRandomWeight
	implements Strategy {

	ArrayList<Field> orderedFields;
	Iterator<Field> it;

	public StrategyRandomWeightOrdered() { }

	public static StrategyRandomWeightOrdered getInstanceFromState() {
		Evolution evolution = new Evolution();
		ArrayList<Player> players = evolution.loadState();
		StrategyRandomWeightOrdered strategy = new StrategyRandomWeightOrdered();
		if (!players.isEmpty()) {
			strategy.setWeights(
					( (StrategyRandomWeightOrdered) players.get(0).getStrategy()).getWeights());
		}
		return strategy;
	}

	/**
	 * Takes a state and outputs single number: the next move.
	 */
	public int nextMoveFor(World world) {
		Random random = new Random();
		/** Order fields by fitness */
		if (null == orderedFields) {
			makeOrdered(world);
		}
		ArrayList<Field> fields = world.getUntouchedFields();
		Field field;
		do {
			field = getOrderedNext();
		} while (!fields.contains(field));
		return field.getIndex();
	}

	public Field getOrderedNext() {
		if (null == it) {
			it = orderedFields.iterator();
		}
		return it.next();
	}

	public void makeOrdered(World world) {
		LinkedHashMap<Field, Integer> ws = new LinkedHashMap<Field, Integer>();
		Field field;
		for (Iterator<Field> it = world.getFields().iterator(); it.hasNext(); ) {
			field = it.next();
			ws.put(field, getWeights()[field.getIndex()]);
		}

		List<Map.Entry<Field, Integer>> list = new ArrayList<Map.Entry<Field, Integer>>(ws.entrySet());
		Collections.sort(list, new ValueReverseComparator());
		orderedFields = new ArrayList<Field>();
		for (Map.Entry<Field, Integer> entry : list) {
			orderedFields.add(entry.getKey());
		}
	}

}

