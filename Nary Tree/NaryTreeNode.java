/**
 * This class represents an n-array tree node, containing children and a data element.
 * @author Hussein Abdallah
 */

public class NaryTreeNode<T> {
	/*
	This is the constructor where we will be
	initializing the data of the node
	*/
	private T data;
	private int numChildren;
	private NaryTreeNode<T>[] children;
	
	/**
	 * Constructor creates an n-array tree node with a data element
	 * @param dataItem the data item to initialize the node with
	 */
	public NaryTreeNode (T dataItem) {
		data = dataItem;
		children = null;
	}
	
	/**
	 * Modifier method to add a child object to this node
	 * @param child the node to be added
	 */
	public void addChild (NaryTreeNode<T> child) {
		numChildren=(children==null)?1:numChildren+1; //if children is null, set the new number of children to 1, else add one to the number
		children=(children==null)?(NaryTreeNode<T>[]) new NaryTreeNode[3]:children; //if children is null, initialize it, else keep it the same
		
		if (numChildren>children.length) { //if max capacity, expand capacity
			expandCapacity();
		}
		
		children[numChildren-1] = child; //set the child
	}
	
	/**
	 * Modifier method to expand the capacity of the children by 3
	 */
	public void expandCapacity () {
		NaryTreeNode<T>[] temp = (NaryTreeNode<T>[]) new NaryTreeNode[children.length+3]; //create a new array with 3 more capacity
		
		for (int i=0; i<children.length; i++) { //move the elements from children to the new array
			temp[i] = children[i];
		}
		
		children = temp; //set children to the new array
	}
	
	/**
	 * Accessor method to get number of children
	 * @return the number of children
	 */
	public int getNumChildren() {
		return numChildren;
	}
	
	/**
	 * Accessor method to get a child element
	 * @param index the index of the child
	 * @return the child
	 */
	public NaryTreeNode<T> getChild(int index){
		return children[index];
	}
	
	/**
	 * Accessor method to get the data element of this node
	 * @return the data element
	 */
	public T getData () {
		return data;
	}
	
	/**
	 * Method to print the data of this node
	 * @return a string of the data of this node
	 */
	public String toString() {
		return "Node containing " + data.toString();
	}
	
}
