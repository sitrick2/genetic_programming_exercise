package model.gp;

/**
 * abstract superclass for Nodes. Establishes all the methods every node should
 * have (getters/setters, treelevel, parent/child nodes, etc.)
 */
abstract class Node implements HasValue {
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
	protected Node() {
		this.parent = null;
		aChild = null;
		bChild = null;
		this.level = 0;
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
	protected void setLeftChild(Node child) {
			aChild = child;
	}
	
	protected void setRightChild(Node child) {
		bChild = child;
}

	protected Node getParent() {
		return this.parent;
	}

	protected Node getLeftChild() {
				return aChild;
	}
	
	protected Node getRightChild() {
		return bChild;
}

	protected int getLevel() {
		return this.level;
	}
}
