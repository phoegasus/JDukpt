package example;

public class StringPadder {
	
	public static String lPad(String source, char paddingCharacter, int desiredLength) {
		return pad(source, paddingCharacter, desiredLength, false);
	}
	
	public static String rPad(String source, char paddingCharacter, int desiredLength) {
		return pad(source, paddingCharacter, desiredLength, true);
	}
	
	private static String pad(String source, char paddingCharacter, int desiredLength, boolean right) {
		StringBuilder sb = new StringBuilder(source);
		while(sb.length() < desiredLength) {
			sb.insert(right ? source.length() : 0, paddingCharacter);
		}
		return sb.toString();
	}
}
