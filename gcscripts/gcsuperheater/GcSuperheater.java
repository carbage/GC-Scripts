package gcscripts.gcsuperheater;

import gcapi.constants.items.Bars;
import gcapi.constants.items.Runes;
import gcapi.constants.recipes.Smithing;
import gcapi.gui.Gui;
import gcapi.methods.CalculationMethods;
import gcapi.methods.GenericMethods;
import gcapi.utils.Antiban;
import gcapi.utils.Logger;
import gcapi.utils.PriceChecker;
import gcscripts.gcsuperheater.nodes.Banker;
import gcscripts.gcsuperheater.nodes.Superheater;

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
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;

@Manifest(authors = { "Fuz" }, name = "GC Superheater", description = "Superheats ore into bars.", version = 1.0)
public class GcSuperheater extends ActiveScript implements MessageListener {

	public static Logger logger;

	public static boolean problemFound = false;

	private final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
	private Tree jobContainer = null;

	public static boolean init = false;

	private OptionsGui frame;
	public static Gui gui;

	private static int magicExp;
	private static int smithingExp;

	public static int casts = 0;

	private static String barToMake;

	protected static int[][] barRecipe;
	protected static int barId;
	public static int[] primaryOre;
	public static int[] secondaryOre;

	public static boolean isBanking = false;

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
		smithingExp = Skills.getExperience(Skills.SMITHING);
		final ReentrantLock lock = new ReentrantLock();
		final Condition condition = lock.newCondition();
		frame = new OptionsGui(Inventory.getAllItems(true), logger);
		frame.addWindowListener(new WindowAdapter() {
			private Object barToHeat;

			@Override
			public void windowClosing(WindowEvent arg0) {
				synchronized (lock) {
					GcSuperheater.barToMake = (String) frame.barList.getSelectedItem();
					switch (barToMake) {
						case "Bronze":
							GcSuperheater.barRecipe = Smithing.BRONZE;
							GcSuperheater.barId = Bars.BRONZE;
							GcSuperheater.primaryOre = Smithing.BRONZE[0];
							GcSuperheater.secondaryOre = Smithing.BRONZE[1];
							break;
						case "Iron":
							GcSuperheater.barRecipe = Smithing.IRON;
							GcSuperheater.barId = Bars.IRON;
							GcSuperheater.primaryOre = Smithing.IRON[0];
							GcSuperheater.secondaryOre = null;
							break;
						case "Silver":
							GcSuperheater.barRecipe = Smithing.SILVER;
							GcSuperheater.barId = Bars.SILVER;
							GcSuperheater.primaryOre = Smithing.SILVER[0];
							GcSuperheater.secondaryOre = null;
							break;
						case "Steel":
							GcSuperheater.barRecipe = Smithing.STEEL;
							GcSuperheater.barId = Bars.STEEL;
							GcSuperheater.primaryOre = Smithing.STEEL[0];
							GcSuperheater.secondaryOre = Smithing.STEEL[1];
							break;
						case "Gold":
							GcSuperheater.barRecipe = Smithing.GOLD;
							GcSuperheater.barId = Bars.GOLD;
							GcSuperheater.primaryOre = Smithing.GOLD[0];
							GcSuperheater.secondaryOre = null;
							break;
						case "Mithril":
							GcSuperheater.barRecipe = Smithing.MITHRIL;
							GcSuperheater.barId = Bars.MITHRIL;
							GcSuperheater.primaryOre = Smithing.MITHRIL[0];
							GcSuperheater.secondaryOre = Smithing.MITHRIL[1];
							break;
						case "Adamant":
							GcSuperheater.barRecipe = Smithing.ADAMANT;
							GcSuperheater.barId = Bars.ADAMANT;
							GcSuperheater.primaryOre = Smithing.ADAMANT[0];
							GcSuperheater.secondaryOre = Smithing.ADAMANT[1];
							break;
						case "Runite":
							GcSuperheater.barRecipe = Smithing.RUNITE;
							GcSuperheater.barId = Bars.RUNITE;
							GcSuperheater.primaryOre = Smithing.RUNITE[0];
							GcSuperheater.secondaryOre = Smithing.RUNITE[1];
							break;
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
		gui = new Gui("GC Superheater", logger, getData(), this);
		provide(new Superheater(), new Banker(), new Antiban());
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
					{ "Making:", barToMake },
					{ "Profit/bar:",
							CalculationMethods.format(getProfitPerBar()) },
					{ "Magic exp gained:",
							CalculationMethods.format(getMagicExpGained()) },
					{ "Smithing exp gained:",
							CalculationMethods.format(getSmithingExpGained()) },
					{ "Bars smelted:", CalculationMethods.format(casts) },
					{ "Profit:", CalculationMethods.format(getTotalProfit()) },
					{ "Magic exp/hour:", perHour(getMagicExpGained()) },
					{ "Smithing exp/hour:", perHour(getSmithingExpGained()) },
					{ "Bars/hour:", perHour(casts) },
					{ "Profit/hour:", perHour(getTotalProfit()) } };
		}
		return new Object[][] { { "Making:", "Nothing" }, { "Making:", 0 },
				{ "Profit/bar:", 0 }, { "Magic exp gained:", 0 },
				{ "Smithing exp gained:", 0 }, { "Bars smelted:", 0 },
				{ "Profit:", 0 }, { "Magic exp/hour:", 0 },
				{ "Smithing exp/hour:", 0 }, { "Bars/hour:", 0 },
				{ "Profit/hour:", 0 } };
	}

	private static int getMagicExpGained() {
		return Skills.getExperience(Skills.MAGIC) - magicExp;
	}

	private static int getSmithingExpGained() {
		return Skills.getExperience(Skills.SMITHING) - smithingExp;
	}

	@SuppressWarnings("deprecation")
	private static int getProfitPerBar() {
		int barPrice = 0;
		int orePrice = 0;
		int runePrice = 0;
		try {
			barPrice = PriceChecker.getPrice(barId);
			orePrice = PriceChecker.getPrice(primaryOre[0]);
			if (secondaryOre != null) {
				orePrice += PriceChecker.getPrice(secondaryOre[0]) * secondaryOre[1];
			}
			runePrice = PriceChecker.getPrice(Runes.NATURE);
			if (Inventory.contains(Runes.FIRE)) {
				runePrice += PriceChecker.getPrice(Runes.FIRE) * 4;
			}
		} catch (IOException e) {
			logger.log(e.getMessage());
		}
		return barPrice - orePrice - runePrice;
	}

	private static int getTotalProfit() {
		return casts * getProfitPerBar();
	}

	private static String perHour(int i) {
		return CalculationMethods.perHour((int) i, gui.runTime);
	}

	@Override
	public void messageReceived(MessageEvent msg) {
		if (msg.getId() == 0) {
			if (msg.getMessage().contains("do not have enough")) {
				logger.log("Not enough runes to superheat, stopping.");
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
