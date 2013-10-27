package model.gp;

import java.util.Random;

public class Tree implements Comparable<Tree> {

	private int height; // height of the tree
	private OperatorNode root; // root node of the tree
	private static Random rand; // randomizer for generating values and
								// randomizing decisions.
	private int numNodesInBottomLevel; // tracks how many nodes should be in the
										// level about to be created
	private Node lastCreatedNode;

	/**
	 * Constructs a tree out of OperatorNodes, OperandNodes, and VariableNodes.
	 * 
	 * @param height
	 *            how many levels deep the tree should grow.
	 */
	public Tree(int height) {
		this.height = height;
		root = new OperatorNode(setRandomOp(), 0);
		this.insert(root, null);
		numNodesInBottomLevel = 1;
		lastCreatedNode = root;

		for (int i = 1; i < height; i++) {
			numNodesInBottomLevel = 2 * numNodesInBottomLevel;
			for (int j = 0; j < numNodesInBottomLevel; j++) {
				double randomizer = rand.nextDouble();
				if (i < height - 1 && randomizer < .50) {
					OperatorNode temp = new OperatorNode(setRandomOp(), i);
					this.insert(temp, lastCreatedNode);
					lastCreatedNode = temp;
				} else if (randomizer >= .50 && randomizer < .90) {
					OperandNode temp = new OperandNode(rand.nextInt(10), i);
					this.insert(temp, lastCreatedNode);
					lastCreatedNode = temp;
				} else {
					VariableNode temp = new VariableNode(i);
					this.insert(temp, lastCreatedNode);
					lastCreatedNode = temp;
				}
			}
		}
	}

	private boolean insert(Node node, Node parent) {
		if (parent.equals(null) && node instanceof OperatorNode) {
			root = (OperatorNode) node;
			root.setParent(null);
			return true;
		} else if (!parent.equals(null) && parent instanceof OperatorNode) {
			parent.setLeftChild(node);
			node.setParent(parent);
			return true;
		} else if (!parent.equals(null) && parent instanceof OperandNode) {
			Node properspot = backtrackToFindValidNode(parent);
			properspot.setRightChild(node);
			node.setParent(properspot);
			return true;

		} else
			return false;
	}

	private Node backtrackToFindValidNode(Node node) {
		if (node.getRightChild().equals(null))
			return node;
		else
			return backtrackToFindValidNode(node.getParent());
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

	public double eval(Node root, int valueForX) {
		if (root.equals(null)) {
			return 0;
		} else if (root instanceof OperatorNode) {
			OperatorNode op = (OperatorNode) root;

			if (op.getValue() == '-') {
				return eval(root.getRightChild(), valueForX)
						- eval(root.getLeftChild(), valueForX);
			} else if (op.getValue() == '*') {
				return eval(root.getRightChild(), valueForX)
						* eval(root.getLeftChild(), valueForX);
			} else if (op.getValue() == '/') {
				double temp = eval(root.getLeftChild(), valueForX);
				if (temp == 0) {
					return eval(root.getRightChild(), valueForX)
							+ eval(root.getLeftChild(), valueForX);
				} else {
					return eval(root.getRightChild(), valueForX)
							/ eval(root.getLeftChild(), valueForX);
				}
			} else {
				return eval(root.getRightChild(), valueForX)
						+ eval(root.getLeftChild(), valueForX);
			}
		} else if (root instanceof OperandNode) {
			OperandNode temp = (OperandNode) root;
			return temp.getValue();
		} else
			return valueForX;

	}

	public int compareTo(Tree t) {
		// TODO
		return 0;
	}

	public int getHeight() {
		return this.height;
	}

}
