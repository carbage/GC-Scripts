package gcscripts.gcwarriorsguild.nodes;

import gcscripts.gcwarriorsguild.GcWarriorsGuild;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.interactive.Player;

public class ThrowShotput extends Node {
	
	Player player = Players.getLocal();
	
	private final static int HEAVY_DOOR_ID = 15658;
	private final static int DOOR_ID = 66758;

	@Override
	public boolean activate() {
		return GcWarriorsGuild.collectingTokens;
	}

	@Override
	public void execute() {
		if (player.getLocation().getX() <= 2852 && player.getLocation().getX() > 2852 && player.getHeight() == 2) {
			SceneEntities.getNearest(HEAVY_DOOR_ID).click(true);
		} else if (player.getLocation().getX() > 2852 && player.getHeight() == 2) {
			SceneEntities.getNearest(DOOR_ID).click(true);
		}
	}

}
