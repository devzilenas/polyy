import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;

class WorldPrinter {
	World world;

	public void setWorld(World world) {
		this.world = world;
	}

	public World getWorld() {
		return world;
	}

	public WorldPrinter(World world) {
		setWorld(world);
	}

	public void printAll(ArrayList<Group> groups) {
		Field field;
		int offset = 6;
		ArrayList<Group> leveled = getLeveled();
		Group level ;
		int levelI = 0;
		int a = 0;
		String prefix;
		Integer prN;
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

			for(Iterator<Field> itf = level.iterator(); itf.hasNext(); ) {
				field = itf.next();
				prN = field.getIndex();
				if (null != groups) {
					prN = groups.indexOf(field.getGroup());
					if (prN == -1) {
						prN = field.getIndex();
					}
				}
				System.out.print(
						String.format(
							getColorCode(field)+"%02d  ", prN));
			}
			System.out.println();
		}
	}

	/**
	 * Returns groups of fields in levels.
	 * Each field is in level.
	 */
	public ArrayList<Group> getLeveled() {
		ArrayList<Group> levels = new ArrayList<Group>();
		Group level;

		Iterator<Field> it = getWorld().iterator();
		for (int i = 0; i < getWorld().getLevels(); i++) {
			level = new Group();
			for (int j = 0; j < getLevelLength(i); j++) {
				level.add(it.next());
			}
			levels.add(level);
		}

		return levels;
	}

	public int getLevelLength(int level) {
		int length = -1;
		if (level >= 0 && level <= 6) {
			length = level*2 + 1;
		} else if (level == 7) {
			length = 12;
		} else if (level == 8) {
			length = 11;
		} else if (level == 9) {
			length = 10;
		} else if (level == 10) {
			length = 9;
		} else if (level == 11) {
			length = 8;
		} else if (level == 12) {
			length = 7;
		}
		return length;
		// 0. 1
		// 1. 3
		// 2. 5
		// 3. 7
		// 4. 9
		// 5. 11
		// 6. 13
		// 7. 12
		// 8. 11
		// 9. 10
		// 10. 9
		// 11. 8
		// 12. 7
	}

	private String getColorCode(Field field) {
		Player player = field.getPlayer();
		String code = ".";
		if (null != player) {
			code = getWorld().getPlayer1().equals(player) ? "w" : "b";
		}
		return code;
	}

	public void printPlayersInfo() {
		ArrayList<Player> players = new ArrayList<Player>(
				Arrays.asList(getWorld().getPlayers()));
		Player player;
		for (Iterator<Player> it = players.iterator(); it.hasNext(); ) {
			player = it.next();

			System.out.println("Player "+player.toString()+ " controls "+getWorld().getPlayerCorners(player).size()+" corners.");
		}
	}

	public void printFields() {
		System.out.println("Printing fields");
		Field field;
		for (Iterator<Field> it = getWorld().iterator(); it.hasNext(); ) {
			field = it.next();
			System.out.println(field.toString());
		}
	}

	public void printGroups() {
		Group group;
		World world = getWorld();

		ArrayList<Group> bGroups = world.getConnectedStonesFor(
				getWorld().getPlayer2());
		System.out.println("Black groups: "+bGroups.size());
		printAll(bGroups);

		/** Print group information */
		int i = 0;
		for (Iterator<Group> it = bGroups.iterator(); it.hasNext(); ) {
			group = it.next();

			System.out.println("Group:"+i+". "+group.toString());
			System.out.println("Connected to sides");
			printSides(new ArrayList<Group>(Arrays.asList(group)));
			i++;
		}

		ArrayList<Group> wGroups = world.getConnectedStonesFor(
				getWorld().getPlayer1());

		System.out.println("White groups: "+wGroups.size());
		printAll(wGroups);
	}

	public void printSides(ArrayList<Group> sides) {
		Field field;
		Group group;

		for (Iterator<Group> it = sides.iterator(); it.hasNext(); ) {
			group = it.next();
			System.out.print("Side group:");
			for (Iterator<Field> itf = group.iterator(); itf.hasNext(); ) {
				field = itf.next();
				System.out.print(field.getIndex()+",");
			}
			System.out.println();
		}
	}

	public String getPlayerColor(Player player) {
		return player == getWorld().getPlayer1() ? "white" : "black";
	}

}

