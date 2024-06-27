/**
 * This class creates a Map and allows cupid to shoot his arrows and find targets
 * @author Hussein Abdallah
 */

import java.io.FileNotFoundException;
import java.io.IOException;

public class StartSearch {
	/*
	This is the constructor where we will be
	initializing the target map and number of arrows, as well as the stack of paths
	*/
	
	private Map targetMap;
	private int numArrows;
	private int inertia;
	private int direction;
	
	private ArrayStack<MapCell> pathStack;
	
	
	/**
	 * Constructor creates the map and initializes settings from a filename
	 * @param origArray the original square (n by n) array to compress
	 */
	public StartSearch(String filename) throws InvalidMapException, FileNotFoundException, IOException {
			targetMap = new Map(filename);
			pathStack = new ArrayStack<MapCell>();
			targetMap.getStart().markInStack();
			pathStack.push(targetMap.getStart());
			targetMap.setDelay(1000);
			numArrows = targetMap.quiverSize()-1;
	}
	
	public static void main(String[] args) throws InvalidMapException, FileNotFoundException, IOException {
		if (args.length < 1) {
			System.out.println("You must provide the name of the input file");
			System.exit(0);
		}
		//the done boolean will be used to check if the pathing is over, and the maxPathLength int will be used to check the max length
		boolean done = false;
		int maxPathLength = -1;
		if(args.length>1) {
			maxPathLength = Integer.parseInt(args[1]);
		}
		try { //make sure there are no errors when creating the map
			StartSearch s = new StartSearch(args[0]);
			int pathLength = 0; //this will be used to compare to the maxPathLength
			int backtrack = 0; //this will be used to check how far we've backtracked
			int targetsHit = 0; //keep track of how many targets hit
			while (!done) { //while done is false, search for another path
				MapCell next = null; //find the next path cell
				try {
					next = s.nextCell(s.pathStack.peek());
				} catch (InvalidNeighbourIndexException e) {
					System.out.println(e.getMessage());
				}
				pathLength++;
				if(next!=null) { //make sure a path was found
					backtrack=0; //set backtrack to 0 if we move
					if(next==s.pathStack.peek()) { //if the same cell was returned, as noted in the function below,
												   //this indicates the arrow was going too quick and no path was found,
												   //so restart if arrows are available, or end it if none are left
						s.inertia=0;
						MapCell temp = s.pathStack.peek();
						while (!s.pathStack.isEmpty()) { //this pops from the stack, and saves the cell closest to cupid
							if(s.pathStack.peek()!=s.targetMap.getStart())
								temp = s.pathStack.pop();
							else
								s.pathStack.pop();
						}
						temp.markOutStack(); //mark out of stack the cell closest to cupid
						if(s.numArrows>0) {
							pathLength=0;
							s.pathStack.push(s.targetMap.getStart());
							s.numArrows--;
						}else {
							done=true;
						}
					}else { //check if we hit the target, and if so return to start and shoot again if arrows left
						s.pathStack.push(next);
						if(s.pathStack.peek().isTarget()) {
							targetsHit++;
							s.inertia=0;
							backtrack=0;
							MapCell temp = s.pathStack.peek();
							while (!s.pathStack.isEmpty()) {
								if(s.pathStack.peek()!=s.targetMap.getStart())
									temp = s.pathStack.pop();
								else
									s.pathStack.pop();
							}
							temp.markOutStack();
							if(s.numArrows>0) {
								pathLength=0;
								s.pathStack.push(s.targetMap.getStart());
								s.numArrows--;
							}else {
								done=true;
							}
						}
					}
						
				}else if (!s.pathStack.isEmpty()){ //if null was returned, pop the top of the stack
					s.pathStack.pop();
					pathLength--;
					s.inertia--;
					backtrack++;
					if(s.pathStack.isEmpty()) { //if we emptied the stack, shoot again if arrows left, or end it
						if(s.numArrows>0) {
							pathLength=0;
							s.pathStack.push(s.targetMap.getStart());
							s.numArrows--;
						}else {
							done=true;
						}
					}
				}else { //if the stack is empty and null returned, end it
					done=true;
				}
				if(backtrack>3 && !s.pathStack.isEmpty()) { //if back track limit is hit, return to start and shoot again or end
					MapCell temp = s.pathStack.peek();
					while (!s.pathStack.isEmpty()) {
						if(s.pathStack.peek()!=s.targetMap.getStart())
							temp = s.pathStack.pop();
						else
							s.pathStack.pop();
					}
					temp.markOutStack();
					if(s.numArrows>0) {
						s.pathStack.push(s.targetMap.getStart());
						s.numArrows--;
					}else {
						done=true;
					}
					backtrack=0;
				}
				if(maxPathLength!=0&&pathLength==maxPathLength) { //if max path length, shoot again or end
					MapCell temp = s.pathStack.peek();
					while (!s.pathStack.isEmpty()) {
						if(s.pathStack.peek()!=s.targetMap.getStart())
							temp = s.pathStack.pop();
						else
							s.pathStack.pop();
					}
					temp.markOutStack();
					if(s.numArrows>0) {
						s.pathStack.push(s.targetMap.getStart());
						s.numArrows--;
						pathLength=0;
					}else {
						done=true;
					}
				}
				
				if(s.pathStack.isEmpty()) { //another check if stack is empty, and to end if it is
					done=true;
				}
			}
			System.out.println("Cupid hit "+targetsHit+" targets!");
			
			//error catching
		}catch (InvalidMapException e) {
			System.out.println("The map provided is invalid, with the message:\n"+e.getMessage());
			System.exit(0);
		}catch (FileNotFoundException e) {
			System.out.println("The file provided was not found.");
			System.exit(0);
		}catch (IOException e) {
			System.out.println("Unknown IOException occured");
			System.exit(0);
		}
	}
	
