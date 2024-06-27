/**
 * This class represents a WesternCipher to decode and encode messages. Each WesternCipher has an encodingQueue, decodingQueue, and an initial capacity
 * @author Hussein Abdallah
 */

import java.io.*;

public class WesternCipher {
	/*
	This is the constructor where we will be
	initializing the encoding queue, decoding queue, and initial capacity
	*/
	private CircularArrayQueue<Character> encodingQueue, decodingQueue;
	
	/**
	 * Constructor initializes the encoding and decoding queues with a capacity of 10
	 */
	public WesternCipher () {
		encodingQueue = new CircularArrayQueue<Character>(10);
		decodingQueue = new CircularArrayQueue<Character>(10);
	}
	
	/**
	 * Constructor initializes the encoding and decoding queues with capacity option
	 * @param initialCapacity the capacity to initialize the queue with
	 */
	public WesternCipher (int initialCapacity) {
		encodingQueue = new CircularArrayQueue<Character>(initialCapacity);
		decodingQueue = new CircularArrayQueue<Character>(initialCapacity);
	}
	
	/**
	 * Method to encode a String as per the rules
	 * @param input the String to encode
	 * @return the encoded String
	 */
	public String encode (String input) {
		
		//convert the input into an array of chars, and then enqueue them
		char[] inputC = input.toCharArray();
		String result = "";
		for (int i = 0; i<inputC.length; i++) {
			encodingQueue.enqueue(inputC[i]);
		}
		
		//these are the special characters that will be changed ignoring the other rules, put in tables in which the position corresponds
		String specialChars = "AEIOUY";
		char[] firstTable1 = specialChars.toCharArray();
		char[] firstTable2 = "123456".toCharArray(); //if last wasn't a number
		char[] secondTable2 = "345612".toCharArray(); //if last was a number
		
		int lastNumber = 0; //if last character was a number, use this, if not, set 0
		int position = -1; //the current position in the queue
		
		while (!encodingQueue.isEmpty()) { //while the queue is not empty, continue encoding
			//take the first character in the queue and increase position
			char in = encodingQueue.dequeue();
			position++;
			
			if (lastNumber!=0&&specialChars.contains(String.valueOf(in))) { //if the last character was a number, and this character should be, use the second table
				for (int i = 0; i<firstTable1.length; i++) {
					if (in==firstTable1[i]) {
						in = secondTable2[i];
						lastNumber = Integer.parseInt(String.valueOf(in));
					}
				}
			}else if (specialChars.contains(String.valueOf(in))) { //if the last character wasn'y a number, but this character should be, use the first table
				for (int i = 0; i<firstTable1.length; i++) {
					if (in==firstTable1[i]) {
						in = firstTable2[i];
						lastNumber = Integer.parseInt(String.valueOf(in));
					}
				}
			}else if (in<='Z'&&in>='A'){ //for any other character other than space, turn the char into ASCI int, and perform the required moves
				int newCharASCI = (((int)in)+5+2*position-2*lastNumber);
				//make sure the output is still an uppercase letter
				while (newCharASCI>90) newCharASCI-=26;
				while (newCharASCI<65) newCharASCI+=26;
				in = (char)(newCharASCI);
				lastNumber=0; //set last number to 0 as this wasn't a number
			}
			result+=in;
		}
		return result;
	}
	
	/**
	 * Method to decode a String as per the rules
	 * @param input the String to decode
	 * @return the decoded String
	 */
	public String decode (String input) {
		//convert the input into an array of chars, and then enqueue them
		char[] inputC = input.toCharArray();
		String result = "";
		for (int i = 0; i<inputC.length; i++) {
			decodingQueue.enqueue(inputC[i]);
		}
		
		//these are the special characters that will be changed ignoring the other rules, put in tables in which the position corresponds
		String specialChars = "AEIOUY";
		char[] firstTable1 = specialChars.toCharArray();
		char[] firstTable2 = "123456".toCharArray(); //if last wasn't a number
		char[] secondTable2 = "345612".toCharArray(); //if last was a number
		
		int lastNumber = 0; //if last character was a number, use this, if not, set 0
		int position = -1; //the current position in the queue
		
		while (!decodingQueue.isEmpty()) { //while the queue is not empty, continue encoding
			//take the first character in the queue and increase position
			char in = decodingQueue.dequeue();
			position++;
			
			if (lastNumber!=0&&"123456".contains(String.valueOf(in))) { //if the last character was a number and so is this one, use table 2
				lastNumber = Integer.parseInt(String.valueOf(in));
				for (int i = 0; i<secondTable2.length; i++) {
					if (in==secondTable2[i]) {
						in = firstTable1[i];
					}
				}
			}else if ("123456".contains(String.valueOf(in))) { //if this character is a number but the last wasn't, use table 1
				lastNumber = Integer.parseInt(String.valueOf(in));
				for (int i = 0; i<firstTable2.length; i++) {
					if (in==firstTable2[i]) {
						in = firstTable1[i];
					}
				}
			}else if (in<='Z'&&in>='A'){ //for any other character other than space, turn the char into ASCI int, and perform the required moves
				int newCharASCI = (((int)in)-5-2*position+2*lastNumber);
				//make sure the output is still an uppercase letter
				while (newCharASCI>90) newCharASCI-=26;
				while (newCharASCI<65) newCharASCI+=26;
				in = (char)(newCharASCI);
				lastNumber=0; //set last number to 0 as this wasn't a number
			}
			result+=in;
		}
		return result;
	}
	
	/**
	 * The main method, to take requests from the user
	 */
	public static void main (String[] args) throws IOException {
		//the input and wester cipher
		char input = 'e';
		WesternCipher wc = new WesternCipher();
		
		while(input=='e'||input=='d') { //as long as the input is e or d
			System.out.println("Choose an option from the following:\nd - decode\ne - encode\nany other character - exit");
			
			//create a reader and take in the first char, and then clear the reader
			InputStreamReader reader = new InputStreamReader(System.in);
			BufferedReader bReader = new BufferedReader(reader);
			input = (char)bReader.read();
			bReader.readLine();
			
			if(input=='e') { //encode if asked to
				System.out.println("Please enter the code you would like to encode:");
				System.out.println("Encoded message: "+ wc.encode(bReader.readLine().toUpperCase())+"\n");
			} else if(input=='d') { //decode if asked to
				System.out.println("Please enter the code you would like to decode:");
				System.out.println("Decode message: "+ wc.decode(bReader.readLine().toUpperCase())+"\n");
			} else { //otherwise close the readers and exit
				System.out.println("Thank you!");
				bReader.close();
				reader.close();
			}
			
		}
		
	}
	
}
