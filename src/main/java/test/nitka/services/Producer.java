package test.nitka.services;

import static test.nitka.utils.Utils.isEmpty;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import test.nitka.model.Message;
import test.nitka.model.MessageType;
import test.nitka.utils.Utils;

/**
 * Simulates the message incoming.
 */
public class Producer extends Thread {

	private final String filePath;
	private final ConsumersManager consumersManager;

	public Producer(String filePath, ConsumersManager consumersManager) {
		this.filePath = filePath;
		this.consumersManager = consumersManager;
	}

	@Override
	public void run() {
		Path path = Paths.get(filePath);
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			while (reader.ready()) {
				// Read next message
				Message message = readMessage(reader.readLine());

				if (message == null) {
					// There is no message, waiting for a new one
					TimeUnit.MILLISECONDS.sleep(100);
					continue;
				}

				switch (message.getType()) {
				case MESSAGE:
					// Submit the message to a consumer
					consumersManager.submit(message);
					TimeUnit.MILLISECONDS.sleep(20);
					break;

				case PAUSE:
					// Pause the execution
					TimeUnit.MILLISECONDS.sleep(message.getDelay());
					break;
				}
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets a message from a string.
	 */
	private Message readMessage(String line) {
		if (isEmpty(line)) {
			return null;
		}

		String[] parts = line.split("\\|");

		Message message = new Message();
		if (parts.length > 0) {
			message.setId(parts[0]);
		}
		if (parts.length > 1) {
			message.setDelay(Utils.parseInt(parts[1], 0));
		}
		if (parts.length > 2) {
			message.setInput(parts[2]);
		}
		message.setType(isEmpty(message.getId()) ? MessageType.PAUSE : MessageType.MESSAGE);

		return message;
	}
}
