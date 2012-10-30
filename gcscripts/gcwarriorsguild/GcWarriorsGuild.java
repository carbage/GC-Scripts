package gcscripts.gcwarriorsguild;

import gcapi.constants.Areas;
import gcapi.gui.Gui;
import gcapi.utils.Antiban;
import gcapi.utils.Logger;
import gcscripts.gcwarriorsguild.branches.TokenFarmer;
import gcscripts.gcwarriorsguild.nodes.CheckForFood;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Job;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.interactive.Player;

@Manifest(authors = { "Fuz" }, name = "GC Warrior's Guild", description = "Gathers tokens with shotput/Gathers defenders", version = 1.0)
public class GcWarriorsGuild extends ActiveScript {

	public static Logger logger;

	public static boolean problemFound = false;

	private final List<Node> jobsCollection = Collections
			.synchronizedList(new ArrayList<Node>());
	private Tree jobContainer = null;

	private static Gui gui;
	
	public static boolean collectingTokens = true;
	public static int defenderId;
	public static boolean guiClosed = false;
	

	@Override
	public void onStart() {
		logger = new Logger(this); // Initialises the logger
		logger.log("Started script.");
		Player player = Players.getLocal();
		if (!Areas.WARRIORS_GUILD_FIRST_FLOOR.contains(player)
				&& Areas.WARRIORS_GUILD_SECOND_FLOOR.contains(player)
				&& Areas.WARRIORS_GUILD_THIRD_FLOOR.contains(player)) {
			problemFound = true;
		} else  {
			new SelectorGui(this);
			while(!guiClosed) {}			
			gui = new Gui("GC Warriors' Guild", logger, getData(), this); // Initialises
			if(collectingTokens) {
				provide(new TokenFarmer(new Node[] { } ));
			}
			provide(new CheckForFood(), new Antiban()); // Provides antiban
		}
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

	public synchronized final void revoke(final Node... jobs) {
		for (final Node job : jobs) {
			if (jobsCollection.contains(job)) {
				jobsCollection.remove(job);
				logger.log("Revoked node: " + job.getClass().getSimpleName());
			}
		}
		jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection
				.size()])); // Reconstructs the updated tree
	}

	public final void submit(final Job... jobs) {
		for (final Job job : jobs) {
			getContainer().submit(job);
		}
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
		if (gui != null) { // Checks if GUI has been initialised
			return new Object[][] { { "Value:", 0 } };
		}
		return new Object[][] { { "Value:", 0 } };
	}

	@Override
	public void onStop() {
		logger.log("Script stopped.");
		logger.close();
		gui.dispose();
	}
}