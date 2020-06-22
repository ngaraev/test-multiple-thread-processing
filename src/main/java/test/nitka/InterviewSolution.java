package test.nitka;

import test.nitka.services.Launcher;
import test.nitka.utils.Utils;

public class InterviewSolution {

	private static final String ARGS_ERROR = "Please provide 2 arguments: \n"
			+ " 1. consumer count (number); \n"
			+ " 2. source file location.";

	public static void main(String[] args) {
		// Validate arguments
		if (args.length != 2) {
			System.out.println(ARGS_ERROR);
			return;
		}

		// Get consumers count
		Integer consumerCount = Utils.parseInt(args[0], null);
		if (consumerCount == null) {
			System.out.println(ARGS_ERROR);
			return;
		}

		// Get path to a file
		String filePath = args[1];

		// Run execution
		Launcher launcher = new Launcher();
		launcher.start(consumerCount, filePath);
	}

}
