package test.nitka.services;

import static test.nitka.utils.Utils.log;
import static test.nitka.utils.Utils.nowTime;

public class Launcher {

	private final static String START_LOG = "%s - STARTING - Consumers: %d;  File: %s \n";
	private final static String END_LOG = "\n%s - END";

	private ConsumersManager consumersManager;

	public void start(Integer consumerCount, String filePath) {
		log(START_LOG, nowTime(), consumerCount, filePath);

		consumersManager = new ConsumersManager(consumerCount);

		// Standard thread start
		Producer producer = new Producer(filePath, consumersManager);
		producer.start();
		try {
			producer.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Shutdown ConsumerManager after reading all the lines
		consumersManager.shutdown();
		log(END_LOG, nowTime(), consumerCount, filePath);
	}

}
