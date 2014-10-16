import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.TreeMap;
import java.util.Map;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Random;
import java.lang.Math;
import java.util.Collections;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.EOFException;
import java.util.NoSuchElementException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * Finds a winning strategy parameters.
 * Only RandomWeighted strategy mutates.
 * My chromosomes is weights.
 * 
 */
class Evolution {
	final int PLAYERS = 12;
	final int MUTANTS = 1;
	static final String STATE = "state.dat";

	int populationCount = PLAYERS;

	public int getPopulationCount() {
		return populationCount;
	} 

	public void setPopulationCount(int count) {
		this.populationCount = count;
	}

	public Evolution(int population) {
		setPopulationCount(population);
	}

	public Evolution() {}

	/**
	 * Fitness is moves
	 */

	/**
	 * The strategy is the better the less moves it does to win.
	 *
	 * Model.
	 * 1) Generate initial population of players.
	 * Iterate:
	 * 	1) Crossover (at random point).
	 * 	2) Mutate.
	 * 	3) Calculate fitness for each player.
	 * 	4) Select best players.
	 * 	5) 
	 *
	 * 	Make initial population (initial Strategies).
	 * 	Make tournament in this population.
	 */
	public void run(int generations) {
		ArrayList<Player> population = new ArrayList<Player>();
		LinkedHashMap<Player, Integer> points ;
		Player p1;
		Player p2;
		Player player;
		/** Make players. */
		population = loadState();
		while (population.size() < getPopulationCount()) {
				player = new Player(
						new StrategyRandomWeight());
				population.add(player);
		}

		for (int generation = 0 ; generation < generations; generation++ ) {
			/**
			 * Premake points
			 */
			points = new LinkedHashMap<Player, Integer>();
			for (Iterator<Player> it = population.iterator(); it.hasNext(); ) {
				points.put(it.next(), 0);
			}

			/**
			 * Duel each player against each and write player's points.
			 */
			for (Iterator<Player> it = population.iterator(); it.hasNext(); ) {
				p1 = it.next();

				for (Iterator<Player> it2 = population.iterator(); it2.hasNext(); ) {
					p2 = it2.next();
					if (p2 == p1) {
						continue;
					}

					Duel duel = new Duel(p1, p2);

					points.put(p1,
							points.get(p1) + duel.getPointsFor(p1));
					points.put(p2,
							points.get(p2) + duel.getPointsFor(p2));
				}
			}

			/** 
			 * Now we have points after duel,
			 * sort points.
			 */
			List<Map.Entry<Player, Integer>> list = new ArrayList<Map.Entry<Player, Integer>>(points.entrySet());
			Collections.sort(list, new ValueComparator());
			Map<Player, Integer> sorted = new LinkedHashMap<Player, Integer>();
			for (Map.Entry<Player, Integer> entry : list) {
				sorted.put(entry.getKey(), entry.getValue());
			}
			printPoints(sorted);

			/** SELECT half of the best players */
			List<Player> bestHalf = Collections.list(Collections.enumeration(sorted.keySet())).subList(0, sorted.size()/2);

			/** CROSSOVER */
			ArrayList<Player> newPopulation = new ArrayList<Player>(bestHalf);

			for (Iterator<Player> it = bestHalf.iterator(); it.hasNext(); ) {
				/** 
				 * Take two consecutive players and crossover them.
				 */
				try {
					p1 = it.next();
					p2 = it.next();
					newPopulation.addAll(
							crossover(p1, p2));
				} catch (NoSuchElementException e) {
					/** Sometimes there is not even number of players */
					break;
				}
			}

			population = newPopulation;

			/** MUTATE members of population */
			int mutants = 0;
			Random random = new Random();
			for (Iterator<Player> it = population.iterator(); it.hasNext(); ) {
				player = it.next();
				/** Mutate at least one member */
				mutateWeight((StrategyRandomWeight) player.getStrategy());
				mutants++;
				if (mutants < MUTANTS) {
					break;
				}
			}

			System.out.println("Generation " + generation);
			/** Print generation */
			for (Iterator<Player> it = population.iterator(); it.hasNext(); ) {
				StrategyRandomWeight srm = (StrategyRandomWeight) it.next().getStrategy();
				srm.printWeights();
			}

			saveState(population);

		}

	}

