package gcscripts.gcwarriorsguild;

import gcapi.constants.Areas;
import gcapi.gui.Gui;
import gcapi.utils.Antiban;
import gcapi.utils.Logger;
import gcscripts.gcwarriorsguild.branches.DefenderFarmer;
import gcscripts.gcwarriorsguild.branches.TokenFarmer;
import gcscripts.gcwarriorsguild.nodes.Banker;
import gcscripts.gcwarriorsguild.nodes.CheckForFood;
import gcscripts.gcwarriorsguild.nodes.DefenderCollector;
import gcscripts.gcwarriorsguild.nodes.FightCyclopes;
import gcscripts.gcwarriorsguild.nodes.ThrowShotput;
import gcscripts.gcwarriorsguild.nodes.Walker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Equipment;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.interactive.Player;

@Manifest(authors = { "Fuz" }, name = "GC Warrior's Guild", description = "Gathers tokens with shotput/Gathers defenders", version = 1.0)
public class GcWarriorsGuild extends ActiveScript implements MessageListener {

	public static Logger logger;

	public static boolean problemFound = false;

	private final List<Node> jobsCollection = Collections
			.synchronizedList(new ArrayList<Node>());
	private Tree jobContainer = null;

	private Object defendersCollected;

	private static SelectorGui selectorGui;
	private static Gui gui;

	public static boolean collectingTokens = true;
	public static int defenderId;
	public static String defenderType;
	public static boolean guiClosed = false;
	
	public static int initialTokens;
	public static int tokensGained;

	public static boolean isBanking = false;

	public static boolean hasDefender = false;

	@Override
	public void onStart() {
		logger = new Logger(this); // Initialises the logger
		logger.log("Started script.");
		Player player = Players.getLocal();
		initialTokens = getTokens();
		if (player == null) {
			problemFound = true;
		} else {
			if (!Areas.WARRIORS_GUILD_FIRST_FLOOR.contains(player.getLocation()) &&
					!Areas.WARRIORS_GUILD_SECOND_FLOOR.contains(player.getLocation()) &&
					!Areas.WARRIORS_GUILD_THIRD_FLOOR.contains(player.getLocation())) {
				logger.log("Player is not in the Warrios' Guild."); problemFound = true; 
			} else {
				selectorGui = new SelectorGui(this);
				logger.log("Initialised selection GUI.");
				while (!getGuiClosed() && selectorGui.isVisible()) {
				}
				selectorGui.dispose();
				gui = new Gui("GC Warriors' Guild", logger, getData(), this); // Initialises
				if (collectingTokens) {
					provide(new TokenFarmer(new Node[] {new ThrowShotput()}));
				} else {
					logger.log("Collecting " + defenderType + " defenders.");
					if ( Inventory.containsOneOf(defenderId -1) || Equipment.appearanceContainsOneOf(defenderId -1)) {
						logger.log("Player does not have any defenders, banking.");
						isBanking = true;
						hasDefender = false;
					}
					provide(new DefenderFarmer(new Node[] {new FightCyclopes(), new DefenderCollector()}));
				}
				provide(new CheckForFood(), new Walker(), new Banker(), new Antiban()); // Provides antiban
			}
		}
	}

	private int getTokens() {
		return Integer.parseInt(Widgets.get(1057, 16).getText()) - initialTokens;
	}

	private boolean getGuiClosed() {
		return guiClosed;
	}

	public synchronized final void provide(final Node... jobs) {
		for (final Node job : jobs) {
			if (!jobsCollection.contains(job)) {
				jobsCollection.add(job);
				logger.log("Provided node: " + job.getClass().getSimpleName());
			}
		}
		jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection
				.size()])); // Reconstructs the updated tree
	}

	@Override
	public int loop() {

		if (problemFound) {
			this.stop();
			Game.logout(true);
		}

		if (gui != null && !gui.isVisible())
			gui.setVisible(true);

		if (jobContainer != null) {
			final Node job = jobContainer.state();
			if (job != null) { // Checks that node is not null
				jobContainer.set(job); // Sets node to state
				getContainer().submit(job); // Activates node
				job.join();
			}
		}
		new Runnable() {
			public void run() {
				if (getData() != null && gui != null) // Why bother updating on
														// loop? Waste of CPU
					gui.updateRows(getData());
			}
		};

		return Random.nextInt(10, 50);
	}

	private Object[][] getData() {
		if (collectingTokens) { // Checks if collecting tokens
			if (gui != null) { // Checks if GUI has been initialised
				return new Object[][] { { "Value:", 0 }, { "Value:", 0 },
						{ "Value:", 0 }, { "Value:", 0 }, };
			}
			return new Object[][] { { "Value:", 0 }, { "Value:", 0 },
					{ "Value:", 0 }, { "Value:", 0 }, };
		} else { // Otherwise it's collecting defenders
			if (gui != null) { // Checks if GUI has been initialised
				return new Object[][] { { "Defender type:", defenderType }, { "Value:", defendersCollected },
						{ "Defenders per hour:", 0 }, { "Value:", 0 }, };
			}
			return new Object[][] { { "Value:", 0 }, { "Value:", 0 },
					{ "Value:", 0 }, { "Value:", 0 }, };
		}
	}

	@Override
	public void onStop() {
		logger.log("Script stopped.");
		logger.close();
		if (gui != null)
			gui.dispose();
	}

	@Override
	public void messageReceived(MessageEvent msg) {
		if (msg.getId() == 109) {
			String message = msg.getMessage();
			if( message.contains("thud.") || message.contains("thump.") || message.contains("floor.") || message.contains("thrown.")) {
				tokensGained = getTokens();
				gui.updateRows(getData());
			}
		}
	}
}