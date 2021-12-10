package org.phoegasus.jdukpt.utils;

/**
 * String padding utility.
 * @author Phoegasus
 * @since 1.0.0
 * @see <a href="https://github.com/phoegasus/JDukpt">https://github.com/phoegasus/JDukpt</a>
 */
public class StringPadder {
	
	/**
	 * Pads the source string to the left with zeroes until the length is a multiple of 16.
	 * @param source Source string
	 * @return Left padded string
	 */
	public static String zeroLeftPadToMultipleOf16(String source) {
		return lPad(source, '0', source.length() % 16 != 0 ? source.length() + (16 - source.length() % 16) : source.length());
	}
	
	/**
	 * Pads the source string to the left using the padding character specified up to the desired length.
	 * @param source Source string
	 * @param paddingCharacter Padding character
	 * @param desiredLength Length of the result
	 * @return Left padded string
	 */
	public static String lPad(String source, char paddingCharacter, int desiredLength) {
		return pad(source, paddingCharacter, desiredLength, false);
	}

	/**
	 * Pads the source string to the right using the padding character specified up to the desired length.
	 * @param source Source string
	 * @param paddingCharacter Padding character
	 * @param desiredLength Length of the result
	 * @return Right padded string
	 */
	public static String rPad(String source, char paddingCharacter, int desiredLength) {
		return pad(source, paddingCharacter, desiredLength, true);
	}


	/**
	 * Pads the source string using the padding character specified up to the desired length, the direction depends on the <i>right</i> boolean.
	 * @param source Source string
	 * @param paddingCharacter Padding character
	 * @param desiredLength Length of the result
	 * @param right Pad to the right
	 * @return Padded string
	 */
	private static String pad(String source, char paddingCharacter, int desiredLength, boolean right) {
		StringBuilder sb = new StringBuilder(source);
		while(sb.length() < desiredLength) {
			sb.insert(right ? source.length() : 0, paddingCharacter);
		}
		return sb.toString();
	}
}
