package cs187.asn03;

import support.DLLNode;

/**
 * A specialized circular doubly linked list for a token ring simulation. 
 */
public class TokenRingList implements TokenRingListInterface {
	// The start of the token ring list:
	private DLLNode<Workstation> list;
	// The location used to search this list:
	private DLLNode<Workstation> location;
	// The size of this list:
	private int size;
	// clockwise/counterclockwise - true if clockwise, false if counterclockwise:
	private boolean clockwise = true;
	
	/**
	 * Creates a new TokenRingList object.
	 */
	public TokenRingList() {
		list     = null;
		location = null;
	}
	
	/**
	 * Returns the size of the token ring.
	 */
	public int size() {
		return size;
	}

	/**
	 * Adds a Workstation object to this token ring.
	 */
	public void add(Workstation element) {
		DLLNode<Workstation> newNode = new DLLNode<Workstation>(element);
		if (list == null){
			//add element to an empty list
			list = newNode;
			newNode.setBack(list);
			newNode.setForward(list);
			location = newNode;
		}
		else{
			newNode.setBack(location);
			newNode.setForward(list);
			location.setForward(newNode);
			location = newNode;
		}
		size++;
	}

	/**
	 * Removes a Workstation object from this token ring.
	 */
	public boolean remove(Workstation element) {
		// TODO
		get(element);
		if (contains(element)){
			
		}
		size--;
		return false;
	}

	/**
	 * Returns true if this token ring has the workstation; false otherwise.
	 */
	public boolean contains(Workstation element) {
		// Is the list empty?
		if (list == null)
			return false;
		
		// Find the element if it exists:
		DLLNode<Workstation> t = list;
		do {
			if (element == t.getInfo())
				return true;
			t = t.getForward();
		} while (t != list);
		
		// Not found:
		return false;
	}

	/**
	 * Returns the workstation if it is in this token ring; null otherwise.
	 */
	public Workstation get(Workstation element) {
		// Is the list empty?
		if (list == null)
			return null;
		
		// Find the element if it exists:
		DLLNode<Workstation> t = list;
		do {
			if (element == t.getInfo())
				return element;			
			t = t.getForward();
		} while (t != list);
		
		// Not found:
		return null;
	}

	public void reset() {
		location = list;
	}

	/**
	 * Returns the next workstation in the token ring.
	 */
	public Workstation getNext() {
		if (clockwise) {
			return getClockwise(); 
		}
		else {
			return getCounterClockwise();
		}
	}

	/**
	 * Returns the current workstation and advances location clockwise. 
	 */
	private Workstation getClockwise() {
		DLLNode<Workstation> t = location;
		location = location.getForward();
		return t.getInfo();
	}
	
	/**
	 * Returns the current workstation and advances location counterclockwise. 
	 */
	private Workstation getCounterClockwise() {
		DLLNode<Workstation> t = location;
		location = location.getBack();
		return t.getInfo();
	}

	/**
	 * Sets the token ring to retrieve workstations in a clockwise fashion.
	 */
	public void setClockwise() {
		clockwise = true;
	}

	/**
	 * Sets the token ring to retrieve workstations in a counterclockwise fashion.
	 */
	public void setCounterClockwise() {
		clockwise = false;
	}
	
}
