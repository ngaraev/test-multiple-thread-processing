package test.nitka.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Various useful methods.
 */
public final class Utils {

	private Utils() {
	}

	/**
	 * Print the line to the log.
	 * 
	 * @param format a format string.
	 * @param args   arguments referenced by the format specifiers in the
	 *               formatstring.
	 */
	public static void log(String format, Object... args) {
		System.out.println(String.format(format, args));
	}

	/**
	 * Return a current local time.
	 */
	public static LocalTime nowTime() {
		return LocalDateTime.now().toLocalTime().truncatedTo(ChronoUnit.MILLIS);
	}

	/**
	 * Parses the string argument as a signed decimal integer.
	 * 
	 * @param value a string to be parsed.
	 * @param def   a default value.
	 */
	public static Integer parseInt(String value, Integer def) {
		if (value == null) {
			return def;
		}
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * Checks that a string is empty.
	 * 
	 * @param value a string to be checked.
	 * @return {@code true} if the string is empty, otherwise {@code false}.
	 */
	public static boolean isEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}

}
