package gcapi.methods;

import org.powerbot.game.api.util.Timer;

public class CalculationMethods {

	public static int perHour(int rawValue, Timer timer) {
		return  (int) Math.floor((rawValue * (3600000 / timer.getElapsed())));		
	}
}
