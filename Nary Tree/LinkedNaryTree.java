/**
 * This class represents an n-array tree, containing a root node
 * @author Hussein Abdallah
 */

import java.util.Iterator;


public class LinkedNaryTree<T> {
	/*
	This is the constructor where we will be
	initializing the root of the tree
	*/
	private NaryTreeNode<T> root;
	
	/**
	 * Constructor creates an n-array tree with a null root
	 */
	public LinkedNaryTree() {
		root = null;
	}
	
	/**
	 * Constructor creates an n-array tree with a root
	 * @param rootOfTree the node to initialize the tree root with
	 */
	public LinkedNaryTree(NaryTreeNode<T> rootOfTree) {
		root = rootOfTree;
	}
	
	/**
	 * Modifier method to add a child node to a parent node
	 * @param parent the node to add the child to
	 * @param child the node to be added
	 */
	public void addNode (NaryTreeNode<T> parent, NaryTreeNode<T> child) {
		parent.addChild(child);
	}
	
	/**
	 * Accessor method to get the root of this tree
	 * @return the root of this tree
	 */
	public NaryTreeNode<T> getRoot(){
		return root;
	}
	
	/**
	 * Accessor method to get the data element of the root of this tree
	 * @return the data element of the root of this tree
	 */
	public T getRootElement() {
		return root.getData();
	}
	
	/**
	 * Method checking if this tree is empty
	 * @return true if the tree is empty
	 */
	public boolean isEmpty() {
		return root==null;
	}
	
	/**
	 * Accessor method to get the number of descendants of a parent node
	 * @param parent the node to cound the number of descendants of
	 * @return the number of descendants of the parent node
	 */
	public int size (NaryTreeNode<T> parent) {
		int size = 1;
		for (int i = 0; i < parent.getNumChildren(); i++) { //for each child, count that child as well as its children, in a recursive manner
			size += size(parent.getChild(i));
		}
		return size;
	}
	
	/**
	 * Method to perform a preorder on a parent node, and adding each to a list
	 * @param parent the node to perform the preorder on
	 * @param list the list to add the items to
	 */
	public void preorder (NaryTreeNode<T> parent, ArrayUnorderedList<T> list) {
		if (parent!=null) {
			list.addToRear(parent.getData()); //add each node to the list
			for (int i = 0; i < parent.getNumChildren(); i++) { //call the method on each of the children in a recursive manner
				preorder(parent.getChild(i), list);
			}
		}
	}
	
	/**
	 * Method to return an iterator of a preorder on the root node
	 * @return the preorder iterator
	 */
	public Iterator<T> iteratorPreorder () {
		ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>(); //the list to perform the preorder with and return its iterator
		preorder(root, tempList);
		return tempList.iterator();
	}
	
	/**
	 * Method to print the items of the tree in a preorder
	 * @return a string of the items of the tree
	 */
	public String toString() {
		if (isEmpty()) return "Tree is empty.";
		Iterator<T> iterator = iteratorPreorder(); //get the iterator of the tree
		String result = "";
		while (iterator!=null) { //for each item in the iterator add it and a new line
			result+=iterator.toString()+"\n";
			iterator.remove();;
		}
		return result;
	}

}
