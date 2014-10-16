interface Strategy { 
	int nextMoveFor(World world);
	int nextMoveFor(Player player, World world); 
	boolean isAi();
}

