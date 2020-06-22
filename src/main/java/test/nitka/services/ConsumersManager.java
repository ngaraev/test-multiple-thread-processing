package test.nitka.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import test.nitka.model.Message;

/**
 * Manages the work of consumers.
 */
public class ConsumersManager {

	private ExecutorService pool;
	private volatile int maxDelay = 0;

	public ConsumersManager(Integer count) {
		pool = Executors.newFixedThreadPool(count);
	}

	/**
	 * Sends a message to a consumer.
	 * 
	 * @param message an incoming message.
	 */
	public void submit(Message message) {
		// store max delay
		if (message.getDelay() > maxDelay) {
			maxDelay = message.getDelay();
		}

		pool.execute(new Consumer(message));
	}

	/**
	 * Shutdown all consumers.
	 */
	public void shutdown() {
		pool.shutdown();

		try {
			pool.awaitTermination(maxDelay, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
