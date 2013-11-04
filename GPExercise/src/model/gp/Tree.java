package model.gp;

import java.util.HashMap;
import java.util.Random;

/**
 * Creates an equation tree in order to populate the Genetic Programming Fitness
 * Tournament. Equation operations are randomized, as are the operands.
 */
public class Tree implements Comparable<Tree> {

	/**
	 * Interior class used to assist in the mechanics of building up the Tree.
	 */
	private class Branch {
		private Node root;

		/**
		 * Constructor to facilitate recursion in buildTree(). Should not be
		 * called outside of the Branch class.
		 * 
		 * @param root
		 *            Base Node for this trio of Nodes.
		 * @param leftChild
		 *            root's left child Node.
		 * @param rightChild
		 *            root's right child Node.
		 */
		private Branch(Node root, Branch leftChild, Branch rightChild) {
			this.root = root;

			try {
				this.root.setAsChild(leftChild.getRoot());
				this.root.setAsChild(rightChild.getRoot());
			} catch (NullPointerException e) {
			}
		}

		/**
		 * Called by the Tree class, which provides a root Node and the desired
		 * height.
		 * 
		 * @param root
		 *            The root Node of the Tree class.
		 * @param height
		 *            How deep to build the Tree.
		 */
		private Branch(Node root, int height) {
			buildTree(root, height);
		}

		/**
		 * Recursive function that builds up a Tree object to the specified
		 * height.
		 * 
		 * @param root
		 *            Rootnode of the Tree object to be constructed.
		 * @param height
		 *            Height of the Tree.
		 * @return The newly-created Branch. This allows for recursion.
		 */
		private Branch buildTree(Node root, int height) {
			if (height == 0) {
				return new Branch(root, null, null);
			} else if (height == 1) {
				Node left = randomIntOrVariable();
				Node right = randomIntOrVariable();
				return new Branch(root, buildTree(left, height - 1), buildTree(
						right, height - 1));
			} else {
				Node left = new OperatorNode(setRandomOp());
				Node right = new OperatorNode(setRandomOp());
				return new Branch(root, buildTree(left, height - 1), buildTree(
						right, height - 1));
			}
		}

		private Node getRoot() {
			return this.root;
		}

		/**
		 * Creates a random Operand or Variable Node for use in the equation.
		 * Ensures that a Variable value appears 20% of the time.
		 * 
		 * @return Randomized Operand/Variable Node.
		 */
		private Node randomIntOrVariable() {
			Random r = new Random();
			if (r.nextDouble() < .80)
				return new OperandNode(r.nextInt(10));
			else
				return new VariableNode();
		}
	}

