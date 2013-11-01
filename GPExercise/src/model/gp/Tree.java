package model.gp;

import java.util.Random;

public class Tree implements Comparable<Tree> {

	private int height; // height of the tree
	private OperatorNode root; // root node of the tree
	private Random rand; // randomizer for generating values and
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
		rand = new Random();
		this.height = height;
		root = new OperatorNode(Tree.setRandomOp(), 0);
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
	//TODO this is probably broken
	private boolean insert(Node node, Node parent) {
		if (parent == null && node instanceof OperatorNode) {
			root = (OperatorNode) node;
			root.setParent(null);
			return true;
		} else if (parent != null && parent instanceof OperatorNode) {
			parent.setLeftChild(node);
			node.setParent(parent);
			return true;
		} else if (parent != null && parent instanceof OperandNode) {
			Node properspot = backtrackToFindValidNode(parent);
			properspot.setRightChild(node);
			node.setParent(properspot);
			return true;

		} else
			return false;
	}

	private Node backtrackToFindValidNode(Node node) {
		if (node.getRightChild() == null)
			return node;
		else
			return backtrackToFindValidNode(node.getParent());
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

	//TODO: rewrite this to take a tree as a parameter. Probably broken too.
	public double eval(Node rootnode, int valueForX) {
		if (rootnode == null) {
			return 0;
		} else if (rootnode instanceof OperatorNode) {
			OperatorNode op = (OperatorNode) rootnode;

			if (op.getValue() == '-') {
				return eval(rootnode.getRightChild(), valueForX)
						- eval(rootnode.getLeftChild(), valueForX);
			} else if (op.getValue() == '*') {
				return eval(rootnode.getRightChild(), valueForX)
						* eval(rootnode.getLeftChild(), valueForX);
			} else if (op.getValue() == '/') {
				double temp = eval(rootnode.getLeftChild(), valueForX);
				if (temp == 0) {
					return eval(rootnode.getRightChild(), valueForX)
							+ temp;
				} else {
					return eval(rootnode.getRightChild(), valueForX)
							/ eval(rootnode.getLeftChild(), valueForX);
				}
			} else {
				return eval(rootnode.getRightChild(), valueForX)
						+ eval(rootnode.getLeftChild(), valueForX);
			}
		} else if (rootnode instanceof OperandNode) {
			OperandNode temp = (OperandNode) rootnode;
			return temp.getValue();
		} else
			return valueForX;
	}
	
	//TODO This is broken. Fix so this works.
	public String getString(Node rootnode) {
		boolean isBottom = isOperandLeaf(rootnode.getLeftChild()) && isOperandLeaf(rootnode.getRightChild()); 
		boolean leftIsBottom = isOperandLeaf(rootnode.getLeftChild()) && !isOperandLeaf(rootnode.getRightChild());
		boolean rightIsBottom = !isOperandLeaf(rootnode.getLeftChild()) && isOperandLeaf(rootnode.getRightChild());
		
		if (isBottom)
		{
			return rootnode.getLeftChild().getStringValue() + rootnode.getStringValue() + rootnode.getRightChild().getStringValue();
		}
		else if (leftIsBottom)
		{
			return rootnode.getLeftChild().getStringValue() + rootnode.getStringValue() + getString(rootnode.getRightChild());
		}
		else if (rightIsBottom)
		{
			return getString(rootnode.getLeftChild()) + rootnode.getStringValue() + rootnode.getRightChild().getStringValue();
		}
		else return "";
	}
	
	private boolean isOperandLeaf(Node node)
	{
		if (node == null)
			return false;
		else if (node.getLeftChild() == null && node.getRightChild() == null)
		{
			if (node instanceof OperatorNode || node instanceof VariableNode){
				return true;
			}
		}
	 return false;
	}

	public int compareTo(Tree t) {
		// TODO
		return 0;
	}

	public int getHeight() {
		return this.height;
	}
	
	public Node getRoot()
	{
		return this.root;
	}

}
