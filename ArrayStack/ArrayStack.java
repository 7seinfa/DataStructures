/**
 * This class represents an array stack, with specific functions.
 * @author Hussein Abdallah
 */



public class ArrayStack<T> implements ArrayStackADT<T>{
	/*
	This is the constructor where we will be
	initializing the stack, the top, and the sequence string
	*/
	private T[] stack;
	private int top;
	public static String sequence;
	
	/**
	 * Constructor creates an array stack with a default size of 14
	 */
	public ArrayStack() {
		stack = (T[])(new Object[14]);
		top = -1;
		sequence = "";
	}
	
	/**
	 * Constructor creates an array stack with a size given
	 * @param initialCapacity the inital size of the array stack
	 */
	public ArrayStack(int initialCapacity) {
		stack = (T[])(new Object[initialCapacity]);
		top = -1;
		sequence = "";
	}
	
	/**
	 * Method to push an object into the array stack
	 * @param dataItem the item to be pushed
	 */
	public void push (T dataItem) {
		if (top==-1||stack[top]!=null) { //make sure the top is not -1 or currently looking at the top
			top++;
		}
		if(size()>length()) { //if the number of items is greater than capacity, expand the array by double (if above 50) or by 10 if below
			T[] newStack;
			if (stack.length<50) {
				newStack = (T[])(new Object[stack.length+10]);
			}else {
				newStack = (T[])(new Object[stack.length*2]);
			}
			
			for (int i = 0; i < stack.length; i++) {
				newStack[i] = stack[i];
			}
			stack = newStack;
		}
		stack[top] = dataItem;
		//top++;
		
		if (dataItem instanceof MapCell) {
			sequence += "push" + ((MapCell)dataItem).getIdentifier();
		}else {
			sequence += "push" + dataItem.toString();
		}
	}
	
	/**
	 * Method to pop the top object of the array stack
	 * @return the object popped
	 */
	public T pop () throws EmptyStackException{
		if (top==-1) { //check if the stack is empty
			throw new EmptyStackException("stack");
		}
		if(stack[top]==null) { //if the top variable is looking one above the top, set it to the top
			top--;
		}
		T result = stack[top];
		stack[top]=null;
		top--;
		
		if (result instanceof MapCell) {
			sequence += "pop" + ((MapCell)result).getIdentifier();
		}else {
			sequence += "pop" + result.toString();
		}
		
		while(size()<0.25*length()&&length()>14) { //if the number of items is a quarter the length or less, decrease by half, until you hit 14
			T[] newStack;
			if(length()/2>=14) {
				newStack = (T[])(new Object[length()/2]);
			}else {
				newStack = (T[])(new Object[14]);
			}
			
			for (int i = 0; i < size(); i++) {
				newStack[i] = stack[i];
			}
			stack = newStack;
		}
		return result;
	}
	
	/**
	 * Method to peek the top object of the array stack
	 * @return the object at the top
	 */
	public T peek () throws EmptyStackException{
		if (top==-1) { //check if the stack is empty
			throw new EmptyStackException("stack");
		}
		if(stack[top]==null) { //if the top variable is looking one above the top
			return stack[top-1];
		}else {
			return stack[top];
		}
	}
	
	/**
	 * Method to check if the array stack is empty or not
	 * @return whether the array stack is empty or not
	 */
	public boolean isEmpty() {
		return top==-1;
	}
	
	/**
	 * Method to check the number of items in the array stack
	 * @return the number of items in the array stack
	 */
	public int size() {
		return top+1;
	}
	
	/**
	 * Method to check the total capacity of the array stack
	 * @return the total capacity of the array stack
	 */
	public int length() {
		return stack.length;
	}
	
	/**
	 * Method to print the items of the array stack
	 * @return a string of the items in the array stack
	 */
	public String toString() {
		String output = "Stack: ";
		for (int i = 0; i < top; i++) {
			output+=stack[i]+", ";
		}
		output+=stack[top];
		return output;
	}
}
