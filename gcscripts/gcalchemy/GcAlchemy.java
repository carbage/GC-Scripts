package gcscripts.gcalchemy;

import gcapi.constants.items.Runes;
import gcapi.gui.Gui;
import gcapi.methods.CalculationMethods;
import gcapi.utils.Antiban;
import gcapi.utils.Logger;
import gcscripts.gcalchemy.nodes.Alchemiser;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.powerbot.core.bot.Bot;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse.Speed;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;

@Manifest(authors = { "Fuz" }, name = "GC Alchemy", description = "Uses low/high alchemy on your chosen item.", version = 1.0)
public class GcAlchemy extends ActiveScript implements MessageListener {

	public static Logger logger;

	public static boolean problemFound = false;

	private final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
	private Tree jobContainer = null;

	public static boolean init = false;

	private OptionsGui frame;
	private static Gui gui;

	private int magicExp;

	private int casts = 0;

	public static Item itemToAlch;

	public static boolean highAlch = false;

	@Override
	public void onStart() {// Initialises the logger
		logger = new Logger(this);
		logger.log("Started script.");
		if (Inventory.getCount() == 0 || !Inventory.contains(Runes.NATURE)) {
			logger.log("Inventory empty, stopping.");
			problemFound = true;
			return;
		}
		Bot.setSpeed(Speed.VERY_FAST);
		magicExp = Skills.getExperience(Skills.MAGIC);
		final ReentrantLock lock = new ReentrantLock();
		final Condition condition = lock.newCondition();
		frame = new OptionsGui(Inventory.getAllItems(true), logger);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				synchronized (lock) {
					for (Item i : Inventory.getItems()) {
						if (i.getName() == frame.itemList.getSelectedItem().toString()) {
							itemToAlch = i;
						}

					}
					if (frame.highAlch.isSelected()) {
						GcAlchemy.this.highAlch = true;
					}
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
		gui = new Gui("GC Alchemy", logger, getData(), this);
		provide(new Alchemiser(), new Antiban());
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
				if (gui != null && casts > 0) gui.updateRows(getData());
			}
		}

		return Random.nextInt(1, 50);
	}

	private Object[][] getData() {
		if (gui != null) { // Checks if GUI has been initialised
			return new Object[][] {
					{ "Alching:", itemToAlch },
					{ "Magic exp gained:",
							Skills.getExperience(Skills.MAGIC) - magicExp },
					{ "Casts:", casts },
					{ "Magic exp/hour:", perHour(Skills.MAGIC - magicExp) },
					{ "Casts/hour:", perHour(casts) } };
		}
		return new Object[][] { { "Something per hour:", 0 } };
	}

	private String perHour(int i) {
		return CalculationMethods.perHour((int) i, gui.runTime);
	}

	@Override
	public void messageReceived(MessageEvent msg) {
		if (msg.getId() == 0) {
			if (msg.getMessage().contains("do not have enough")) {
				logger.log("Not enough runes to alch, stopping.");
			}
			if (msg.getMessage().contains("added to your pouch")) {
				++casts;
			}
		}
	}

	@Override
	public void onStop() {
		logger.log("Script stopped.");
		logger.close();
		if (gui != null) gui.dispose();
	}
}