	public ArrayList<Player> crossover(Player father, Player mother) {
		/** 
		 * Make vectors.
		 */
		StrategyRandomWeight sf = (StrategyRandomWeight) father.getStrategy();
		StrategyRandomWeight sm = (StrategyRandomWeight) mother.getStrategy();
		ArrayList<Vector<Integer>> childrenVectors = crossoverVectors(sf.getVector(), sm.getVector());

		/**
		 * Construct players
		 */
		Integer[] wc1 = new Integer[childrenVectors.get(0).size()];
		childrenVectors.get(0).copyInto(wc1);
		Integer[] wc2 = new Integer[childrenVectors.get(1).size()]; 
		childrenVectors.get(0).copyInto(wc2);

		Player c1 = new Player(
				new StrategyRandomWeight(wc1));
		Player c2 = new Player(
				new StrategyRandomWeight(wc2));
		ArrayList<Player> children = new ArrayList<Player>();
		children.add(c1);
		children.add(c2);

		return children;
	}

	public ArrayList<Vector<Integer>> crossoverVectors(Vector<Integer> v1, Vector<Integer> v2) {

		Random random = new Random();
		ArrayList<Vector<Integer>> children = 
			new ArrayList<Vector<Integer>>();
		/** Crossover point */
		int cp = random.nextInt(v1.size());
		Vector<Integer> c1 = new Vector<Integer>();
		Vector<Integer> c2 = new Vector<Integer>();
		ArrayList<Vector<Integer>> vectors =
			new ArrayList<Vector<Integer>>();
		
		int i = 0;
		Integer value = 0;
		for (Iterator<Integer> it = v1.iterator(); it.hasNext(); i++) {
			value = it.next();
			if (i > cp) {
				value = v2.get(i);
			}
			c1.add(value);
		}
		children.add(c1);

		i = 0;
		for (Iterator<Integer> it = v2.iterator(); it.hasNext(); i++) {
			value = it.next();
			if (i < cp) {
				value = v1.get(i);
			}
			c2.add(value);
			i++;
		}
		children.add(c2);

		return children;
	}

	/** 
	 * Changes one random weight at some position by +/-1.
	 */
	public void mutateWeight(StrategyRandomWeight strategy) {
		Random random = new Random();
		/** Mutate up to 10 genes */
		for (int i = random.nextInt(10); i > 0; i--) {
			int by = (random.nextInt() % 2 == 0 ? random.nextInt(10) : -random.nextInt(10));
			int at = random.nextInt(strategy.getWeights().length); 
			strategy.setWeight(at, strategy.getWeights()[at] + by);
		}
	}

	public void printPoints(Map<Player,Integer> points) {
		Player player;

		for (Map.Entry<Player, Integer> entry : points.entrySet()) {
			player = entry.getKey();
			System.out.println(entry.getValue() + " points for Player "+ player.toString());
		}
	}

	public void saveState(ArrayList<Player> players) {
		try {
			BufferedWriter out = new BufferedWriter(
					new FileWriter(STATE));
			Player player;
			Integer[] weights;
			StrategyRandomWeight strategy;
			for (Iterator<Player> it = players.iterator(); it.hasNext(); ) {
				player = it.next();
				strategy = (StrategyRandomWeight) player.getStrategy();
				weights = strategy.getWeights();
				for (int i = 0; i < weights.length; i++) {
					out.write(weights[i]+" ");
				}
				out.write("\r\n");
			}
			out.close();
		} catch (IOException e) {
			System.out.println("State write error!"+e);
		}
	}

	public ArrayList<Player> loadState() {
		ArrayList<Player> players = new ArrayList<Player>();
		try {
			BufferedReader in = new BufferedReader(
					new FileReader(STATE));
			Scanner scanner;
			Player player;
			int weight;
			int i;
			StrategyRandomWeightOrdered strategy;
			String line;
			while (null != (line = in.readLine())) { 
				scanner = new Scanner(line);
				strategy = new StrategyRandomWeightOrdered();
				i = 0;
				while (scanner.hasNextInt()) {
					weight = scanner.nextInt();
					strategy.setWeight(i, weight);
					i++;
				}
				player = new Player(strategy); 
				players.add(player);
			}
			in.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			System.out.println("State read error!"+e);
		} 
		return players;
	} 
}

