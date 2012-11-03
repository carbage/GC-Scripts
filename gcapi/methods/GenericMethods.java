package gcapi.methods;

import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Bank.Amount;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.Item;

public final class GenericMethods {

	public static String cleanUsername(String s) { // Removes the space (Which
		// is a unicode character)
		// from player names.
		if (s != null) {
			return s.replace("\u00A0", " ");
		} else {
			return s;
		}
	}

	public static void waitForCondition(boolean condition, long timeout) { // Waits for condition to be fulfilled or timer to run out
		Timer timer = new Timer(timeout);
		while (!condition && timer.isRunning()) {
		}
	}

	public static void searchAndWithdraw(String searchTerm, String[] keywords) {
		Item[] items = Bank.search(searchTerm);
		for (Item i : items) {
			for (String keyword : keywords) {
				if (i.getName().contains(keyword)) Bank.withdraw(i.getId(), Amount.ALL);
			}
		}
	}

}
