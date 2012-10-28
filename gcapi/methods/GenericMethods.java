package gcapi.methods;




public final class GenericMethods {
	
	public static String cleanUsername(String s) { //Removes the space (Which is a unicode character) from player names.
		if (s != null) {
			return s.replace("\u00A0", " ");
		} else {
			return s;
		}
	}
	
	public static void waitForCondition(boolean condition) { // Waits for any condition (boolean)
	    while (!condition) {}
	}
	
}
