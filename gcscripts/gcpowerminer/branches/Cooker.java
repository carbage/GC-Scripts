package gcscripts.gcpowerminer.branches;

import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;

public class Cooker extends Branch {

    public Cooker(Node[] nodes) {
	super(nodes);
    }

    @Override
    public boolean branch() {
	return Game.isLoggedIn();
    }

}
