package controller.gp;

/**
 * 
 * @author sitrick2 Class establishes an operator node for the tree structure.
 */
class OperatorNode extends Node {

	private char value;

	protected OperatorNode(char value, int level) {
		super(level);

		if (isValidValue(value))
			this.value = value;
	}

	protected OperatorNode(char value, int level, Node parent) {
		super(level, parent);

		if (isValidValue(value))
			this.value = value;
	}

	protected char getValue() {
		return value;
	}

	protected boolean setValue(char value) {
		if (isValidValue(value)) {
			this.value = value;
			return true;
		} else
			return false;
	}

	private boolean isValidValue(char value) {
		return (value == '+' || value == '-' || value == '*' || value == '/');
	}
}
