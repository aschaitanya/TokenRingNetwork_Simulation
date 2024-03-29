package cs187.asn03;

import ch06.lists.ListInterface;
import ch06.lists.RefUnsortedList;

/**
 * A basic token ring simulator. 
 */
public class TokenRingSimulator {

	/**
	 * Main program entry point. 
	 */
	public static void main(String[] args) {
		//// First, create all the workstations ////

		// We will have each workstation send a message to every other
		// workstation (except itself).  We will also have it send a
		// message to a non-existent workstation.
		
		// Initialize Workstation 0
		ListInterface<Message> messages0 = new RefUnsortedList<Message>();  //creating unsorted list of messages
		messages0.add(new Message(0, 3));
		messages0.add(new Message(0, 5));									// creating 6 messages, putting into the unsorted list.
		messages0.add(new Message(0, 2));
		messages0.add(new Message(0, 1));
		messages0.add(new Message(0, 4));
		messages0.add(new Message(0, 9));
		
		Workstation ws0 = new Workstation(0, messages0); 					//creating a new workstation, giving it the list of messages
		
		// Initialize Workstation 1
		ListInterface<Message> messages1 = new RefUnsortedList<Message>();
		messages1.add(new Message(1, 3));
		messages1.add(new Message(1, 5));
		messages1.add(new Message(1, 2));
		messages1.add(new Message(1, 0));
		messages1.add(new Message(1, 4));
		messages1.add(new Message(1, 9));
		
		Workstation ws1 = new Workstation(1, messages1);
		
		// Initialize Workstation 2
		ListInterface<Message> messages2 = new RefUnsortedList<Message>();
		messages2.add(new Message(2, 3));
		messages2.add(new Message(2, 5));
		messages2.add(new Message(2, 1));
		messages2.add(new Message(2, 0));
		messages2.add(new Message(2, 4));
		messages2.add(new Message(2, 9));
		
		Workstation ws2 = new Workstation(2, messages2);
		
		// Initialize Workstation 3
		ListInterface<Message> messages3 = new RefUnsortedList<Message>();
		messages3.add(new Message(3, 2));
		messages3.add(new Message(3, 5));
		messages3.add(new Message(3, 1));
		messages3.add(new Message(3, 0));
		messages3.add(new Message(3, 4));
		messages3.add(new Message(3, 9));
		
		Workstation ws3 = new Workstation(3, messages3);
		
		// Initialize Workstation 4
		ListInterface<Message> messages4 = new RefUnsortedList<Message>();
		messages4.add(new Message(4, 2));
		messages4.add(new Message(4, 5));
		messages4.add(new Message(4, 1));
		messages4.add(new Message(4, 0));
		messages4.add(new Message(4, 3));
		messages4.add(new Message(4, 9));
		
		Workstation ws4 = new Workstation(4, messages4);		
		
		// Initialize Workstation 5
		ListInterface<Message> messages5 = new RefUnsortedList<Message>();
		messages5.add(new Message(5, 2));
		messages5.add(new Message(5, 4));
		messages5.add(new Message(5, 1));
		messages5.add(new Message(5, 0));
		messages5.add(new Message(5, 3));
		messages5.add(new Message(5, 9));
		
		Workstation ws5 = new Workstation(5, messages5);
		
		TokenRingListInterface ring = new TokenRingList();
		ring.add(ws0);
		ring.add(ws1);
		ring.add(ws2);
		ring.add(ws3);
		ring.add(ws4);
		ring.add(ws5);
		
		// Set clockwise/counter clockwise
		ring.setClockwise();
		
		//// Run Simulation ////
		int numMsgsSent = 0;
		int numMsgsRcvd = 0;
		
		Token tok = new Token();
		
		boolean have_msgs = true;
		while (have_msgs) {
			// Look for a next message to send. If one is found then
			// nextMessage will be the next message, t will be the
			// workstation sending the message, and s was the previous
			// workstation that sent a message.
			Message nextMessage = null;
			Workstation t = ring.getNext();
			Workstation s = t;
			do {
				if (t.hasMessages()) {
					nextMessage = t.send();
					numMsgsSent+=1;
					break;
				}
				t = ring.getNext();
			} while (t != s);
			
			// If nextMessage is null the workstations have no 
			// more messages to send.
			if (nextMessage == null) {
				have_msgs = false;
				continue;
			}
			
			// Here we "send" the message around the ring.  First, we
			// reset the token and set the message:
			tok.reset();
			tok.setMessage(nextMessage);
			
			// Assign the current sending workstation t to s:
			s = t;
			
			// Loop around the ring until the token is received back
			// at the original sender (s):
			do {
				t = ring.getNext();
				t.receive(tok);
			} while (t != s);
			
			if (tok.wasReceived()) {
				numMsgsRcvd+=1;
			}
		}
		
		System.out.printf("%nmessages sent = %d, received = %d%n", numMsgsSent, numMsgsRcvd);
	}
	
}
