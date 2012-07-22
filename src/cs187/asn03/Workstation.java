package cs187.asn03;

import ch06.lists.ListInterface;
import ch06.lists.RefUnsortedList;

/**
 * A Workstation used in the network simulation.
 */
public class Workstation {
	// Workstation ID:
	private int id;

	// The list of messages to send.
	private ListInterface<Message> messages;

	/**
	 * Creates a new Workstation object.
	 */
	public Workstation(int id, ListInterface<Message> messages) {
		this.id = id;
		this.messages = messages;
	}

	/**
	 * Creates a new Workstation object.
	 */
	public Workstation(int id) {
		this(id, new RefUnsortedList<Message>());
	}

	/**
	 * Returns true if this workstation has messages to send.
	 */
	public boolean hasMessages() {
		return messages.size() != 0;
	}

	/**
	 * Receives a message from the simulated network.
	 */
	public void receive(Token t) {

		Message receivedMessage = t.getMessage();

		if (id == receivedMessage.getReceiver()) {
			// does this workstation ID match the receivers ID?
			// if yes, this message is meant for this workstation
			// announce that message as been received
			System.out
					.printf("message %s was received by receiver %d; and sent by sender %d%n",
							receivedMessage.toString(),
							receivedMessage.getReceiver(),
							receivedMessage.getSender());

			t.setReceived(true);
		}

		// If the sender has the same id as this workstation and the token was
		// received
		// (that is the receiver received the token and sent an acknowledgment)
		// "message M24[4->2] acknowledged by sender 4 from destination 2".
		else if (id == receivedMessage.getSender() && t.wasReceived()) {
			System.out
					.printf("message %s acknowledged by sender %d; from destination %d%n",
							receivedMessage.toString(),
							receivedMessage.getSender(),
							receivedMessage.getReceiver());

		}

		// The final case is when the receiver did not receive the message -
		// in this case, the token was never set as being received.
		// In this case, you need to check to see if the sender is the same as
		// this workstation's id
		// and the token was not received. If this is true you need to print a
		// message like
		// "message M29[4->9] dropped; destination not reachable".
		else if (id == receivedMessage.getSender() && !t.wasReceived()) {
			System.out.println("message " + receivedMessage.toString()
					+ " dropped; destination not reachable");
		}

	}

	/**
	 * Sends a message from the network by returning the next message to send.
	 */
	public Message send() {
		if (messages.size() > 0) {
			messages.reset();
			Message m = messages.getNext();
			messages.remove(m);
			System.out.printf("message %s sent by %d; to be received by %d%n",
					m.toString(), m.getSender(), m.getReceiver());
			return m;
		}
		// Return null if we have no messages to send.
		return null;
	}
}
