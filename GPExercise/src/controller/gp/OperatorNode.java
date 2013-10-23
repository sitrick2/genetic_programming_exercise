package controller.gp;

/**
 * 
 * @author sitrick2 Class establishes an operator node for the tree structure.
 */
public class OperatorNode extends Node {

	private char value;

	protected OperatorNode(char value, int level) {
		super(level);

		if (value == '+' || value == '-' || value == '*' || value == '/')
			this.value = value;
	}

	protected char getValue() {
		return value;
	}

	protected boolean setValue(char value) {
		if (value == '+' || value == '-' || value == '*' || value == '/') {
			this.value = value;
			return true;
		} else
			return false;
	}
}
