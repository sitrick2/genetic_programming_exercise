package model.gp;

import java.util.Random;

/**
 * Creates an equation tree in order to populate the Genetic Programming Fitness
 * Tournament. Equation operations are randomized, as are the operands.
 */
public class Tree implements Comparable<Tree> {

	private OperatorNode root; // root node of the tree

	/**
	 * Constructs a randomized equation tree out of OperatorNodes, OperandNodes,
	 * and VariableNodes.
	 * 
	 * @param height
	 *            Represents how many levels deep the tree should grow.
	 */
	public Tree(int height) {
		this.root = new OperatorNode(setRandomOp());
		new Branch((Node) root, height);

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
	public double eval(Node rootnode, int valueForX) {
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

	public String getString(Node rootnode) {
		// TODO
		if (rootnode.getLeftChild() == null && rootnode.getRightChild() == null)
			return rootnode.getStringValue();
		else
			return "(" + getString(rootnode.getLeftChild()) + ")"
					+ rootnode.getStringValue() + "("
					+ getString(rootnode.getRightChild()) + ")";
	}

	public int compareTo(Tree t) {
		// TODO
		return 0;
	}

	public Node getRoot() {
		return this.root;
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

	private class Branch {
		private Node root;

		private Branch(Node root, Branch leftChild, Branch rightChild) {
			this.root = root;
			try {
				this.root.setAsChild(leftChild.getRoot());
				this.root.setAsChild(rightChild.getRoot());
			} catch (NullPointerException e) {
			}
		}

		private Branch(Node root, int height) {
			buildTree(root, height);
		}

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

		/**
		 * Creates a random Operand or Variable Node for use in the equation.
		 * Ensures that a Variable value appears at least 10% of the time.
		 * 
		 * @return Randomized Operand/Variable Node.
		 */
		private Node randomIntOrVariable() {
			Random r = new Random();

			if (r.nextDouble() < .90)
				return new OperandNode(r.nextInt(10));
			else
				return new VariableNode();
		}

		private Node getRoot() {
			return this.root;
		}
	}

}
