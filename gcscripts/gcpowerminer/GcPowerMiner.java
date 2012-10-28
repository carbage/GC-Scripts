package gcscripts.gcpowerminer;

import gcscripts.gcpowerminer.branches.Cooker;
import gcscripts.gcpowerminer.nodes.CookBacon;
import gcscripts.gcpowerminer.nodes.EatBacon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Job;
import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.util.Random;

@Manifest(authors = { "Fuz" }, name = "GC Power Miner", description = "Mines bacon and cooks from the closest fridges and then eats them.", version = 9000.1)
public class GcPowerMiner extends ActiveScript implements MessageListener {

	public static boolean problemFound = false;

	private final List<Node> fridge = Collections
			.synchronizedList(new ArrayList<Node>());
	private Tree fryingPan = null;
	
	private Branch cooker;
	
	@Override
    public void onStart() {
		cooker = new Cooker(new Node[] {new CookBacon(), new EatBacon()});
    }
	
	public synchronized final void provide(final Node... jobs) {
        for (final Node job : jobs) {
                if(!fridge.contains(job)) {
                        fridge.add(job);
                }
        }
        fryingPan = new Tree(fridge.toArray(new Node[fridge.size()]));
	}

	public synchronized final void revoke(final Node... jobs) {
		for (final Node job : jobs) {
			if (fridge.contains(job)) {
				fridge.remove(job);
			}
		}
		fryingPan = new Tree(fridge.toArray(new Node[fridge
				.size()]));
	}

	public final void submit(final Job... jobs) {
		for (final Job job : jobs) {
			getContainer().submit(job);
		}
	}

	@Override
	public int loop() {
		if (problemFound)
			return -1;

		// check if Tree is constructed
		if (fryingPan != null) {
			/*
			 * Gets first Node / Branch (state of Tree) that activates (remember
			 * priority system explained above) or null if none of
			 * Nodes/Branches activates
			 */
			final Node job = fryingPan.state();
			if (job != null) {
				// Sets the current Node to the running state
				fryingPan.set(job);
				// Sets job (Node) to work
				getContainer().submit(job);
				// Attempt to pause calling thread till execution of job is done
				job.join();
			}
		}

		return Random.nextInt(10, 50);
	}

	@Override
	public void messageReceived(MessageEvent msg) {
		if(msg.getId() == 3)
			if (msg.getMessage().contains("a pickaxe")) {
				System.out.println("Player does not have a pickaxe, stopping.");
				problemFound = true;
			} else if (msg.getMessage().contains("level")) {
				System.out.println("Player's mining level is too low, stopping.");
				problemFound = true;
			}
	}
	}

}
