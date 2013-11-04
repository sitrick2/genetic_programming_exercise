package model.fitness.tournament;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import model.gp.Tree;

/**
 * Manages the Fitness Tournament between trees to determine which Trees will
 * move on to the next evolution cycle.
 */
public class Tournament {

	private HashMap<Integer, Double> testdata;
	private ArrayList<Tree> population;
	private Tree solution;

	/**
	 * Creates and sets up conditions for a competition between pairs of Trees
	 * to see which equation Trees will survive to the next evolution cycle.
	 * 
	 * @param testdata
	 *            Testing Data to evaluate competitor Trees against.
	 */
	public Tournament(HashMap<Integer, Double> testdata) {
		solution = null;
		this.testdata = testdata;
		population = TourneySetup.setupTourney();
	}

	public ArrayList<Tree> getPopulation() {
		return this.population;
	}

	public Tree getValidSolution() {
		return this.solution;
	}

	public void refreshPopulation() {
		this.population = TourneySetup.setupTourney();
	}

	/**
	 * Initializes the tournament round, comparing pairs of equation Trees and
	 * keeping the Tree with a better Fitness value. If there is an odd number
	 * of Trees in the population, the final Tree automatically survives, and is
	 * moved to the first position for the next round to ensure it gets tested.
	 * 
	 * @param pop
	 *            The population of Tree competitors at the start of the
	 *            Tournament Round.
	 * @return The new population of survivors ready for crossover or mutation.
	 */
	public ArrayList<Tree> startTournamentRound(ArrayList<Tree> pop) {
		ArrayList<Tree> winners = new ArrayList<Tree>();
		Random r = new Random();

		if (pop.size() % 2 != 0)
			winners.add(pop.get(pop.size() - 1));

		for (int i = 1; i < pop.size(); i += 2) {
			if (pop.get(i).compareTo(pop.get(i - 1)) >= 0) {
				winners.add(pop.get(i));
			} else {
				winners.add(pop.get(i - 1));
			}
		}
		this.population = winners;

		for (int i = 1; i < this.population.size(); i += 2) {
			double randomizer = r.nextDouble();
			if (randomizer < .05) {
				population.get(i).mutate();
			} else if (randomizer >= .05 && randomizer < .10)
				population.get(i - 1).mutate();

			population.get(i).crossover(population.get(i - 1));
		}

		return this.population;
	}

	/**
	 * Runs the Fitness Test for each Tree in the population.
	 */
	public void testValues() {
		for (Tree t : population) {
			t.testFitness(testdata);
		}
	}

	/**
	 * Checks to see if a solution within the specified margin of error exists.
	 * If a valid solution exists, saves the tree in the solution instance
	 * variable.
	 * 
	 * @param errormargin
	 *            Acceptable margin of error to return a valid equation.
	 * @return True if a valid solution exists, false if not.
	 */
	public boolean validSolutionExists(double errormargin) {
		for (Tree t : this.population) {
			if (t.getFitness() < errormargin) {
				this.solution = t;
				return true;
			}
		}

		return false;
	}
}
