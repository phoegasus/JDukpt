package org.phoegasus.jdukpt;

/**
 * Hex utilities.
 * @author Phoegasus
 * @version 1.0.0
 * @since 1.0.0
 * @see <a href="https://github.com/phoegasus/JDukpt">https://github.com/phoegasus/JDukpt</a>
 */
public class HexUtils {
	
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	
	/**
	 * Converts a byte array to a hex String
	 * @param bytes Byte array to convert
	 * @return Hexadecimal representation of the byte array
	 * @see <a href="https://stackoverflow.com/a/9855338">https://stackoverflow.com/a/9855338</a>
	 */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
    
    /**
     * Converts a hex String to a byte array
	 * @param s String to convert
	 * @return Byte array
	 * @see <a href="https://stackoverflow.com/a/9855338">https://stackoverflow.com/a/140861</a>
	 */
    public static byte[] hexToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
