package gcapi.methods;

import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.widget.Widget;

public final class InterfaceMethods {

	public static void waitForWidgetAppear(int widgetID) { // Waits for widget to appear
		//Timer t = new Timer(millis);
		Widget w = Widgets.get(widgetID);
		//while (t.isRunning() && !w.validate()) {}
		while (!Widgets.get(widgetID).validate()) {
		}
	}

	public static void waitForWidgeDisappear(long millis, int widgetID) { // Waits for widget to disappear
		//Timer t = new Timer(millis);
		//while (t.isRunning() && w.validate()) {}
		while (Widgets.get(widgetID).validate()) {
		}
	}

}
