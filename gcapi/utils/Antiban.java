package gcapi.utils;

import gcapi.constants.interfaces.Main;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.Player;

public class Antiban extends Node {

	int[] tabs = { 71, 72, 73, 74, 75, 89, 90, 91, 92, 93, 94, 95, 96 };
	int[] skills = { 1, 2, 3, 4, };

	/** Our timer */
	private Timer antiBanTimer;

	/** Our min and max times */
	private int minTime = (20 * 1000); // 20 seconds
	private int maxTime = (120 * 1000); // 2 minutes, or 120 seconds

	private boolean isBusy;

	public Antiban() {
		antiBanTimer = new Timer(Random.nextInt(minTime, maxTime)); // Do an anti ban action at least once between 20 seconds and two minutes
		System.out.println("Started anti-ban task.");
	}

	public void execute() {
		if (Game.isLoggedIn() & !antiBanTimer.isRunning()) {
			int action = Random.nextInt(0, 6);
			switch (action) {
				case 0:
					// Randomly click a tab and go back to inventory.
					System.out.println("Clicking random tab.");
					int tab = tabs[Random.nextInt(0, tabs.length)];
					Widgets.get(Main.TAB_AREA_PARENT, tab).click(true);
					Time.sleep(200, 350);
					if (tab == 71) {
						int skill = skills[Random.nextInt(0, skills.length)];
						Widgets.get(Main.SKILLS_TAB_PARENT, skill).hover();
						Time.sleep(200, 350);
					}
					Widgets.get(Main.TAB_AREA_PARENT, Main.TAB_AREA_INVENTORY_TAB).click(true);
					break;

				case 1:
					// Right click random player
					System.out.println("Right clicking random player.");
					Player[] players = Players.getLoaded();
					if (players.length >= 1) {
						Player player = players[Random.nextInt(0, players.length - 1)];
						player.click(false);
						Time.sleep(Random.nextInt(75, 250));
						Mouse.move(Random.nextInt(550, 735), Random.nextInt(205, 450));
					}
					break;

				case 3:
				case 4:
				default:
					// Moving mouse randomly.
					System.out.println("Moving mouse randomly.");
					if (Players.getLocal().getInteracting() != null) {
						int randomXMin = Random.nextInt(1, 450);
						int randomYMin = Random.nextInt(1, 450);
						int randomXMax = Random.nextInt(1, 300);
						int randomYMax = Random.nextInt(1, 300);
						Mouse.move(randomXMin, randomYMin, randomXMax, randomYMax);
					}
					break;

				case 5:
				case 6:
					break;
			}
		}
		resetAntiBanTime();
	}

	private void resetAntiBanTime() {
		antiBanTimer.setEndIn(Random.nextInt(minTime, maxTime));
	}

	@Override
	public boolean activate() {
		return true;
	}

	/**
	 * Constructor. Initializes timer.
	 */
}
