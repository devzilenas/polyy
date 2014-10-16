import java.util.Iterator;
import java.util.ArrayList;

class Group
	extends ArrayList<Field> {
	Side side;

	public Side getSide() {
		return side;
	}

	public Group() {
	}
	
	public Group(ArrayList<Field> fields) {
		addAll(fields);
	}

	public void addSideAbilities() {
		side = new Side(this);
	}

	public boolean isConnected() {
		return getConnections() > 0;
	}

	/** 
	 * Returns number of connections to the sides.
	 * If a group has a field from the Side, then a group is connected to the Side.
	 */
	public int getConnections() {
		return getSides().size();
	}

	/** Returns sides this group is connected to. */
	public ArrayList<Group> getSides() {
		ArrayList<Group> sides = new ArrayList<Group>();
		ArrayList<Group> fieldSides = null;
		Field field;
		Group side;
		/** 
		 * Iterate over group fields 
		 * and for each field take sides
		 * it is connected to.
		 */
		for (Iterator<Field> it = iterator() ; it.hasNext(); ) {
			field = it.next();
			fieldSides = field.getWorld().getSidesFor(field);
			for (Iterator<Group> itg = fieldSides.iterator(); itg.hasNext(); ) {
				side = itg.next();
				if (!sides.contains(side)) {
					sides.add(side);
				}
			}
		}
		return sides;
	}

	/**
	 * Group controls a corner 
	 * if a group is connected to 
	 * both sides of this corner
	 * and it is connected to third corner
	 * (number of sides group is connected to is 3 or more).
	 */ 
	public boolean isControlling(Field corner) { 
		Group side;
		Field sideField;
		int controlledSides = 0;
		boolean isControlling = false;


		/** Only groups with 3 or more sides can control a corner. */
		if (getConnections() >= 3) {
			/** 
			 * Iterate over corner sides.
			 * If corner's sides are in group's sides then corner is controlled.
			 */
			for (Iterator<Group> it = corner.getWorld().getSidesFor(corner).iterator() ; it.hasNext(); ) {
				side = it.next();
				if (getSides().contains(side)) { 
					controlledSides++;
				}
			}
			isControlling = (2 == controlledSides);
		}
		return isControlling;
	}

	public String toString() {
		Field field;
		String out = new String();
		for (Iterator<Field> it = iterator(); it.hasNext(); ) {
			field = it.next();
			out += field.getIndex()+",";
		}
		return out;
	}

}
