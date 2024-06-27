/**
 * @author Hussein Abdallah
 */
package Graph;

public class Edge {
	private Node u, v; //first and second node of edge
	private String type; //type of road this edge is
	
	public Edge (Node u, Node v, String type){ //initialize first and second nodes, as well as type of road
		this.u = u;
		this.v = v;
		this.type = type;
	}
	
	public Node firstNode() { //get first node
		return this.u;
	}
	
	public Node secondNode() { //get second node
		return this.v;
	}
	
	public String getType() { //get type of road
		return this.type;
	}
}
