package controller.gp;

import java.util.Random;
import java.util.Stack;

public class Tree implements Comparable<Tree> {

	private int height;
	private OperatorNode root;
	private OperandNode firstdigit;
	private Stack<Node> opstorage; // stores the operators for reference
	private static Random rand;

	public Tree(int height) {

		rand = new Random();
		this.height = height;
		firstdigit = new OperandNode(rand.nextInt(10), 1);
		root = new OperatorNode(setRandomOp(), 0);

		opstorage.add(firstdigit);
		opstorage.add(root);

		for (int i = 2; i < this.height; i++) {
			// TODO add VariableNode class, implement random equation generation
		}
	}

	public int compareTo(Tree t) {
		// TODO
		return 0;
	}

	private static char setRandomOp() {
		int opdecider; // used with randomizer to determine Node values for
						// operators

		opdecider = rand.nextInt(4);
		char solution;

		switch (opdecider) {
		case 0:
			solution = '+';
			break;
		case 1:
			solution = '-';
			break;
		case 2:
			solution = '*';
			break;
		default:
			solution = '/';
			break;
		}

		return solution;
	}
}
