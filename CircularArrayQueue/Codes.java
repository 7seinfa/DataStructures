
public class Codes
{
  /**
   * Encode and decode a message using a key of values stored in
   * a queue.
   */
  public static void main ( String[] args)
  { 
    int[] key = {5, 12, -3, 8, -9, 4, 10};
    Integer keyValue;
    String encoded = "", decoded = "";
    String message = "All programmers are playwrights and all " +
                     "computers are lousy actors.";
    
    CircularArrayQueue<Integer> keyQueue1 = new CircularArrayQueue<Integer>();
    CircularArrayQueue<Integer> keyQueue2 = new CircularArrayQueue<Integer>();
    
    /** load key queue */
    for (int i=0; i < key.length; i++)
    {
      keyQueue1.enqueue (key[i]);
      keyQueue2.enqueue (key[i]);
    }
    
    /** encode message */
    for (int j=0; j < message.length(); j++)
    {
      keyValue = keyQueue1.dequeue();
      encoded += (char) ((int)message.charAt(j) + keyValue);
      keyQueue1.enqueue (keyValue);
    }
    
    System.out.println ("Encoded Message:\n" + encoded + "\n");
    
    /** decode message */
    for (int k=0; k < encoded.length(); k++)
    {
      keyValue = keyQueue2.dequeue();
      decoded += (char) ((int)encoded.charAt(k) - keyValue);
      keyQueue2.enqueue (keyValue);
    }
    
    System.out.println ("Decoded Message:\n" + decoded);
  }
}
