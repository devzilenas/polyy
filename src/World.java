import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.text.NumberFormat;

/**
 * World is a graph of Fields.
 * This graph:
 * 	is INCOMPLETE graph;
 * 	is IRREGULAR graph;
 * 	is FINITE graph.
 */
class World {

	public static final int SIZE   = 106; //size of the world
	public static final int LEVELS = 13;
	/** Winner is the player that controls three or more corners. */
	public static final int WINNING_CORNERS = 3  ;
	public static final int SWAP            = -1 ;

	int move = 0;

	/** All fields of the world */
	Group fields  = new Group();
	Group corners = new Group();
	ArrayList<Group> sides = new ArrayList<Group>();

	/** Fields graph: first number for field idx, next numbers for connected fields. */
	public static final Integer[][] CONNECTIONS = {
		{1,2,3,4}, {2,1,3,5,6}, {3,1,2,4,6,7,8},
		{4,1,3,8,9}, {5,2,6,10,11}, {6,2,3,5,7,11,12},
		{7,3,6,8,12,13,14}, {8,3,4,7,9,14,15}, {9,4,8,15,16},
		{10,5,11,17,18}, {11,5,6,10,12,18,19},
		{12,6,7,11,13,19,20}, {13,7,12,14,20,21,22},
		{14,7,8,13,15,22,23}, {15,8,9,14,16,23,24},
		{16,9,15,24,25}, {17,10,18,26,27},
		{18,10,11,17,19,27,28}, {19,11,12,18,20,28,29},
		{20,12,13,19,21,29,30}, {21,13,20,22,30,31,32},
		{22,13,14,21,23,32,33}, {23,14,15,22,24,33,34},
		{24,15,16,23,25,34,35}, {25,16,24,35,36},
		{26,17,27,37,38}, {27,17,18,26,28,38,39},
		{28,18,19,27,29,39,40}, {29,19,20,28,30,40,41},
		{30,20,21,29,31,41,42}, {31,21,30,32,42,43,44},
		{32,21,22,31,33,44,45}, {33,22,23,32,34,45,46},
		{34,23,24,33,35,46,47}, {35,24,25,34,36,47,48},
		{36,25,35,48,49}, {37,26,38,50}, {38,26,27,37,39,50,51},
		{39,27,28,38,40,51,52}, {40,28,29,39,41,52,53},
		{41,29,30,40,42,53,54}, {42,30,31,41,43,54,55},
		{43,31,42,44,55,56}, {44,31,32,43,45,56,57},
		{45,32,33,44,46,57,58}, {46,33,34,45,47,58,59},
		{47,34,35,46,48,59,60}, {48,35,36,47,49,60,61},
		{49,36,48,61}, {50,37,38,51,62}, {51,38,39,50,52,62,63},
		{52,39,40,51,53,63,64}, {53,40,41,52,54,64,65},
		{54,41,42,53,55,65,66}, {55,42,43,54,56,66,67},
		{56,43,44,55,57,67,68}, {57,44,45,56,58,68,69},
		{58,45,46,57,59,69,70}, {59,46,47,58,61,70,71},
		{60,47,48,59,61,71,72}, {61,48,49,60,72},
		{62,50,51,63,73}, {63,51,52,62,64,73,74},
		{64,52,53,63,65,74,75}, {65,53,54,64,66,75,76},
		{66,54,55,65,67,76,77}, {67,55,56,66,68,77,78},
		{68,56,57,67,69,78,79}, {69,57,58,68,70,79,80},
		{70,58,59,69,71,80,81}, {71,59,60,70,72,81,82},
		{72,60,61,71,82}, {73,62,63,74,83},
		{74,63,64,73,75,83,84}, {75,64,65,74,76,84,85},
		{76,65,66,75,77,85,86}, {77,66,67,76,78,86,87},
		{78,67,68,77,79,87,88}, {79,68,69,78,80,88,89},
		{80,69,70,79,81,89,90}, {81,70,71,80,82,90,91},
		{82,71,72,81,91}, {83,73,74,81,92},
		{84,74,75,83,85,92,93}, {85,75,76,84,86,93,94},
		{86,76,77,85,87,94,95}, {87,77,78,86,88,95,96},
		{88,78,79,87,89,96,97}, {89,79,80,88,90,97,98},
		{90,80,81,89,91,98,99}, {91,81,82,90,99},
		{92,81,83,93,100}, {93,81,85,92,94,100,101},
		{94,85,86,93,95,101,102}, {95,86,87,94,96,102,103},
		{96,87,88,95,97,103,104}, {97,88,89,96,98,104,105},
		{98,89,90,97,99,105,106}, {99,90,91,98,106},
		{100,92,93,101}, {101,93,94,100,102},
		{102,94,95,101,103}, {103,95,96,102,104},
		{104,96,97,103,105}, {105,97,98,104,106},
		{106,98,99,105}};

	public Player lastMover;

	/** Players */
	public Player[] players;

