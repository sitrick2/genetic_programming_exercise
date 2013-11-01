package model.gp;

public class Test {
	public static void main(String[] args)
	{
		Tree testtree = new Tree(9);
		System.out.println(testtree.getString(testtree.getRoot()));
		System.out.println(testtree.eval(testtree.getRoot(), 5));
	}
}
