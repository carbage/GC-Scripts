package gcscripts.gcwarriorsguild.branches;

import gcscripts.gcwarriorsguild.GcWarriorsGuild;

import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;

public class DefenderFarmer extends Branch {

    public DefenderFarmer(Node[] nodes) {
	super(nodes);
    }

    @Override
    public boolean branch() {
	return Game.isLoggedIn() && !GcWarriorsGuild.collectingTokens;
    }

}
