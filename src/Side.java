import java.util.Iterator;
import java.util.ArrayList;

class Side {

	/** Side knows group it belongs to */
	final Group group;
	public static final int SIDE_LENGTH = 7;

	public static final Integer[][] SIDES_INDEXES = {
		{1  ,   2,   5,  10,  17,  26,  37}, 
		{37 ,  50,  62,  73,  83,  92, 100},
		{100, 101, 102, 103, 104, 105, 106}, 
		{106,  99,  91,  82,  72,  61,  49},
		{49 ,  36,  25,  16,   9,   4,   1}};

	public static int getSideLength() {
		return SIDE_LENGTH;
	}

	public Side(Group group) {
		this.group = group;
	}

	public Group getGroup() {
		return group;
	}

	public static Integer[][] getSidesIndexes() {
		Integer[] side   ;
		Integer[][] sides = new Integer[SIDES_INDEXES.length][];
		for (int i = 0; i < SIDES_INDEXES.length; i++) {
			side = SIDES_INDEXES[i];
			sides[i] = new Integer[side.length];
			for (int j = 0; j < side.length; j++ ) {
				sides[i][j] = side[j] - 1;
			}
		}
		return sides;
	}

	/** Two sides are adjancted if they have one common field. */
	public boolean isAdjanctedTo(Group side) {
		return null != getCommonFieldWith(side);
	}

	public Field getCommonFieldWith(Group side) {
		Field field = null;
		for (Iterator<Field> it = side.iterator(); it.hasNext(); ) {
			field = it.next();
			if (getGroup().contains(field)) {
				break;
			}
		}
		return field;
	}

}

