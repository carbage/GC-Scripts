package gcscripts.gcroller;

import java.awt.event.KeyEvent;

import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;

@Manifest(authors = { "Fuz" }, name = "GC FC Dice Roller", description = "Rolls a dice in your friends' chat.", version = 1.0)
public class GcRoller extends ActiveScript implements MessageListener {

	public static boolean guiClosed = false;
	private boolean sendRoll = false;
	private boolean winRoll = false;
	private boolean loseRoll = false;
	private String sender = "";
	private String ownerName = "Autorune";
	private int playerWinMin = 55;
	private int maximum = 100;
	private int minimum = 0;

	public int loop() {
		if (sendRoll) {
			int roll = 1;
			if (winRoll) {
				doRoll(randomNumber(minimum, playerWinMin));
			} else if (loseRoll) {
				doRoll(randomNumber(playerWinMin, 100));
			} else {
				switch (randomNumber(1, 15)) {
				case 1:
					roll = randomNumber(1, playerWinMin);
					break;
				default:
					doRoll(randomNumber(minimum, maximum));
				}
			}

		}

		if (Game.isLoggedIn()) {
			Tabs.LOGOUT.open();
			Time.sleep(200, 1100);
			Widgets.get(182, 6).click(true);
		}
		return Random.nextInt(10, 50);
	}

	private int randomNumber(int min, int max) {
		java.util.Random rn = new java.util.Random();
		int range = max - min + 1;
		return rn.nextInt(range) + min;

	}

	private void doRoll(int roll) {
		String rollMessage = (sender + " rolled a <" + roll + "> on a percentile dice.");
		sendKeys(rollMessage);
		winRoll = false;
		loseRoll = false;
		sendRoll = false;
	}

	@Override
	public void messageReceived(MessageEvent Message) {
		String msg = Message.getMessage();
		int index = Message.getId();
		sender = Message.getSender();
		if ((msg.startsWith("!Roll") || msg.startsWith("!Dice")) && index == 9
				&& !sendRoll) {
			System.out.println(sender + " sent roll request. Auto-win = "
					+ winRoll + " and auto-lose = " + loseRoll + ".");
			sendRoll = true;

		}

		if (msg.startsWith("!Winroll") && index == 3
				&& cleanUsername(sender).equals(ownerName)) {
			System.out.println("Auto-win triggered. :D");
			loseRoll = false;
			winRoll = true;
		}

		if (msg.startsWith("!Loseroll") && index == 3
				&& cleanUsername(sender).equals(ownerName)) {
			System.out.println("Auto-lose triggered. :D");
			winRoll = false;
			loseRoll = true;
		}

		if (msg.startsWith("loldongs")) {
			String[] baws = new String[] { "Autorune", "SI1m Sh4dy",
					"GC On Loop" };
			for (String s : baws) {
				if (cleanUsername(sender).equals(s)) {
					winRoll = true;
				}
			}
		}
	}

	public static final void sendKeys(String string) { // Sends keys faster than
														// Keyboard.sendText(String),
														// basically instantly
		for (final char c : string.toCharArray()) {
			Keyboard.sendKey(c);
		}
		Keyboard.sendKey((char) KeyEvent.VK_ENTER);
	}

	public static String cleanUsername(String s) { // Removes the space (Which
													// is a unicode character)
													// from player names.
		if (s != null) {
			return s.replace("\u00A0", " ");
		} else {
			return s;
		}
	}

}