	/**
	 * AMethod to find the next map cell cupid's arrow should travel to
	 * @return the next MapCell
	 */
	public MapCell nextCell (MapCell cell) {
		//These variables are to identify which paths are valid
		boolean isHoriz;
		boolean isVert;
		boolean isCross;
		if(cell==targetMap.getStart()) { //if the cell is the start, and direction or path is alright
			isHoriz=true;
			isVert=true;
			isCross=true;
		}else {
			isHoriz=cell.isHorizontalPath();
			isVert=cell.isVerticalPath();
			isCross=cell.isCrossPath();
		}
		
		MapCell top = cell.getNeighbour(0);
		MapCell right = cell.getNeighbour(1);
		MapCell bot = cell.getNeighbour(2);
		MapCell left = cell.getNeighbour(3);
		
		//if any cell is marked or a black hole, set to null so the rest of the code can ignore it
		if(top!=null&&(top.isMarked()||top.isMarkedOutStack()||top.isBlackHole())) top = null;
		if(right!=null&&(right.isMarked()||right.isMarkedOutStack()||right.isBlackHole())) right = null;
		if(bot!=null&&(bot.isMarked()||bot.isMarkedOutStack()||bot.isBlackHole())) bot = null;
		if(left!=null&&(left.isMarked()||left.isMarkedOutStack()||left.isBlackHole())) left = null;
		
		
		if(inertia>3) { //if inertia is greater than 3, either keep going in the same direction, or return 
						//the original cell (which will signal the main code to shoot a new arrow or be finished
			switch(direction) {
			case 0:
				if(top!=null&&(top.isVerticalPath()||top.isCrossPath()||top.isTarget())) {
					inertia++;
					top.markInStack();
					return top;
				}else {
					return cell;
				}
			case 1:
				if(right!=null&&(right.isHorizontalPath()||right.isCrossPath()||right.isTarget())) {
					inertia++;
					right.markInStack();
					return right;
				}else {
					return cell;
				}
			case 2:
				if(bot!=null&&(bot.isVerticalPath()||bot.isCrossPath()||bot.isTarget())) {
					inertia++;
					bot.markInStack();
					return bot;
				}else {
					return cell;
				}
			case 3:
				if(left!=null&&(left.isHorizontalPath()||left.isCrossPath()||left.isTarget())) {
					inertia++;
					left.markInStack();
					return left;
				}else {
					return cell;
				}
			}
		}
		if(inertia>0) { //if the arrow is in movement, first check to see if it can continue the same way
			
			//if the cells aren't the right way, set them as null to be ignored by code
			if(top!=null&&!top.isTarget()&&!(top.isVerticalPath()||top.isCrossPath())) {
				top=null;
			}
			if(right!=null&&!right.isTarget()&&!(right.isHorizontalPath()||right.isCrossPath())) {
				right=null;
			}
			if(bot!=null&&!bot.isTarget()&&!(bot.isVerticalPath()||bot.isCrossPath())) {
				bot=null;
			}
			if(left!=null&&!left.isTarget()&&!(left.isHorizontalPath()||left.isCrossPath())) {
				left=null;
			}
			switch(direction) {
			case 0:
				if(top!=null) {
					inertia++;
					top.markInStack();
					return top;
				}
				break;
			case 1:
				if(right!=null) {
					inertia++;
					right.markInStack();
					return right;
				}
				break;
			case 2:
				if(bot!=null) {
					inertia++;
					bot.markInStack();
					return bot;
				}
				break;
			case 3:
				if(left!=null) {
					inertia++;
					left.markInStack();
					return left;
				}
				break;
			}
		}
		
		//if it can't continue in the same direction, check for any targets
		if(top!=null&&top.isTarget()) {
			inertia = 0;
			top.markInStack();
			return top;
		}else if(right!=null&&right.isTarget()) {
			inertia = 0;
			right.markInStack();
			return right;
		}else if(bot!=null&&bot.isTarget()) {
			inertia = 0;
			bot.markInStack();
			return bot;
		}else if(left!=null&&left.isTarget()) {
			inertia = 0;
			left.markInStack();
			return left;
		}
		
		//if there is no target, check for cross paths, and make sure the paths are compatible 
		if(top!=null&&top.isCrossPath()&&(isVert||isCross)) {
			inertia = 1;
			direction = 0;
			top.markInStack();
			return top;
		}else if(right!=null&&right.isCrossPath()&&(isHoriz||isCross)) {
			inertia = 1;
			direction = 1;
			right.markInStack();
			return right;
		}else if(bot!=null&&bot.isCrossPath()&&(isVert||isCross)) {
			inertia = 1;
			direction = 2;
			bot.markInStack();
			return bot;
		}else if(left!=null&&left.isCrossPath()&&(isHoriz||isCross)) {
			inertia = 1;
			direction = 3;
			left.markInStack();
			return left;
		}
		
		//if there are no cross paths, check for compatible vertical or horizontal paths
		if(top!=null&&(top.isVerticalPath()&&(isVert||isCross))) {
			inertia = 1;
			direction = 0;
			top.markInStack();
			return top;
		} else if(right!=null&&(right.isHorizontalPath()&&(isHoriz||isCross))) {
			inertia = 1;
			direction = 1;
			right.markInStack();
			return right;
		} else if(bot!=null&&(bot.isVerticalPath()&&(isVert||isCross))) {
			inertia = 1;
			direction = 2;
			bot.markInStack();
			return bot;
		} else if(left!=null&&(left.isHorizontalPath()&&(isHoriz||isCross))) {
			inertia = 1;
			direction = 3;
			left.markInStack();
			return left;
		}
		
		//if no path was found, return null
		return null;
	}
}
