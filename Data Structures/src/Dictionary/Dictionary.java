package Dictionary;

public class Dictionary implements DictionaryADT {
	static private Node [] hashTable;
	private int recordNum = 0; //going to keep number of records here, increase when added, decrease when removed
	
	private static class Node{ //node to be used in hash table (table holds nodes which hold records and next node for separate chaining, used as linked list)
		Record rec;
		Node next;
	}
	
	public Dictionary (int size) { //constructor creates hash table of selected size
		hashTable = new Node[size];
	}
	
	private int hashFunc(String key) { //hash function starts with last letter, converts to int, adds to last hash value * 33, and gets mod of length of table
		int hashKey = 0;
		
		int x = 33;
		for (int i = key.length()-1; i > -1; i--) {
			hashKey= (hashKey*x + key.chars().toArray()[i])%hashTable.length;
		}
		
		return hashKey;
	}
	
	public int put(Record rec) throws DuplicatedKeyException{ //put record in dictionary
		int hashKey = hashFunc(rec.getKey());
		
		if(hashTable[hashKey]!=null) { //if there is already a node at the location of the hash key
			if (hashTable[hashKey].rec.getKey().equals(rec.getKey())) { //if there is a Record already with same key, then give error
				throw new DuplicatedKeyException(rec.getKey()+"is already in the dictionary");
			} else { //otherwise go through linked list
				Node cur = hashTable[hashKey];
				while (cur.next!=null) { //check every Record in linked list to see if the key is already there, if so, give error
					if (cur.next.rec.getKey().equals(rec.getKey())) {
						throw new DuplicatedKeyException(rec.getKey()+"is already in the dictionary");
					} else {
						cur = cur.next;
					}
				}
				//put Record in hash table at next location
				Node newEntry = new Node();
				newEntry.rec = rec;
				cur.next = newEntry;
				recordNum++;
				return 1;
			}
		}else { //if no node at location of hash key, put new node with Record rec at this location
			Node newEntry = new Node();
			newEntry.rec = rec;
			hashTable[hashKey] = newEntry;
			recordNum++;
			return 0;
		}
	}
	public void remove(String key) throws InexistentKeyException{ //remove Record with key, if doesn't exist throw error
		int hashKey = hashFunc(key);
		Node cur = hashTable[hashKey]; //find node at location hash key
		boolean found = false;
		while (!found && cur!=null) { //check every node in location hash key, if it doesn't exist throw error, otherwise remove
			if (cur.rec.getKey().equals(key)) {
				found = true;
				recordNum--;
				if (cur.next!=null) {
					cur = hashTable[hashKey].next;
				}else {
					cur = null;
				}
			}else {
				cur = cur.next;
			}
		}
		if(!found) {
			throw new InexistentKeyException(key = "is not in the dictionary");
		}
	}
	public Record get(String key) { //get node with key
		int hashKey = hashFunc(key);
		Node cur = hashTable[hashKey]; //get node at location hash key
		boolean found = false;
		while (!found && cur!=null) { //check every node in linked list at this position, return the Record if found, otherwise return null
			if (cur.rec.getKey().equals(key)) {
				found = true;
				return cur.rec;
			}else {
				cur = cur.next;
			}
		}
		return null;
	}
	public int numRecords() { //return number of records in hash table
		return recordNum;
	}
}

