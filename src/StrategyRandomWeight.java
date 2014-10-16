import java.util.Random   ;
import java.util.ArrayList;
import java.util.Iterator ;
import java.util.Arrays   ;
import java.util.Vector   ;
import java.util.Collections;
import java.lang.Math;
import java.io.Serializable;

/**
 * Random strategy prefers fields
 * by weight.
 */
class StrategyRandomWeight
	implements Strategy, Serializable {

	Integer[] weights = {
		          1,
		       1, 1, 1,
		    1, 1, 1, 1, 1,
		 1, 1, 1, 1, 1, 1, 1,
	  1, 1, 1, 1, 1, 1, 1, 1, 1,
   1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
  1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
      1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1,
          1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1
	};

	ArrayList<Integer> randomField;

	public Integer[] getWeights() {
		return weights;
	}

	public void setWeights(Integer[] weights) {
		this.weights = weights;
	}

	public void setWeight(int idx, int weight) {
		getWeights()[idx] = weight;
	}

	public StrategyRandomWeight() { }

	public static StrategyRandomWeight getInstanceFromState() {
		Evolution evolution = new Evolution();
		ArrayList<Player> players = evolution.loadState();
		StrategyRandomWeight strategy = new StrategyRandomWeight();
		if (!players.isEmpty()) {
			strategy.setWeights(
					( (StrategyRandomWeight) players.get(0).getStrategy()).getWeights());
		}
		return strategy;
	}

	public StrategyRandomWeight(Integer[] weights) {
		setWeights(weights);
	}

	public boolean isAi() {
		return true;
	}

	/**
	 * Takes a state and outputs single number: the next move.
	 */
	public int nextMoveFor(World world) {
		Random random = new Random();
		/** Take random from unnocuppied fields */
		ArrayList<Field> fields = world.getUntouchedFields();
		ArrayList<Field> randomFields = makeRandom(fields);
		int r = random.nextInt(randomFields.size());
		return randomFields.get(r).getIndex();
	}

	public int nextMoveFor(Player player, World world) {
		return nextMoveFor(world);
	}

	public ArrayList<Field> makeRandom(ArrayList<Field> fields) {
		ArrayList<Field> rf = new ArrayList<Field>();
		int weight;
		Field field;
		int zero = getGroundZero();
		for (Iterator<Field> it = fields.iterator(); it.hasNext(); ) {
			field  = it.next();
			weight = weights[field.getIndex()]; 
			/** 
			 * Make a list of fields. 
			 * Each field occurs weight+1 times in this list. 
			 * Every field must occur at least once because then it is error on some cases.
			 */
			for (int j = 0; j < zero + 1 + weight; j++) {
				rf.add(field);
			}
		}
		return rf;
	}

	/**
	 * Finds lowest weight.
	 */
	public Integer getGroundZero() {
		return Math.abs(Collections.min(Arrays.asList(getWeights())));
	}

	public Vector<Integer> getVector() {
		return new Vector<Integer>(Arrays.asList(weights));
	}

	public void printWeights() {
		World world = new World(
				new Player(), new Player());

		ArrayList<Group> leveled = world.getPrinter().getLeveled();
		Group level = null;
		int levelI = 0;
		int offset = 6;
		int a = 0;
		int prN = 0;
		int w = 0;
		String prefix;
		Field field;
		for (Iterator<Group> it = leveled.iterator(); it.hasNext(); ) {
			level = it.next();

			// Generate prefix
			if (levelI < 6) {
				prefix = "";
				for (int j = offset - levelI; j > 0 ; j--) {
					prefix += "     ";
				}
				levelI++;
			} else {
				prefix = "";
				for (int j = 0; j < a ; j++) {
					prefix += "  ";
				}
				a++;
			}

			/** Print prefix */
			System.out.print(prefix);

			/** Print fields */
			for(Iterator<Field> itf = level.iterator(); itf.hasNext(); ) {
				field = itf.next();
				System.out.print(
						String.format("%03d  ", getWeights()[w]));
				w++;
			}
			
			System.out.println();

		}
	}

}

