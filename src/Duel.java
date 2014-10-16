import java.util.ArrayList;
import java.util.Iterator;

/**
 * Plays two players one against other.
 */
class Duel
	extends ArrayList<GameResult> {

	public static final int ROUNDS     = 2;
	public static final int MAX_POINTS = 106;

	/**
	 * Win - moves points. Lose - 106 points.
	 * The less points the better.
	 */
	public Duel(Player p1, Player p2) {
		for (int round = 0; round < ROUNDS; round++) {
			add(play(p1, p2));
			add(play(p2, p1));
		}
	}

	private GameResult play(Player p1, Player p2) {
		World world = new World(p1, p2);
		while (!world.isGameEnded()) {
			world.nextMove();
		}
		return new GameResult(world);
	}

	public int getPointsFor(Player player) {
		GameResult gr ;
		int points = 0;
		int add    = 0;
		for (Iterator<GameResult> it = iterator(); it.hasNext(); ) {
			gr = it.next();
			if (player == gr.getWinner()) {
				add = gr.getMoves();
			} else {
				add = MAX_POINTS;
			}
			points += add;
		}
		return points;
	}

}
