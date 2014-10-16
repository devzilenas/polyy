import java.io.Serializable;

class Player 
	implements Serializable {

	Strategy strategy;

	public Player(Strategy strategy) {
		setStrategy(strategy);
	}

	public Player() {
		setStrategy(new StrategyRandom());
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	public Strategy getStrategy() {
		return strategy;
	}

	public boolean isAi() {
		return getStrategy().isAi();
	}

	public String toString() {
		return "I am "+(isAi() ? "computer" : "human")+" player. Using strategy " + getStrategy().toString();
	}

	public void talk(int move, World world) {
		System.out.println("Made a move to "+move+" for "+ world.getPrinter().getPlayerColor(this)+" player.");
	}

	public int getNextMoveFor(World world) {
		return getStrategy().nextMoveFor(this, world);
	}

}

