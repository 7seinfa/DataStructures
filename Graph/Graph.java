/**
 * @author Hussein Abdallah
 */
package Graph;

import java.util.*;

public class Graph implements GraphADT{
	private Node nodeList[]; //save nodes here
	private Edge adjacencyMatrix[][]; //save edges here
	
	public Graph (int n){ //initialize node list and adjacency matrix
		this.nodeList = new Node[n];
		this.adjacencyMatrix =  new Edge[n][n];
		
		for (int i = 0; i < n; i++) { //fill node list with nodes of IDs from 1 0 to n-1
			this.nodeList[i] = new Node(i);
		}
	}
	
	public Node getNode(int id) throws GraphException{ //get node of id
		if (id >= nodeList.length || id < 0) throw new GraphException("No node with id "+id); //if id out of range, throw error
		return this.nodeList[id];
	}
	
	public void addEdge(Node u, Node v, String edgeType) throws GraphException{ //add edge edge between nodes u and v, of edge type edgeType
		//if id of either node out of range, throw error
		if (u.getId() >= nodeList.length || u.getId() < 0
				|| v.getId() >= nodeList.length || v.getId() < 0)
			throw new GraphException("This node doesn't exist.");
		//if edge already between u and v, throw error
		if (adjacencyMatrix[u.getId()][v.getId()]!=null) throw new GraphException("There is already an edge here at nodes with ids "+u.getId()+" and "+v.getId());
		adjacencyMatrix[u.getId()][v.getId()] = new Edge(u, v, edgeType);
		adjacencyMatrix[v.getId()][u.getId()] = new Edge(v, u, edgeType);
	}
	
	public Iterator<Edge> incidentEdges (Node u) throws GraphException{ //get all edges on node
		if (u.getId() >= nodeList.length || u.getId() < 0) throw new GraphException("This node doesn't exist."); //if id out of range, throw error
		List<Edge> edgesAtU = new ArrayList<Edge>();
		
		for (int i = 0; i < nodeList.length; i++) {
			if (adjacencyMatrix[u.getId()][i] != null) edgesAtU.add(adjacencyMatrix[u.getId()][i]);
		}

		return edgesAtU.iterator();
	}
	
	public Edge getEdge (Node u, Node v) throws GraphException{ //get edge between two nodes
		//if either node id out of range, throw error
		if (u.getId() >= nodeList.length || u.getId() < 0 || v.getId() >= nodeList.length || v.getId() < 0) throw new GraphException("This node doesn't exist.");
		//if no edge on these nodes, throw error
		if (adjacencyMatrix[u.getId()][v.getId()] == null) throw new GraphException("Edge doesn't exist");
		return adjacencyMatrix[u.getId()][v.getId()];
	}
	
	public boolean areAdjacent (Node u, Node v) throws GraphException{ //check if edge between two nodes
		//if either node id out of range, throw error
		if (u.getId() >= nodeList.length || u.getId() < 0 || v.getId() >= nodeList.length || v.getId() < 0) throw new GraphException("This node doesn't exist.");
		
		if (adjacencyMatrix[u.getId()][v.getId()] == null) return false;
		return true;
	}
	
}
