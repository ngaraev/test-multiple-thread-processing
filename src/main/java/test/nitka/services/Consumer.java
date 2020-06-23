package test.nitka.services;

import static java.lang.Thread.currentThread;
import static test.nitka.utils.Utils.log;
import static test.nitka.utils.Utils.nowTime;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import test.nitka.model.Message;

/**
 * Simulates the message processing.
 */
public class Consumer implements Runnable {

	private final static String OUTPUT_LOG = "%s;  Thread: %s;  Start: %s;  End: %s";

	private final Message message;

	public Consumer(Message message) {
		this.message = message;
	}

	@Override
	public void run() {
		LocalTime startTime = nowTime();

		// Simulate the message processing time
		if (message.getDelay() > 0) {
			try {
				TimeUnit.MILLISECONDS.sleep(message.getDelay());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Log a result
		log(OUTPUT_LOG, message, currentThread().getName(), startTime, nowTime());
		message.getId().intern().notify();
	}

}
