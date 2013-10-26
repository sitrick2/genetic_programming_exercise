package model.gp;

/**
 * Class establishes an operand node for the tree structure.
 */
class OperandNode extends Node {

	private int value;

	protected OperandNode(int value, int level) {
		super(level);

		if (isValidValue(value))
			this.value = value;
	}

	protected OperandNode(int value) {
		super();

		if (isValidValue(value))
			this.value = value;
	}

	protected int getValue() {
		return value;
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
