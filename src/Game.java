class Game {

	public GameResult play(Player p1, Player p2) {
		World world = new World(p1, p2);
		do {
			world.getPrinter().printAll(null);
			world.nextMove();
		} while (!world.isGameEnded()); 
		System.out.println("The winner is "+world.getWinner().toString());

		return new GameResult(world);
	} 

}

