package controller.gp;

class VariableNode extends Node {
	
	private char value;
	
	protected VariableNode(int level) {
		super(level);
		value = 'x';
	}
	
	protected VariableNode(int level, Node parent) {
		super(level, parent);
		this.setParent(parent);
		value = 'x';
	}
	
	protected char getValue()
	{
		return value;
	}

}
