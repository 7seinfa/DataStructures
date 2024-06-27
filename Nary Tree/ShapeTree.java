/**
 * This class represents a shape tree, an n-array tree of shapes following specific conditions
 * @author Hussein Abdallah
 */

public class ShapeTree<T> {
	/*
	This is the constructor where we will be
	initializing the tree
	*/
	private LinkedNaryTree<Shape> tree;
	
	/**
	 * Accessor method to get the n-array tree
	 * @return the tree
	 */
	public LinkedNaryTree<Shape> getTree(){
		return tree;
	}
	
	/**
	 * Accessor method to get the root of the n-array tree
	 * @return the root of the tree
	 */
	public NaryTreeNode<Shape> getRoot(){
		return tree.getRoot();
	}
	
	/**
	 * Modifier method to add a child node to the tree
	 * @param shape the shape to add to the tree
	 */
	public void addShapeNode (Shape shape) {
		if (tree==null) tree = new LinkedNaryTree<Shape>(new NaryTreeNode<Shape>(shape)); //add the shape as the root if the tree is empty
		else { //otherwise add it to the topmost node that is compatible, if possible
			NaryTreeNode<Shape> parent = addShapeNodeHelper(shape);
			if (parent!=null) parent.addChild(new NaryTreeNode<Shape>(shape));
		}
	}
	
	/**
	 * Helper method to attempt to find the highest, leftmost node that the child can be added to 
	 * @param child the shape to add to check
	 * @return the node to add the child to, if possible
	 */
	public NaryTreeNode<Shape> addShapeNodeHelper (Shape child) {
		if (tree==null) return null;
		ArrayStack<NaryTreeNode<Shape>> S = new ArrayStack<NaryTreeNode<Shape>>();
		S.push(tree.getRoot());
		while(!S.isEmpty()) { //perform a stack preorder and visit each node, checking if compatible, and returning it if it is 
			NaryTreeNode<Shape> v = S.pop();
			if (checkNode(v, child)) return v;
			for (int i = v.getNumChildren()-1; i >= 0; i--) {
				S.push(v.getChild(i));
			}
		}
		return null;
	}
	
	/**
	 * Helper method to attempt to check if a child shape can be added to a parent node
	 * @param node the node the compare the child to
	 * @param child the shape to check the node with
	 * @return true if the shape can be added to this node
	 */
	public boolean checkNode (NaryTreeNode<Shape> node, Shape child) {
		if (node.getNumChildren() >= node.getData().getNumSides()) return false; //if the node has maximum children, return false
		if (node.getData().getColour()==child.getColour()) return false; //if the node matches the colour of the child, return false
		for (int i = 0; i < node.getNumChildren(); i++) { //if any of the node's children match the colour of the child, return false
			if (node.getChild(i).getData().getColour()==child.getColour()) return false;
		}
		return true; //otherwise return true
	}
	
	/**
	 * Method to print the items of the tree
	 * @return the toString of the n-array tree
	 */
	public String toString() {
		return tree.toString();
	}

}
