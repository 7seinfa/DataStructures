package BinarySearchTree;

public class BinarySearchTree implements BinarySearchTreeADT{
	private BNode root;
	
	public BinarySearchTree() {
		this.root =  new BNode();
	}
	
	public Pel get (BNode r, Location key) { //check and get Pel from tree, if doesn't exist, return null
		if (r.isLeaf()) return null; //if you reach a leaf, the node doesn't exist
		
		if (key.compareTo(r.getData().getLocus()) == 0) return r.getData(); //if found, return the Pel
		else if (key.compareTo(r.getData().getLocus()) > 0) return get(r.rightChild(), key); //if we are looking for a larger node, go to the right
		else return get(r.leftChild(), key); //if we need a lower node go to the left
	}
	
	public void put (BNode r, Pel newData) throws DuplicatedKeyException { //put a node
		BNode toAdd = getNode(r, newData.getLocus()); //get the correct position of a node
		if (!toAdd.isLeaf()) throw new DuplicatedKeyException("This key is already in the tree."); //if correct position isn't a leaf, that means node is already there
		
		//set data of new node
		toAdd.setContent(newData);
		toAdd.setLeftChild(new BNode());
		toAdd.leftChild().setParent(toAdd);
		toAdd.setRightChild(new BNode());
		toAdd.rightChild().setParent(toAdd);
	}
	
	public void remove (BNode r, Location key) throws InexistentKeyException { //remove a node
		BNode toRemove = getNode(r, key); //get correct position of node
		if (toRemove.isLeaf()) throw new InexistentKeyException("This key doesn't exist."); //if position is a leaf, the node is not in tree
		if (toRemove.leftChild().isLeaf() || toRemove.rightChild().isLeaf()) {
			//if one of the nodes children are leaves, move the other node up in the tree
			BNode child = toRemove.leftChild().isLeaf()?toRemove.rightChild():toRemove.leftChild();
			
			BNode parent = toRemove.parent();
			if (parent != null) {
				if (parent.rightChild() == toRemove) parent.setRightChild(child);
				else parent.setLeftChild(child);
			}else { //if the parent is null, that means we are removing the root, so set the child to root
				root = child;
				root.setParent(null);
			}
		}else { //otherwise, move left branch of right child up so you replace with successor
			BNode smallest = toRemove.rightChild();
			while (!smallest.leftChild().isLeaf()) smallest = smallest.leftChild();
			toRemove.setContent(smallest.getData());
			remove(smallest, smallest.getData().getLocus());
		}
	}
	
	public Pel successor (BNode r, Location key) { //get successor of node or of location (if node doesn't exist)
		if (r.isLeaf()) return null;

		BNode node = getNode(r, key); //get correct location of node
		
		//if node and right child of node are not leaves, get the smallest node from right child as successor
		try {
			if (!node.isLeaf() && !node.rightChild().isLeaf()) return smallest(node.rightChild()); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//otherwise
		node = node.parent(); //go up one
		while (node != null && key.compareTo(node.getData().getLocus()) > 0) node = node.parent(); //keep going up until we are looking at a location greater than key
		if (node == null) return null; //if we reach top and then go above root, successor doesn't exist
		return node.getData();
	}
	
	public Pel predecessor (BNode r, Location key) { //get predecessor of node or of location (if node doesn't exist)
		if (r.isLeaf()) return null;

		BNode node = getNode(r, key); //get correct location of node
		
		//if node and left child are not leaves, get largest node from right child
		try {
			if (!node.isLeaf() && !node.leftChild().isLeaf()) return largest(node.leftChild()); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//otherwise
		node = node.parent(); //go up one
		while (node != null && key.compareTo(node.getData().getLocus()) < 0) node = node.parent(); //keep going up until we are looking at a location less than key
		if (node == null) return null; //if we reach top and then go above root, successor doesn't exist
		return node.getData();
	}
	
	public Pel smallest (BNode r) throws EmptyTreeException{ //get smallest node from tree with root r
		if (r == null || r.isLeaf()) throw new EmptyTreeException("The tree is empty.");
		
		while (!r.leftChild().isLeaf()) { //go all the way to the left until before you reach a leaf
			r = r.leftChild();
		}
		return r.getData();
	}
	
	public Pel largest (BNode r) throws EmptyTreeException{ //get largest node from tree with root r
		if (r == null || r.isLeaf()) throw new EmptyTreeException("The tree is empty.");
		
		while (!r.rightChild().isLeaf()) { //go all the way to the right until before you reach a leaf
			r = r.rightChild();
		}
		return r.getData();
	}
	
	public BNode getRoot() { //get root of tree
		return this.root;
	}
	
	private BNode getNode (BNode r, Location key) { //helper function to get correct location of a node, whether in the tree or not
		if (r.isLeaf()) return r;
		
		if (key.compareTo(r.getData().getLocus()) == 0) return r;
		else if (key.compareTo(r.getData().getLocus()) > 0) return getNode(r.rightChild(), key);
		else return getNode(r.leftChild(), key);
	}
	
}
