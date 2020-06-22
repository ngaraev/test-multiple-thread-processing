package test.nitka.model;

/**
 * Incoming message.
 */
public class Message {

	private String id;
	private Integer delay;
	private String input;
	private MessageType type;

	public Message() {
		this.delay = 0;
		this.type = MessageType.PAUSE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getDelay() {
		return delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return id + "|" + delay + "|" + input;
	}

}
