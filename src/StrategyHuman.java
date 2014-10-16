import java.util.ArrayList;
import java.util.Scanner;

class StrategyHuman
	implements Strategy {

	public boolean isAi() {
		return false;
	}

	public int nextMoveFor(World world) {
		return -1;
	}

	/** Asks user for input. */
	public int nextMoveFor(Player player, World world) {
		System.out.print("Make a move for "+ world.getPrinter().getPlayerColor(player)+" player:");
		Scanner in = new Scanner(System.in);
		return in.nextInt();
	}
}

