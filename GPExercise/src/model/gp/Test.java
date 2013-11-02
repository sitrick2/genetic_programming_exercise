package model.gp;

/**
 * @author sitrick2
 * 
 *         <p>
 *         Practice exercise:
 *         <p>
 *         Purpose:
 *         <p>
 *         Use a recursive tree structure to implement a genetic programming
 *         algorithm. Generate a random equation, including a variable, that can
 *         be printed as a string and evaluated against testing data. Divide
 *         testing groups into pairs, comparing fitness to the testing data.
 *         Winners will have crossover and mutation operations performed, and
 *         tested again. This continues until an equation with an acceptable
 *         margin of error is found.
 * 
 *         <p>
 *         What works:
 *         <p>
 *         - Tree creation and printing of the equation as a String.
 *         <p>
 *         - Evaluation of the equation tree given a particular value X.
 * 
 *         <p>
 *         What doesn't work:
 *         <p>
 *         - Everything for this iteration currently functions!
 * 
 *         <p>
 *         What's left to add:
 *         <p>
 *         - Receive Testing Data input from user.
 *         <p>
 *         - Populate Fitness Tournament to calculate distance from Testing
 *         Data.
 *         <p>
 *         - Add mutate and crossover methods to evolve tournament winners.
 *         <p>
 *         - Eventually add GUI.
 */
public class Test {
	public static void main(String[] args) {
		Tree testtree = new Tree(3);
		System.out.println(testtree.getString(testtree.getRoot()));
		System.out.println(testtree.eval(testtree.getRoot(), 0));
	}
}
