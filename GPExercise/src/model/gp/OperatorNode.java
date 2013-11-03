package model.gp;

/**
 * Establishes an operator node for the tree structure, allowing for addition,
 * subtraction, multiplication and division. Checks to ensure valid operator is
 * set before continuing.
 */
class OperatorNode extends Node {

	private char value;

	protected OperatorNode(char value) {
		if (isValidValue(value))
			this.value = value;
	}

	public String getStringValue() {
		return "" + value;
	}

	protected char getValue() {
		return value;
	}

	private boolean isValidValue(char value) {
		return (value == '+' || value == '-' || value == '*' || value == '/');
	}

	protected boolean setValue(char value) {
		if (isValidValue(value)) {
			this.value = value;
			return true;
		} else
			return false;
	}
}
