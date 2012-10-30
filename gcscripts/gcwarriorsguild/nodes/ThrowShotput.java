package gcscripts.gcwarriorsguild.nodes;

import gcapi.constants.Areas;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.interactive.Player;

public class ThrowShotput extends Node {
	
	Player player = Players.getLocal();

	@Override
	public boolean activate() {
		return Areas.WARRIORS_GUILD_SHOTPUT_AREA.contains(player.getLocation());
	}

	@Override
	public void execute() {
		
	}

}
