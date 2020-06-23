package test.nitka.services;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import test.nitka.model.Message;

/**
 * Manages the work of consumers.
 */
public class ConsumersManager extends Thread {

	private ExecutorService pool;
	private volatile int maxDelay = 0;
	private final ConcurrentLinkedQueue<Message> messageQueue = new ConcurrentLinkedQueue<>();
	private final Map<String, Future<?>> activeTasks = new HashMap<>();

	public ConsumersManager(Integer count) {
		pool = Executors.newFixedThreadPool(count);
	}

	@Override
	public void run() {
		while (!pool.isTerminated()) {
			for (Message m : messageQueue) {
				Future<?> f = activeTasks.get(m.getId());
				// if active task with same ID was not found
				if (f == null || f.isDone()) {
					// then run it
					f = pool.submit(new Consumer(m));
					// add task to the list
					activeTasks.put(m.getId(), f);
					// remove message from the queue
					messageQueue.remove(m);
					break;
				}
			}

			try {
				TimeUnit.MILLISECONDS.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
		messageQueue.add(message);
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
