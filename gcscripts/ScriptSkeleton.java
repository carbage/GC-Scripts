package gcscripts;

import gcapi.gui.Gui;
import gcapi.utils.Antiban;
import gcapi.utils.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Job;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.util.Random;

@Manifest(authors = { "Fuz" }, name = "Script Skeleton", description = "Script skeleton", version = 1.0)
public class ScriptSkeleton extends ActiveScript {

	public static Logger logger;

	public static boolean problemFound = false;

	private final List<Node> jobsCollection = Collections
			.synchronizedList(new ArrayList<Node>());
	private Tree jobContainer = null;

	private static Gui gui;

	@Override
	public void onStart() {
		logger = new Logger(this); // Initialises the logger
		logger.log("Started script.");
		gui = new Gui("ScriptSkeleton", logger, getData(), this); // Initialises
																	// GUI
		provide(new Antiban()); // Provides nodes
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
		if(gui != null) gui.dispose();
	}
}