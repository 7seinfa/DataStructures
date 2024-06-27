/**
 * This class represents a CircularArrayQueue. Each queue has a front, read, count, and initial capacity
 * @author Hussein Abdallah
 */

public class CircularArrayQueue<T> implements QueueADT<T>{
	/*
	This is the constructor where we will be
	initializing the front, rear, count, queue, and initial capacity
	*/
	private int front, rear, count;
	private T[] queue;
	final int DEFAULT_CAPACITY = 20;
	
	/**
	 * Constructor creates a circular array queue with a capacity of 20
	 */
	public CircularArrayQueue() {
		front = 1;
		rear = DEFAULT_CAPACITY;
		count = 0;
		queue = (T[])(new Object[DEFAULT_CAPACITY]);
	}
	
	/**
	 * Constructor creates a circular array queue with capacity option
	 * @param initialCapacity the capacity to initialize the queue with
	 */
	public CircularArrayQueue(int initialCapacity) {
		front = 1;
		rear = initialCapacity;
		count = 0;
		queue = (T[])(new Object[initialCapacity]);
	}
	
	@Override
	/**
	 * Method to add an item to the front of the queue
	 * @param element the item to add
	 */
	public void enqueue(T element) {
		if (count==getLength()-1) expandCapacity(); //expand capacity if full
		queue[rear % queue.length] = element; //add the element to the end
		rear = (rear+1) % queue.length; //increase rear by 1
		count++; //increase count
	}
	
	@Override
	/**
	 * Method to remove the first item of the queue
	 * @return the item removed
	 */
	public T dequeue() {
		if (isEmpty()) throw new EmptyCollectionException("Queue");
		
		count--; //decrease count
		T result = queue[((front-1)%queue.length+queue.length)%queue.length]; //set the object to front-1, and make sure front-1 is greater than zero and within the queue
		queue[((front-1)%queue.length+queue.length)%queue.length] = null; //set it to null
		front = ((front+1)%queue.length+queue.length)%queue.length; //increase front by 1
		
		return result;
	}
	
	@Override
	/**
	 * Accessor method to get the first item of the queue
	 * @return first item of the queue
	 */
	public T first() {
		if (isEmpty()) throw new EmptyCollectionException("Queue");
		if(front-1>=0) { //if front-1 is greater than 0, take that item, otherwise, take the item at max length
			return queue[front-1];
		}
		return queue[queue.length-1];
	}
	
	@Override
	/**
	 * Method to check if the queue is empty
	 * @return whether the queue is empty or not
	 */
	public boolean isEmpty() {
		if (count==0) return true;
		return false;
	}
	
	@Override
	/**
	 * Accessor method to get the size of the queue
	 * @return the size of the queue
	 */
	public int size() {
		return count;
	}
	
	/**
	 * Accessor method to get the front of the queue
	 * @return the front of the queue
	 */
	public int getFront() {
		return front;
	}
	
	/**
	 * Accessor method to get the rear of the queue
	 * @return the rear of the queue
	 */
	public int getRear() {
		return rear;
	}
	
	/**
	 * Accessor method to get the capacity of the queue
	 * @return the capacity of the queue
	 */
	public int getLength() {
		return queue.length;
	}
	
	/**
	 * Method to print the queue in a readable manner
	 * @return the queue items in a string 
	 */
	public String toString() {
		if (isEmpty()) return "The queue is empty";
		
		String result = "QUEUE: "; //result string
		
		for(int x = front-1; x != rear-1; x++) { //loop through the array and add to the string
			if (x==queue.length) x = 0; //when x gets to length, reset x to 0
			result+=queue[x]+", ";
		}
		result+=queue[rear-1]+"."; //add last item and period at the end
		return result;
	}
	
	/**
	 * Method to expand the capacity of the queue by 20
	 */
	private void expandCapacity() {
		T[] newArray = (T[]) (new Object[getLength()+20]); //create new array with bigger capacity
		
		int i = 0;
		for(int x = front-1; x != rear; x++) { 
			if (x==queue.length) x = 0; //when x gets to length, reset x to 0
			newArray[i] = queue[x];
			i++;
		}
		
		queue = newArray;
		front = 1; //reset front to 1 and rear to the last item
		rear = count;
		
	}
	
	
}
