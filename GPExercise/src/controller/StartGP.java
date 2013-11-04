package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import model.fitness.tournament.TestData;
import model.fitness.tournament.Tournament;

/**
 * 
 * <p>
 * Practice exercise:
 * <p>
 * Purpose:
 * <p>
 * Use a recursive tree structure to implement a genetic programming algorithm.
 * Generates a random equation, including a variable, that can be printed as a
 * string and evaluated against testing data. Divide testing groups into pairs,
 * comparing fitness to the testing data. Winners will have crossover and
 * mutation operations performed, and tested again. This continues until an
 * equation with an acceptable margin of error is found.
 * 
 * <p>
 * What works:
 * <p>
 * - Tree creation and printing of the equation as a String.
 * <p>
 * - Evaluation of the equation tree given a particular value X.
 * <p>
 * - Receive Testing Data input from user.
 * <p>
 * - Populate Fitness Tournament to calculate distance from Testing Data.
 * <p>
 * - Added mutate and crossover methods to evolve tournament winners.
 * <p>
 * 
 * 
 * What doesn't work:
 * <p>
 * - Everything for this iteration currently functions!
 * <p>
 * What's left to add:
 * <p>
 * - Eventually add GUI.
 */

public class StartGP {

	public static void main(String[] args) throws FileNotFoundException {

		HashMap<Integer, Double> testdata = TestData.getTestValues(new File(
				"res/testdata.txt"));

		Tournament tourney = new Tournament(testdata);
		tourney.testValues();

		while (!tourney.validSolutionExists(0.01)) {
			if (tourney.getPopulation().size() < 2)
				tourney.refreshPopulation();
			tourney.startTournamentRound(tourney.getPopulation());
			tourney.testValues();
			System.out.println(System.currentTimeMillis());
		}

		System.out.println(tourney.getValidSolution().getString(
				tourney.getValidSolution().getRoot()));
	}
}
