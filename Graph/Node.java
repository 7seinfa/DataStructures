/**
 * @author Hussein Abdallah
 */
package Graph;

public class Node {
	private int id; //id of node
	private boolean mark; //boolean to know if node has been visited
	
	public Node (int id) { //set id of node, and start node marked as false
		this.id = id;
		this.mark = false;
	}
	
	public void markNode(boolean mark) { //change mark of node
		this.mark = mark;
	}
	
	public boolean getMark() { //get mark of node
		return this.mark;
	}
	
	public int getId() { //get id of node
		return this.id;
	}
}
