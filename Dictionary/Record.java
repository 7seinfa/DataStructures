/**
 * @author Hussein Abdallah
 */
package Dictionary;

public class Record {
	private String key;
	private int score, level;

	public Record(String key, int score, int level) { //constructor
		this.key = key;
		this.score = score;
		this.level = level;
	}
	
	public String getKey() { //get the key string
		return key;
	}
	
	public int getScore() { //get the score
		return score;
	}
	
	public int getLevel() { //get the level
		return level;
	}
}