	public void setPlayers(Player[] players) { 
		this.players = players;
	}

	public Player getPlayer1() {
		return getPlayers()[0];
	}

	public Player getPlayer2() {
		return getPlayers()[1];
	}

	public void setPlayer(int i, Player player) {
		players[i] = player;
	}

	public void setFields(Group fields) {
		this.fields = fields;
	}

	public Group getFields() {
		return fields;
	}

	public void setCorners(Group corners) {
		this.corners = corners;
	}

	public Group getCorners() {
		return corners;
	} 

	public void setSides(ArrayList<Group> sides) {
		this.sides = sides;
	}

	public ArrayList<Group> getSides() {
		return sides;
	} 

	public Player[] getPlayers() {
		return players;
	}

	public static int getSize() {
		return SIZE;
	} 

	public Player getWhitePlayer() {
		return getPlayer1();
	}

	public Player getBlackPlayer() {
		return getPlayer2();
	}

	public int getMoves() {
		return move;
	} 

	public int getLevels() {
		return LEVELS;
	}

	public World(Player p1, Player p2) {
		Player[] players = {p1, p2};
		setPlayers(players);
		initWorld();
	}

	public Integer[][] getConnections() {
		Integer[][] connections = new Integer[CONNECTIONS.length][];
		for (int i = 0 ; i < CONNECTIONS.length; i++) {
			connections[i] = new Integer[CONNECTIONS[i].length];
			for (int j = 0; j < CONNECTIONS[i].length; j++) {
				connections[i][j] = CONNECTIONS[i][j] - 1;
			}
		}
		return connections;
	}

	public Iterator<Field> iterator() {
		return getFields().iterator();
	}

	public boolean isGameEnded() {
		return null != getWinner();
	}

	/** Return winning player */
	public Player getWinner() {
		Player winner = null;
		if (getPlayerCorners(getPlayer1()).size() >= WINNING_CORNERS) {
			winner = getPlayer1();
		} else if (getPlayerCorners(getPlayer2()).size() >= WINNING_CORNERS) {
			winner = getPlayer2();
		}
		return winner;
	}

	/** 
	 * Returns number of corners controlled by player.
	 *
	 * A player controls a corner
	 * if a group (Group) of stones
	 * is connected to two adjancted sides (Side)
	 * of the board and is also connected 
	 * to a third side, the corner (Corner)
	 * between the two adjancted sides (Board)
	 * of the board is controlled 
	 * by this group of stones.
	 */
	public Group getPlayerCorners(Player player) {
		Field corner;
		Group corners = new Group();
		int ret = 0;
		
		/** 
		 * Take each corner and check if the player controls it:
		 * a player controls a corner 
		 * if the player has a group that 
		 * is adjancted to the sides of this corner.
		 */
		/** For each corner */
		for (Iterator<Field> it = getCorners().iterator(); it.hasNext(); ) {
			corner = it.next();

			if (corner.isControlledBy(player)) {
				corners.add(corner);
			}
		}
		return corners;
	}

	private void initWorld() {
		Field field;
		Field neighbour;

		/** Build fields, corners. */
		ArrayList<Integer> cornersIndexes = new ArrayList<Integer>(
				Arrays.asList(Corner.getCornersIndexes()));
		for (int i = 0; i < getSize(); i++) {
			field    = new Field(i, this);
			getFields().add(field);
			if (cornersIndexes.contains(i)) {
				field.addCornerAbilities();
				getCorners().add(field);
			}
		}

		/** Build sides */
		for (int i = 0 ; i < Side.getSidesIndexes().length; i++) {
			sides.add(
					at(Side.getSidesIndexes()[i]));
		}

		/**
		 * Builds two-way graph of fields:
		 * connections.
		 */
		Integer[] connections;
		for (int i=0; i < getConnections().length; i++) {
			connections = getConnections()[i];
			field = at(connections[0]);
			for (int j=0; j < connections.length; j++) {
				neighbour = at(connections[j]);
				field.addNeighbour(neighbour);
			}
		}
	}

	public Field at(Integer idx) {
		return getFields().get(idx);
	}

	public Group at(Integer[] idxs) {
		Group group = new Group();
		for (int i = 0; i < idxs.length; i++) {
			group.add(at(idxs[i]));
		}
		return group;
	}

	public void occupy(int idx, Player player) {
		at(idx).occupyBy(player);
	}

	public void touchBy(Player player, int idx) {
		occupy(idx, player);
	}

	public int neighboursCount(int idx) {
		int neighbours;
		if (1 == idx || 37 == idx || 49 == idx || 100 == idx || 106 == idx) {
			neighbours = 3;
		} else if (2 == idx || 5 == idx || 10 == idx || 17 == idx || 26 == idx 
				|| 50 == idx || 62 == idx || 73 == idx || 83 == idx || 92 == idx 
				|| 101 == idx || 102 == idx || 103 == idx || 104 == idx || 105 == idx 
				|| 99 == idx || 91 == idx || 82 == idx || 72 == idx || 61 == idx 
				|| 36 == idx || 25 == idx || 16 == idx || 9 == idx || 4 == idx) {
			neighbours = 4;
		} else if (43 == 5) {
		} else {
			neighbours = 6;
		}

		return neighbours;
	}

