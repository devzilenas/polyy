import java.util.Iterator;
import java.util.ArrayList;

class Corner {
	/** corner knows that it is field */
	final Field field;
	public static final int   CORNER_SIDES = 2;
	public static final Integer[] CORNERS = {1, 37, 49, 100, 106};

	private static Integer[] getCorners() {
		return CORNERS;
	}

	public Field getField() {
		return field;
	}

	public static int getCornerSides() {
		return CORNER_SIDES;
	}

	public Corner(Field field) {
		this.field = field;
	}

	public static Integer[] getCornersIndexes() {
		Integer[] cornersIndexes = new Integer[getCorners().length]; 
		for (int i = 0; i < getCorners().length; i++) {
			cornersIndexes[i] = getCorners()[i] - 1;
		}
		return cornersIndexes;
	}

	/**
	 * A corner is controlled by a player 
	 * when a player has a group of stones 
	 * that connects both sides of this corner
	 * and also is connected to the third side. 
	 *
	 * If a group is connected to three sides,
	 * then two of the sides are adjancted.
	 */
	public boolean isControlledBy(Player player) {
		/** All groups of the player */
		ArrayList<Group> groups = getField().getWorld().getConnectedStonesFor(player);
		boolean isControlled = false;
		/** 
		 * Find a group that is connected 
		 * to the sides of the corner.
		 */
		Group group;
		for (Iterator<Group> it = groups.iterator(); it.hasNext(); ) {
			group = it.next();

			if (group.isControlling(getField())) {
				isControlled = true;
				break;
			}

		}
		return isControlled;
	}

}
