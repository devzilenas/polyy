import java.lang.Comparable;

class GameResult 
	implements Comparable {

	World  world ;

	public void setWorld(World world) {
		this.world = world;
	}

	public World getWorld() {
		return world;
	}

	public Player getWinner() {
		return getWorld().getWinner();
	}

	public int getMoves() {
		return getWorld().getMoves();
	}

	public GameResult(World world) {
		setWorld(world);
	}

	public int compareTo(Object gr) {
		return ((Integer) getMoves()).compareTo( ((GameResult) gr).getMoves());
	}

	public void print() {
		System.out.println("The winner in "+ getWorld().getMoves() +" moves is "+getWorld().getPrinter().getPlayerColor(getWinner())+" "+getWinner().toString());
	}
}

