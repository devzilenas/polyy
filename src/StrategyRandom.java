import java.util.Random;
import java.util.ArrayList;

class StrategyRandom 
	implements Strategy {

	public boolean isAi() {
		return true;
	}

	/**
	 * Takes a state and outputs single number: the next move.
	 */
	public int nextMoveFor(World world) {
		Random random = new Random();
		/** Take random from unnocuppied fields */
		ArrayList<Field> fields = world.getUntouchedFields();
		return fields.get(random.nextInt(fields.size())).getIndex();
	}

	public int nextMoveFor(Player player, World world) {
		int move = nextMoveFor(world);
		if (!world.getOpponentOf(player).isAi()) {
			player.talk(move, world);
		}
		return move;
	}

}

