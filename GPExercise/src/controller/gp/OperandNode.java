package controller.gp;

/**
 * 
 * @author sitrick2 Class establishes an operand node for the tree structure.
 */
public class OperandNode extends Node {

	private int value;

	protected OperandNode(int value, int level) {
		super(level);

		if (value == '+' || value == '-' || value == '*' || value == '/')
			this.value = value;
	}

	protected int getValue() {
		return value;
	}

	protected boolean setValue(int value) {
		if (value == '+' || value == '-' || value == '*' || value == '/') {
			this.value = value;
			return true;
		} else
			return false;
	}
}
