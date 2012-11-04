package gcapi.methods;

import org.powerbot.game.api.util.Timer;

public class CalculationMethods {

	public static String perHour(int rawValue, Timer timer) {
		return String.format("%,d", rawValue * (3600000 / timer.getElapsed())); // Coverts the raw value into a number per hour and formats		
	}
}
