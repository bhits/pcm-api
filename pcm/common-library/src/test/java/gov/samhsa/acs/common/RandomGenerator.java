package gov.samhsa.acs.common;

import java.util.Random;

public class RandomGenerator {

	public static final String ALPHA_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String ALPHA_LOWER = "abcdefghijklmnopqrstuvwxyz";
	public static final String NUMERIC = "0123456789";
	public static final String ALPHA_NUMERIC_MIXED = ALPHA_LOWER + ALPHA_UPPER
			+ NUMERIC;
	static Random rnd = new Random();

	public static String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(ALPHA_NUMERIC_MIXED.charAt(rnd
					.nextInt(ALPHA_NUMERIC_MIXED.length())));
		return sb.toString();
	}

	public static String randomString() {
		int startLen = Integer.MAX_VALUE/1800;
		int len = rnd.nextInt(startLen) + 1;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++)
			sb.append(ALPHA_NUMERIC_MIXED.charAt(rnd
					.nextInt(ALPHA_NUMERIC_MIXED.length())));
		return sb.toString();
	}

	public static byte[] randomBytes(int len) {
		byte[] bytes = new byte[len];
		rnd.nextBytes(bytes);
		return bytes;
	}

	public static byte[] randomBytes() {
		byte[] bytes = new byte[randomInteger(254) + 1];
		rnd.nextBytes(bytes);
		return bytes;
	}

	public static char[] randomChars(int len) {
		return randomString(len).toCharArray();
	}

	protected static char[] randomChars() {
		return randomString().toCharArray();
	}

	protected static int randomInteger(int max) {
		return rnd.nextInt(max);
	}
}
