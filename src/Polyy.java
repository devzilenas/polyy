/**
 * A program for playing Poly-Y board game.
 * Copyright 2013 Marius Zilenas <mzilenas@gmail.com>
 */
public class Polyy {

	public static final String AI         = "ai"        ;
	public static final String HUMAN      = "human"     ;
	public static final String EVOLVE     = "evolve"    ;

	public static void main(String[] args) {
		if (EVOLVE.equals(args[0])) {
			Evolution evolution;
			if (args.length > 2) {
				evolution = new Evolution(Integer.valueOf(args[2]));
			} else {
				evolution = new Evolution();
			}
			evolution.run(Integer.valueOf(args[1]));
		} else {
			Player p1 = new Player(new StrategyRandom());
			Player p2 = new Player(new StrategyRandom());
			if (HUMAN.equals(args[0])) { 
				p1 = new Player(new StrategyHuman());
			}
			if (HUMAN.equals(args[1])) { 
				p2 = new Player(new StrategyHuman());
			}
			(new Game().play(p1,p2)).print();
		}
	}

}
