package gcscripts.gcwarriorsguild.nodes;

import gcapi.constants.Areas;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.interactive.Player;

public class FightCyclopes extends Node {
		
	private int[] CYCLOPS_IDS = { };
	
	Player player = Players.getLocal();

	@Override
	public boolean activate() {
		return Areas.WARRIORS_GUILD_CYCLOPS_AREA.contains(player.getLocation());
	}

	@Override
	public void execute() {
		if (player.isIdle() && !player.isInCombat()) {
			NPCs.getNearest(gcapi.constants.NPCs.CYCLOPES).click(true);
		}
	}

}
