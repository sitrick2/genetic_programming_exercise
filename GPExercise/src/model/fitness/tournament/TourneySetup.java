package model.fitness.tournament;

import java.util.ArrayList;

import model.gp.Tree;

/**
 * Sets up conditions to begin a Tournament instance.
 */
class TourneySetup {

	private static ArrayList<Tree> testtrees;

	/**
	 * Creates a new population of equation Trees to take part in the
	 * Tournament.
	 * 
	 * @return The initialized population of equation Trees.
	 */
	protected static ArrayList<Tree> setupTourney() {
		testtrees = new ArrayList<Tree>();
		for (int i = 0; i < 50; i++) {
			Tree temp = new Tree(3);
			testtrees.add(temp);
		}
		return testtrees;
	}
}
