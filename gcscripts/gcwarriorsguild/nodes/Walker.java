package gcscripts.gcwarriorsguild.nodes;

import gcapi.constants.Areas;
import gcapi.methods.LocationMethods;
import gcscripts.gcwarriorsguild.GcWarriorsGuild;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.Player;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class Walker extends Node {

	Player player = Players.getLocal();
	
	private int[] STAIRCASE_IDS = { 66795, 66796, 66797 };	

	private final static int SHOTPUT_PILE_ID = 15665;
	
	private final static int HEAVY_DOOR_ID = 15658;
	private final static int SHOTPUT_DOOR_ID = 66758;
	private final static int CYCLOPS_DOOR_ID = 66599;

	@Override
	public boolean activate() {
		return Game.isLoggedIn()
				&& !GcWarriorsGuild.isBanking
				&& (Areas.WARRIORS_GUILD_FIRST_FLOOR.contains(player.getLocation()) 
						|| Areas.WARRIORS_GUILD_SECOND_FLOOR.contains(player.getLocation()) 
						|| Areas.WARRIORS_GUILD_THIRD_FLOOR.contains(player.getLocation()));
	}

	@Override
	public void execute() {
		
		SceneObject staircase = SceneEntities.getNearest(STAIRCASE_IDS);
		Tile staircaseTile = LocationMethods.getObjectBox(staircase).getNearest();
		
		if (GcWarriorsGuild.collectingTokens) {
			if (Areas.WARRIORS_GUILD_FIRST_FLOOR.contains(player.getLocation())) {
				GcWarriorsGuild.logger.log("Player is on first floor, moving upstairs.");
				Walking.walk(staircaseTile);
				staircase.click(true);
			}
			if (Areas.WARRIORS_GUILD_SECOND_FLOOR.contains(player.getLocation())) {
				if (player.getLocation().getX() <= 2852 && player.getLocation().getX() > 2852 && player.getPlane() == 2) {
					GcWarriorsGuild.logger.log("Player is behind heavy door, moving through door.");
					SceneEntities.getNearest(HEAVY_DOOR_ID).click(true);
					
				} else if (player.getLocation().getX() > 2852 && player.getPlane() == 2) {
					GcWarriorsGuild.logger.log("Player is behind door to shotput room, moving through door.");
					SceneEntities.getNearest(SHOTPUT_DOOR_ID).click(true);
					
				} else if (Areas.WARRIORS_GUILD_SHOTPUT_ROOM.contains(player.getLocation())) {
					GcWarriorsGuild.logger.log("Player is in the shotput room, continuing to shotput range.");
					Walking.walk(Areas.WARRIORS_GUILD_SHOTPUT_AREA.getCentralTile());
					SceneEntities.getNearest(SHOTPUT_PILE_ID).click(true);
				}
			}
			if (Areas.WARRIORS_GUILD_THIRD_FLOOR.contains(player.getLocation())) {
				GcWarriorsGuild.logger.log("Player is on third floor, moving downstairs.");
				if(Areas.WARRIORS_GUILD_CYCLOPS_AREA.contains(player.getLocation())) {
					GcWarriorsGuild.logger.log("Player is in cyclops area, leaving room.");
					SceneEntities.getNearest(CYCLOPS_DOOR_ID).click(true);
				}
				Walking.walk(staircaseTile);
				staircase.click(true);
			}
		} else {
			if (Areas.WARRIORS_GUILD_FIRST_FLOOR.contains(player.getLocation())) {

			}
			if (Areas.WARRIORS_GUILD_SECOND_FLOOR.contains(player.getLocation())) {

			}
			if (Areas.WARRIORS_GUILD_THIRD_FLOOR.contains(player.getLocation())) {

			}
		}
	}

}
