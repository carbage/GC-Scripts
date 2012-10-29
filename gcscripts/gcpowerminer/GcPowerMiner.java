package gcscripts.gcpowerminer;

import gcapi.gui.Gui;
import gcapi.methods.CalculationMethods;
import gcapi.utils.Logger;
import gcscripts.gcpowerminer.nodes.CookBacon;
import gcscripts.gcpowerminer.nodes.EatBacon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Job;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.util.Random;

@Manifest(authors = { "Fuz" }, name = "GC Power Miner", description = "Mines bacon and cooks from the closest fridges and then eats them.", version = 9000.1)
public class GcPowerMiner extends ActiveScript implements MessageListener {

	public static Logger logger;

	public static boolean problemFound = false;

	private final List<Node> fridge = Collections
			.synchronizedList(new ArrayList<Node>());
	private Tree fryingPan = null;

	private int oresMined;

	private static Gui gui;

	@Override
	public void onStart() {
		logger = new Logger(this); // Initialises the logger
		logger.log("Started script.");
		gui = new Gui("GC Power Miner", logger, getData(), this); // Initialises the GUI
		provide(new CookBacon(), new EatBacon()); // Provides nodes for mining and dropping
		// new Branch(new CookBacon(), new EatBacon());
		// fryingPan = new Tree();
	}

	private int getOresMined() {
		return oresMined;
	}

	public synchronized final void provide(final Node... jobs) {
		for (final Node job : jobs) {
			if (!fridge.contains(job)) {
				fridge.add(job);
				logger.log("Provided node: " + job.getClass().getSimpleName());
			}
		}
		fryingPan = new Tree(fridge.toArray(new Node[fridge.size()])); // Reconstructs the updated tree
	}

	public synchronized final void revoke(final Node... jobs) {
		for (final Node job : jobs) {
			if (fridge.contains(job)) {
				fridge.remove(job);
				logger.log("Revoked node: " + job.getClass().getSimpleName());
			}
		}
		fryingPan = new Tree(fridge.toArray(new Node[fridge.size()])); // Reconstructs the updated tree
	}

	public final void submit(final Job... jobs) {
		for (final Job job : jobs) {
			getContainer().submit(job);
		}
	}

	@Override
	public int loop() {
		if (gui != null && !gui.isVisible())
			gui.setVisible(true);
		
		if (problemFound) {
			this.stop();
			Game.logout(true);
		}

		if (fryingPan != null) {
			final Node job = fryingPan.state();
			if (job != null) { // Checks that node is not null
				fryingPan.set(job); // Sets node to state
				getContainer().submit(job); // Activates node
				job.join();
			}
		}
		
		return Random.nextInt(10, 50);
	}

	@Override
	public void messageReceived(MessageEvent msg) {
		if (msg.getId() == 109) { // Checks if message sent by game server
			if (msg.getMessage().contains("mine some")) {
				logger.log("Ore successfully mined.");
				oresMined++;
				if (getData() != null && gui != null) // Why bother updating on loop? Waste of CPU
					gui.updateRows(getData());
			}
			if (msg.getMessage().contains("a pickaxe")) {
				logger.log("Player does not have a pickaxe, stopping.");
				problemFound = true;
			} else if (msg.getMessage().contains("level")) {
				logger.log("Player's mining level is too low, stopping.");
				problemFound = true;
			}
		}
	}

	private Object[][] getData() {
		if (gui != null) { // Checks if GUI has been initialised
			return new Object[][] {
					{ "Ores mined:", getOresMined() },
					{ "Exp gained:", getOresMined() * 35 },
					{ "Ores per hour:", CalculationMethods.perHour(getOresMined(), gui.runTime) },
					{ "Exp per hour:", CalculationMethods.perHour(getOresMined() * 35, gui.runTime) } };
		}
		return new Object[][] { // Displays values as 0 because GUI not initialised yet
				{ "Ores mined:", 0 },
				{ "Exp gained:", 0 },
				{ "Ores per hour:", 0 },
				{ "Exp per hour:", 0 } };
	}

	@Override
	public void onStop() {
		logger.log("Script stopped.");
		logger.close();
		gui.dispose();
	}
}
