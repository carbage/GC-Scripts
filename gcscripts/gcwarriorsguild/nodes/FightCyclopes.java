package gcscripts.gcwarriorsguild.nodes;

import gcapi.constants.Areas;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.interactive.Player;

public class FightCyclopes extends Node {

    @Override
    public boolean activate() {
	return Areas.WARRIORS_GUILD_CYCLOPS_AREA.contains(Players.getLocal().getLocation());
    }

    @Override
    public void execute() {
	if (Players.getLocal().isIdle() && !Players.getLocal().isInCombat()) {
	    NPCs.getNearest(gcapi.constants.NPCs.CYCLOPES).click(true);
	}
    }

}