	public boolean isValidMove(Player player, int idx) { 
		return !at(idx).isOccupied();
	}

	public int getTotalMoves() {
		int total = 0;
		for (Iterator<Field> it = iterator(); it.hasNext();) {
			if (it.next().isOccupied()) {
				total++;
			}
		}
		return total;
	}

	public ArrayList<Field> getUntouchedFields() {
		Iterator<Field> it = iterator();
		ArrayList<Field> untouched = new ArrayList<Field>();
		Field field;
		for (;it.hasNext();) {
			field = it.next();
			if (!field.isOccupied()) {
				untouched.add(field);
			}
		}
		return untouched;
	}

	public ArrayList<Field> getTouchedFields() {
		ArrayList<Field> untouched = getUntouchedFields();
		ArrayList<Field> touched = new ArrayList<Field>();
		Field field;
		for (Iterator<Field> it = untouched.iterator(); it.hasNext(); ) {
			field = it.next();
			if (!untouched.contains(field)) {
				touched.add(field);
			}
		}
		return touched;
	}

	public ArrayList<Group> getConnectedStonesFor(Player owner) {
		Field field;
		Field tf   ;
		int[] p = DisjointSets.createSets(getSize()); 
		ArrayList<Group> groups = new ArrayList<Group>();
		/** Initiate groups */ 
		for(Iterator<Field> it = iterator(); it.hasNext(); ) {
			field = it.next();
			if (field.isOccupied()) {
				field.setGroup(new Group());
			}
		}

		for (Iterator<Field> it = iterator(); it.hasNext(); ) {
			field = it.next();
			if (!field.isOwnedBy(owner)) {
				continue;
			}
			// Iterate all it's neighbours.
			for (Iterator<Field> itn = field.getNeighbours().iterator(); itn.hasNext(); ) {
				tf = itn.next();
				// Neighbours with the same owner join the same set.
				if (tf.isOwnedBy(owner)) {
					DisjointSets.unite(p, field.getIndex(), tf.getIndex());
				}
			}
		}

		Group group;
		Field root  ;
		int x;
		for (int i = 0; i < p.length; i++) {
			field = at(i);
			if (field.isOwnedBy(owner)) {
				//Put the field to the same group of field root
				root = at(DisjointSets.root(p, i));
				field.setGroup(root.getGroup());
				if (!groups.contains(root.getGroup())) {
					groups.add(root.getGroup());
				}
			}
		}
		return groups;
	}

	public void swapPlayers() {
		/**
		 * Swap player it means, swap players strategies.
		 */
		Strategy s1 = getPlayer1().getStrategy();
		getPlayer1().setStrategy(getPlayer2().getStrategy());
		getPlayer2().setStrategy(s1);
	}

	public void makeSides() {
		Integer[][] sidesIndexes = Side.getSidesIndexes();

		for (int i = 0; i < sidesIndexes.length; i++) { 
			getSides().add(at(sidesIndexes[i]));
		}
	}

	public ArrayList<Group> getSidesFor(Field corner) {
		ArrayList<Group> ret  = new ArrayList<Group>();
		Group side = null; 
		for (Iterator<Group> it = getSides().iterator(); it.hasNext(); ) {
			side = it.next();
			if (side.contains(corner)) {
				ret.add(side);
			}
		}
		return ret;
	}

	public Player getNextMover() {
		if (null == lastMover) {
			return getStartingPlayer();
		} else if (lastMover == getWhitePlayer()) {
			return getBlackPlayer();
		} else {
			return getWhitePlayer();
		}
	}

	private boolean move(Integer idx, Player player) {
		if (isValidMove(player, idx)) { 
			touchBy(player, idx);
			lastMover = player;
			return true;
		}
		return false;
	}

	public void nextMove() {
		Player player = getNextMover();
		int nextMove  = player.getNextMoveFor(this);
		if (SWAP == nextMove && isSwapAllowed()) {
			swap();
		} else {
			move(nextMove, player);
			move++;
		}
	}

	public boolean isSwapAllowed() { 
		return 1 == getTotalMoves();
	}

	public void swap() { 
		swapPlayers();
	}

	public Player getStartingPlayer() {
		return getWhitePlayer();
	}

	public boolean isAITournament() {
		return getWhitePlayer().isAi() && getBlackPlayer().isAi(); 
	}

	public GameResult getResult() {
		return new GameResult(this);
	}

	public WorldPrinter getPrinter() {
		return new WorldPrinter(this);
	}

	public Player getOpponentOf(Player player) {
		return player == getPlayer1() ? getPlayer2() : getPlayer1();
	}
}


