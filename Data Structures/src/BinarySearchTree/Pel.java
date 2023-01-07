
public class Pel {
	private Location p;
	private int color;
	
	public Pel (Location p, int color) { //constructor to initialize location and color
		this.p = p;
		this.color = color;
	}
	
	public Location getLocus() { //get location
		return this.p;
	}
	
	public int getColor() { //get color
		return this.color;
	}
}
