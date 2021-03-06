package model.gp;

/**
 * 
 * Node class to represent a variable in the generated equation. Only using a
 * single variable in the equation at this time, thus hard-coding "x" as the
 * value.
 * 
 */
class VariableNode extends Node {

	private char value;

	protected VariableNode() {
		value = 'x';
	}

	@Override
	public String getStringValue() {
		return "x";
	}

	protected char getValue() {
		return value;
	}

}
