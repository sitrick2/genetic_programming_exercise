package model.gp;

/**
 * Establishes an operand node for the tree structure, with a settable value
 * between 0-9, inclusive.
 */
class OperandNode extends Node {

	private int value;

	protected OperandNode(int value) {
		if (isValidValue(value))
			this.value = value;
	}

	protected int getValue() {
		return value;
	}

	public String getStringValue() {
		return "" + value;
	}

	protected boolean setValue(int value) {
		if (isValidValue(value)) {
			this.value = value;
			return true;
		} else
			return false;
	}

	private boolean isValidValue(int value) {
		return (value >= 0 && value < 10);
	}
}
