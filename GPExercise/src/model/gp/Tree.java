package model.gp;

import java.util.Random;
import java.util.Stack;

public class Tree implements Comparable<Tree> {

	private int height;					// height of the tree
	private Stack<Node> tree;			//stack collection for managing the tree
	private OperatorNode root;			//root node of the tree
	private static Random rand;			//randomizer for generating values and randomizing decisions.
	private int numNodesInBottomLevel;	//tracks how many nodes should be in the level about to be created;\

	
	/**
	 * Constructs a "tree" using a Stack algorithm out of OperatorNodes, OperandNodes, and VariableNodes.
	 * 
	 * @param height how many levels deep the tree should grow.
	 */
	public Tree(int height) {
		this.height = height;
		tree = new Stack<Node>();
		root = new OperatorNode(setRandomOp(), 0);
		this.insert(root);
		numNodesInBottomLevel = 1;

		for (int i = 1; i < height; i++) {
			numNodesInBottomLevel = 2 * numNodesInBottomLevel;
			for (int j = 0; j < numNodesInBottomLevel; j++) {
				double randomizer = rand.nextDouble();
				if (i < height - 1 && randomizer < .50) {
					this.insert(new OperatorNode(setRandomOp(), i));
				} else if (randomizer >= .50 && randomizer < .90) {
					this.insert(new OperandNode(rand.nextInt(10), i));
				} else
					this.insert(new VariableNode(i));
			}
		}
	}

	private boolean insert(Node node) {
		if (tree.empty() && node instanceof OperatorNode) {
			tree.add(node);
			return true;
		} else if (!tree.empty() && tree.peek() instanceof OperatorNode) {
			tree.peek().setLeftChild(node);
			tree.add(node);
			return true;
		} else if (!tree.empty() && tree.peek() instanceof OperandNode) {
			Stack<Node> temp = new Stack<Node>();

			for (int i = 0; i < tree.size(); i++) {
				if (tree.peek().getRightChild() == null) {
					tree.peek().setRightChild(node);
					tree.add(node);
					for (int j = 0; j < temp.size(); j++) {
						tree.push(temp.pop());
					}
					return true;
				}

				temp.push(tree.pop());
			}

			for (int i = 0; i < temp.size(); i++) {
				tree.push(temp.pop());
			}
			return false;
		} else
			return false;
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

	public int compareTo(Tree t) {
		// TODO
		return 0;
	}
	
	public int getHeight()
	{
		return this.height;
	}

}
