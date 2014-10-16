import java.util.Iterator;
import java.util.ArrayList;

class Field {

	/** Field knows about a world it is in. */
	final World world;
	Player  player;
	Group   group;
	int     idx;
	Corner  corner;

	/** Neighbour: is a field that is connected with a distance 1. */
	ArrayList<Field> neighbours;

	public void setCorner(Corner corner) {
		this.corner = corner;
	}

	private Corner getCorner() {
		return corner;
	}

	public boolean isCorner() {
		return null != getCorner();
	}

	public World getWorld() {
		return world;
	}

	public void setNeighbours(ArrayList<Field> neighbours) {
		this.neighbours = neighbours;
	}

	public ArrayList<Field> getNeighbours() {
		return neighbours;
	}

	public void setIndex(int idx) {
		this.idx = idx;
	}

	public int getIndex() {
		return idx;
	}

	public void setGroup(Group group) {
		this.group = group;
		group.add(this);
	}

	public Group getGroup() {
		return group;
	}

	public Player getPlayer() {
		return player;
	}

	public Field(int idx, World world) {
		setNeighbours(new ArrayList<Field>());
		setIndex(idx);
		this.world = world;
	}

	public void addCornerAbilities() {
		setCorner(new Corner(this));
	} 

	public boolean isOwnedBy(Player owner) {
		return owner == getOwner();
	}

	public Group getNeighboursGroup() { 
		return new Group(getNeighbours());
	}

	public int getIndexHuman() {
		return getIndex() + 1;
	}

	public void addNeighbour(Field field) {
		if (!getNeighbours().contains(field)) {
			getNeighbours().add(field);
		}
	}

	public void occupyBy(Player player) {
		this.player = player;
	}

	public Player getOwner() {
		return player;
	}

	public void setOwner(Player player) {
		this.player = player;
	}

	public boolean isOccupied() {
		return null != player;
	}

	public boolean isFree() {
		return !isOccupied();
	}

	public String toString() {
		return "f:"+isFree()
			+",n" + getNeighbours().size();
	}

	public boolean equals(Field field) {
		return getIndex() == field.getIndex();
	}

	public boolean isControlledBy(Player player) {
		return getCorner().isControlledBy(player);
	}
}

