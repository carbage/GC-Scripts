package gcapi.methods;

import org.powerbot.game.api.util.Timer;




public final class GenericMethods {
	
	public static String cleanUsername(String s) { //Removes the space (Which is a unicode character) from player names.
		if (s != null) {
			return s.replace("\u00A0", " ");
		} else {
			return s;
		}
	}
	
	public static void waitForCondition(boolean condition, long timeout) { // Waits for any condition (boolean)
	    Timer timer = new Timer(timeout);
	    while (!condition && timer.isRunning()) {}
	}
	
}
