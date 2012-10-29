package gcapi.methods;

import java.text.DecimalFormat;

import org.powerbot.game.api.util.Timer;

public class CalculationMethods {

	public static String perHour(int rawValue, Timer timer) {
		DecimalFormat df = new DecimalFormat("###,###,###"); // Formats in the format of xxx,xxx,xxx
		return df.format((int) Math.floor((rawValue * (3600000 / timer.getElapsed())))); // Coverts the raw value into a number per hour and formats		
	}
}
