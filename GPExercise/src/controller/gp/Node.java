package controller.gp;

/**
 * abstract superclass for Nodes. Establishes all the methods every node should
 * have (getters/setters, treelevel, parent/child nodes, etc.)
 */
abstract class Node {
	private Node parent;
	private Node aChild;
	private Node bChild;
	private int level;

	/**
	 * New node with no parent set.
	 */
	protected Node(int level) {
		this.parent = null;
		this.aChild = null;
		this.bChild = null;
		this.level = level;
	}

	/**
	 * New node with parent set.
	 */
	protected Node(Node parent, int level) {
		this.parent = parent;
		aChild = null;
		bChild = null;
		this.level = level;
	}

	/**
	 * Sets parent node for current node.
	 * 
	 * @return true if successful.
	 */
	protected boolean setParent(Node parent) {
		if (this.parent == null) {
			this.parent = parent;
			return true;
		} else
			return false;
	}

	/**
	 * Sets child node for current node.
	 * 
	 * @return true if successful.
	 */
	protected boolean setChild(Node child) {
		if (aChild == null) {
			aChild = child;
			return true;
		} else if (bChild == null) {
			bChild = child;
			return true;
		} else
			return false;
	}

	protected Node getParent() {
		return this.parent;
	}

	protected Node[] getChildren() {
		Node[] children = { aChild, bChild };

		return children;
	}

	protected int getLevel() {
		return this.level;
	}
}
