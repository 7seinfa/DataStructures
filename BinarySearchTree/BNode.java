/**
 * @author Hussein Abdallah
 */
package BinarySearchTree;

public class BNode {
	private Pel value;
	private BNode left, right, parent;
	
	public BNode (Pel value, BNode left, BNode right, BNode parent) { //constructor to initialize all values of BNode
		this.value = value;
		this.left = left;
		this.right = right;
		this.parent = parent;
	}
	
	public BNode () { //constructor to create BNode with null values
		this.value = null;
		this.left = null;
		this.right = null;
		this.parent = null;
	}
	
	public BNode parent() { //get parent
		return this.parent;
	}
	
	public void setParent(BNode newParent) { //set parent
		this.parent = newParent;
	}
	
	public void setLeftChild(BNode p) { //set left child
		p.setParent(this);
		this.left = p;
	}
	
	public void setRightChild(BNode p) { //set right child
		p.setParent(this);
		this.right = p;
	}
	
	public void setContent(Pel value) { //set content stored as Pel
		this.value = value;
	}
	
	public boolean isLeaf() { //check if this is a leaf
		if (this.left == null && this.right == null) return true;
		else return false;
	}
	
	public Pel getData() { //get Pel data
		return this.value;
	}
	
	public BNode leftChild() { //get left child
		return this.left;
	}
	
	public BNode rightChild() { //get right child
		return this.right;
	}
}
