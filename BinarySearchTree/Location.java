/**
 * @author Hussein Abdallah
 */
package BinarySearchTree;

public class Location {
	private int x, y;
	
	public Location(int x, int y) { //constructor initializes x and y of location
		this.x = x;
		this.y = y;
	}
	
	public int getx() { //get x of location
		return this.x;
	}
	
	public int gety() { //get y of location
		return this.y;
	}
	
	public int compareTo(Location p) { //compare this location to other location
		if (this.gety() > p.gety() || (this.gety() == p.gety() && this.getx() > p.getx())) return 1; //if this has greater y or same y but greater x, return 1
		else if (this.gety() == p.gety() && this.getx() == p.getx()) return 0; //if the locations equal return 0
		else return -1; //otherwise return -1
	}
}
