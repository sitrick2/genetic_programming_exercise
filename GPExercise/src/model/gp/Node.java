package model.gp;

/**
 * Abstract Node superclass. Ensures that every extended subclass of Node can
 * call getStringValue() (thanks to the HasValue interface), and can get and set
 * parent-child relationships.
 */
abstract class Node implements HasValue {
	private Node parent;
	private Node aChild; // left child
	private Node bChild; // right child

	/**
	 * Gets this instance's left child node.
	 * 
	 * @return the left child node.
	 */
	protected Node getLeftChild() {
		return aChild;
	}

	/**
	 * Gets parent node for current node.
	 * 
	 * @return This instance's parent node.
	 */
	protected Node getParent() {
		return this.parent;
	}

	/**
	 * Gets this instance's right child node.
	 * 
	 * @return The right child node.
	 */
	protected Node getRightChild() {
		return bChild;
	}

	/**
	 * Method to assist Tree crossover. Exchanges this Node's parent with the
	 * replacement's parent.
	 * 
	 * @param replacement
	 */
	protected void replace(Node replacement) {
		Node temp = replacement;
		Node oldparent = this.getParent();
		Node newparent = replacement.getParent();

		if (oldparent != null && newparent != null) {
			if (newparent.getLeftChild().equals(replacement))
				newparent.setLeftChild(this);
			else if (newparent.getRightChild().equals(replacement))
				newparent.setRightChild(this);

			if (oldparent.getLeftChild().equals(this))
				oldparent.setLeftChild(temp);
			else if (oldparent.getRightChild().equals(this))
				oldparent.setRightChild(temp);
		}

	}

	/**
	 * Sets the incoming Node as a child of this Node instance if two child
	 * nodes are already not present. Child node will be the left-child if
	 * empty, right-child if one other child node is already present, or do
	 * nothing if no room.
	 * 
	 * @param child
	 *            Incoming child node
	 */
	protected void setAsChild(Node child) {
		if (aChild == null) {
			aChild = child;
			aChild.setParent(this);
		} else if (bChild == null) {
			bChild = child;
			bChild.setParent(this);
		}
	}

	protected void setLeftChild(Node child) {
		this.aChild = child;
		aChild.setParent(this);
	}

	/**
	 * Sets a parent node for this Node instance.
	 * 
	 * @param parent
	 *            Incoming parent node.
	 */
	protected void setParent(Node parent) {
		this.parent = parent;
	}

	protected void setRightChild(Node child) {
		this.bChild = child;
		bChild.setParent(this);
	}

}
