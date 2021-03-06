package gcscripts.gcalchemy;

import gcapi.constants.items.Runes;
import gcapi.gui.Gui;
import gcapi.methods.CalculationMethods;
import gcapi.utils.Antiban;
import gcapi.utils.Logger;
import gcapi.utils.PriceChecker;
import gcscripts.gcalchemy.nodes.Alchemiser;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
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
	public static Gui gui;

	private static int magicExp;

	public static int casts = 0;

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
						logger.log("Selected item: " + frame.itemList.getSelectedItem());
						if (i.getName().equals(frame.itemList.getSelectedItem())) {
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
			}
		}

		return Random.nextInt(1, 50);
	}

	public static Object[][] getData() {
		if (gui != null) { // Checks if GUI has been initialised
			return new Object[][] {
					{ "Alching:", itemToAlch.getName() },
					{ "Magic exp gained:",
							CalculationMethods.format(getMagicExpGained()) },
					{ "Casts:", CalculationMethods.format(casts) },
					{ "Profit:", CalculationMethods.format(getProfitPerAlch()) },
					{ "Magic exp/hour:", perHour(getMagicExpGained()) },
					{ "Casts/hour:", perHour(casts) },
					{ "Profit/hour:", perHour(getTotalProfit()) } };
		}
		return new Object[][] { { "Alching:", 0 }, { "Magic exp gained:", 0 },
				{ "Casts:", 0 }, { "Profit:", 0 }, { "Magic exp/hour:", 0 },
				{ "Casts/hour:", 0 } };
	}
	
	private static int getMagicExpGained() {
		return Skills.getExperience(Skills.MAGIC) - magicExp;
	}

	private static String perHour(int i) {
		return CalculationMethods.perHour((int) i, gui.runTime);
	}

	@SuppressWarnings("deprecation")
	private static int getProfitPerAlch() {
		int itemPrice = 0;
		int runePrice = 0;
		try {
			itemPrice = PriceChecker.getPrice(itemToAlch.getId());
			runePrice = PriceChecker.getPrice(Runes.NATURE);
			if (Inventory.contains(Runes.FIRE)) {
				runePrice += PriceChecker.getPrice(Runes.FIRE) * 4;
			}
		} catch (IOException e) {
			logger.log(e.getMessage());
		}
		return itemPrice - runePrice;
	}
	
	private static int getTotalProfit() {
		return getProfitPerAlch() * casts;
	}

	@Override
	public void messageReceived(MessageEvent msg) {
		if (msg.getId() == 0 || msg.getId() == 109) {
			if (msg.getMessage().contains("do not have enough")) {
				logger.log("Not enough runes to alch, stopping.");
			}
			if (msg.getMessage().contains("added to your money pouch")) {
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
