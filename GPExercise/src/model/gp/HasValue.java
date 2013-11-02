package model.gp;

/**
 * Interface implemented by the Node abstract superclass ensuring that every
 * subclass of Node includes a "getStringValue()" function. Not written as a
 * method in the Node abstract class because "Value" refers to a different data
 * type in each of the subclasses (int for OperandNode, char for VariableNodes
 * and OperatorNodes).
 * 
 */
public interface HasValue {
	public String getStringValue();
}