	private static char setRandomOp() {
		int opdecider; // used with randomizer to determine Node values for
						// operators
		Random r = new Random();
		opdecider = r.nextInt(4);
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

	private Random r;

	private OperatorNode root; // root node of the tree

	private double fitnessresult; // average distance between the result of
									// eval() and the test solution for each
									// entry in the testing data.

	/**
	 * Constructs a randomized equation tree out of OperatorNodes, OperandNodes,
	 * and VariableNodes.
	 * 
	 * @param height
	 *            Represents how many levels deep the tree should grow.
	 */
	public Tree(int height) {
		r = new Random();
		this.root = new OperatorNode(setRandomOp());
		new Branch((Node) root, height);
		this.fitnessresult = Double.MAX_VALUE;

	}

	public int compareTo(Tree t) {

		return (int) this.fitnessresult - (int) t.getFitness();
	}

	/**
	 * Finds a random node in the tree, removes its attached branch or branches,
	 * and exchanges it with a similar branch from the partner tree.
	 * 
	 * @param partner
	 *            The companion tree to exchange branches with.
	 */
	public void crossover(Tree partner) {
		this.findRandomNode(this.getRoot()).replace(
				partner.findRandomNode(partner.getRoot()));
	}

	/**
	 * Evaluates the equation tree recursively, returning the values of the
	 * bottom Operand Nodes and traversing upward back to the root node. Order
	 * of operations is preserved by treating every branch as a parenthetical.
	 * 
	 * @param rootnode
	 *            Root of the equation tree we're evaluation.
	 * @param valueForX
	 *            The variable "x" value we're testing with this particular
	 *            trial.
	 * @return The result of the equation given valueForX as the value of "x".
	 */
	private double eval(Node rootnode, int valueForX) {
		if (rootnode.getLeftChild() == null && rootnode.getRightChild() == null) {
			if (rootnode instanceof OperandNode) {
				OperandNode temp = (OperandNode) rootnode;
				return temp.getValue();
			} else {
				return valueForX;
			}
		} else {
			OperatorNode op = (OperatorNode) rootnode;
			char operator = op.getValue();

			switch (operator) {
			case '-':
				return eval(rootnode.getLeftChild(), valueForX)
						- eval(rootnode.getRightChild(), valueForX);
			case '*':
				return eval(rootnode.getLeftChild(), valueForX)
						* eval(rootnode.getRightChild(), valueForX);
			case '/':
				double denominator = eval(rootnode.getRightChild(), valueForX);
				double numerator = eval(rootnode.getLeftChild(), valueForX);
				if (denominator == 0)
					return numerator;
				else
					return numerator / denominator;
			default:
				return eval(rootnode.getLeftChild(), valueForX)
						+ eval(rootnode.getRightChild(), valueForX);
			}
		}

	}

	private Node findRandomNode(Node startingnode) {
		double test = r.nextDouble();
		if (startingnode == null)
			return findRandomNode(this.getRoot());
		else if (test < .10)
			return startingnode;
		else if (test >= .10 && test < .55)
			return findRandomNode(startingnode.getLeftChild());
		else
			return findRandomNode(startingnode.getRightChild());
	}

	public double getFitness() {
		return this.fitnessresult;
	}

	public Node getRoot() {
		return this.root;
	}

	/**
	 * Returns a String representation of the equation tree. Uses recursion to
	 * break the equation down into recursive binaries.
	 * 
	 * @param rootnode
	 *            The root of the tree. Essentially the center of the equation
	 *            tree.
	 * @return The equation string. Recursion is used to break the equation into
	 *         easily retrievable binary expressions.
	 */
	public String getString(Node rootnode) {
		if (rootnode.getLeftChild() == null && rootnode.getRightChild() == null)
			return rootnode.getStringValue();
		else
			return "(" + getString(rootnode.getLeftChild()) + ")"
					+ rootnode.getStringValue() + "("
					+ getString(rootnode.getRightChild()) + ")";
	}

	/**
	 * Chooses a random Node in the tree and mutates the value.
	 */
	public void mutate() {
		Node n = this.findRandomNode(this.getRoot());

		if (n instanceof OperatorNode) {
			((OperatorNode) n).setValue(setRandomOp());
		} else if (n instanceof OperandNode) {
			((OperandNode) n).setValue(r.nextInt(10));
		} else {
		}
		;
	}

	/**
	 * Compares the values in the testing data set against the values returned
	 * by the equation tree for the specified value.
	 * 
	 * @param testingdata
	 *            HashMap containing the values to test the Tree object's
	 *            fitness against.
	 * @return The average distance from the Tree's equation to the solution
	 *         provided by the testingdata.
	 */
	public double testFitness(HashMap<Integer, Double> testingdata) {
		double sum = 0;
		int numOfSuccessfulTests = 0;

		for (int i = 0; i < testingdata.size(); i++) {
			try {
				sum += Math.abs(this.eval(this.root, i) - testingdata.get(i));
				++numOfSuccessfulTests;
			} catch (Exception e) {
			}
		}

		if (numOfSuccessfulTests == 0) {
			this.fitnessresult = Double.MAX_VALUE;
		} else {
			this.fitnessresult = sum / numOfSuccessfulTests;
		}

		return this.fitnessresult;

	}
}
