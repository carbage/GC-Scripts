package gcscripts.gcrevenants;

import gcapi.gui.Gui;
import gcapi.methods.CalculationMethods;
import gcapi.utils.Logger;
import gcscripts.gcwarriorsguild.GcWarriorsGuild;
import gcscripts.gcwarriorsguild.OptionsGui;

import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JComboBox;

import org.powerbot.core.bot.Bot;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse.Speed;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;

@Manifest(authors = { "Fuz" }, name = "GC Revenants", description = "Kills revenants in forinthry dungeon.", version = 1.0)
public class GcRevenants extends ActiveScript implements MessageListener, PaintListener {

	public static Logger logger;

	public static boolean problemFound = false;

	private final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
	private Tree jobContainer = null;

	public static boolean init = false;

	private OptionsGui frame;

	private static Gui gui;

	public static enum Enum {
	}

	@Override
	public void onStart() {// Initialises the logger
		logger = new Logger(this);
		logger.log("Started script.");
		Bot.setSpeed(Speed.VERY_FAST);
		final ReentrantLock lock = new ReentrantLock();
		final Condition condition = lock.newCondition();
		frame = new OptionsGui();
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				synchronized (lock) {

					frame.setVisible(false);
					lock.notify();
				}
			}

		});
		Thread t = new Thread() {
			public void run() {
				synchronized (lock) {
					while (frame.isVisible())
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
			}
		};
		t.start();

		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gui = new Gui("GC Revenants", logger, getData(), this);
		init = true;
	}

	public synchronized final void provide(final Node... jobs) {
		for (final Node job : jobs) {
			if (!jobsCollection.contains(job)) {
				jobsCollection.add(job);
				logger.log("Provided branch/node: " + job.getClass().getSimpleName());
			}
		}
		jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()])); // Reconstructs the updated tree
	}

	@Override
	public int loop() {
		if (problemFound) {
			this.stop();
			Game.logout(true);
		}
		if (Game.isLoggedIn()) {
			if (init) {
				if (gui != null && !gui.isVisible()) gui.setVisible(true);

				if (jobContainer != null) {
					final Node job = jobContainer.state();
					if (job != null) { // Checks that node is not null
						jobContainer.set(job); // Sets node to state
						getContainer().submit(job); // Activates node
						job.join();
					}
				}
				if (gui != null) gui.updateRows(getData());
			}
		}

		return Random.nextInt(100, 500);
	}

	private Object[][] getData() {
		if (gui != null) { // Checks if GUI has been initialised
			return new Object[][] { { "Something per hour:",
					CalculationMethods.perHour((int) 0, gui.runTime) } };
		}
		return new Object[][] { { "Something per hour:",
				CalculationMethods.perHour((int) 0, gui.runTime) } };
	}

	@Override
	public void messageReceived(MessageEvent msg) {
	}

	@Override
	public void onRepaint(Graphics g) {
		if (Game.isLoggedIn() && Players.getLocal() != null) {
			if (Players.getLocal() != null) {

			}
		}
	}

	@Override
	public void onStop() {
		logger.log("Script stopped.");
		logger.close();
		if (gui != null) gui.dispose();
	}

	public static Enum getEnum() {
		return null;
	}
}
