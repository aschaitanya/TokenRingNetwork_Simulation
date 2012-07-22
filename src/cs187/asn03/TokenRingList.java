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
	// clockwise/counterclockwise - true if clockwise, false if
	// counterclockwise:
	private boolean clockwise = true;

	/**
	 * Creates a new TokenRingList object.
	 */
	public TokenRingList() {
		list = null;
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
		if (list == null) {
			// add element to an empty list
			list = newNode;
			newNode.setBack(list);
			newNode.setForward(list);
			location = newNode;
		} else {
			// add in any other case, list is unordered. Add to end of list.
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
		// check if element exists, store element reference in location
		// Successfully link back and forward elements together
		// remove location

		if (contains(element)) {

			DLLNode<Workstation> nodeToDelete = null;
			DLLNode<Workstation> t = list;
			System.out.println(element);
			do {
				if (element == t.getInfo()) {
					nodeToDelete = t;
					System.out.println("Workstation Found!");
					System.out.println(t.getInfo());
					break; // if item is found, stop do/while loop
				}
				t = t.getForward();
			} while (t != list);

			nodeToDelete.getBack().setForward(nodeToDelete.getForward());
			nodeToDelete.getForward().setBack(nodeToDelete.getBack());
			System.out.println("Workstation Deleted!");

		} else {
			System.out.println("Workstation does not exsist");
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
		} else {
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
	 * Sets the token ring to retrieve workstations in a counterclockwise
	 * fashion.
	 */
	public void setCounterClockwise() {
		clockwise = false;
	}
	

}
