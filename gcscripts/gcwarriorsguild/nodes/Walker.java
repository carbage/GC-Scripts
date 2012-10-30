package gcscripts.gcwarriorsguild.nodes;

import gcapi.constants.Areas;
import gcapi.methods.LocationMethods;
import gcscripts.gcwarriorsguild.GcWarriorsGuild;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.Player;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class Walker extends Node {

	Player player = Players.getLocal();
	
	private int[] STAIRCASE_IDS = { 66795, 66796, 66797 };	
	
	private final static int HEAVY_DOOR_ID = 15658;
	private final static int SHOTPUT_DOOR_ID = 66758;
	private final static int CYCLOPS_DOOR_ID = 66599;

	@Override
	public boolean activate() {
		return player.isIdle() 
				&& Game.isLoggedIn()
				&& !GcWarriorsGuild.isBanking
				&& (Areas.WARRIORS_GUILD_FIRST_FLOOR.contains(player.getLocation()) 
						|| Areas.WARRIORS_GUILD_SECOND_FLOOR.contains(player.getLocation()) 
						|| Areas.WARRIORS_GUILD_THIRD_FLOOR.contains(player.getLocation()));
	}

	@Override
	public void execute() {
		
		SceneObject staircase = SceneEntities.getNearest(STAIRCASE_IDS);
		Tile staircaseTile = LocationMethods.getObjectBox(staircase).getNearest();
		if(staircase == null) {
			GcWarriorsGuild.logger.log("No staircases nearby, stopping script.");
			GcWarriorsGuild.problemFound = true;
		}
		
		if (GcWarriorsGuild.collectingTokens) {
			if (Areas.WARRIORS_GUILD_FIRST_FLOOR.contains(player.getLocation())) {
				GcWarriorsGuild.logger.log("Player is on first floor, moving upstairs.");
				Walking.walk(staircaseTile);
				while (Walking.getDestination() != staircaseTile) {
					Walking.walk(staircaseTile);
				}
				while (!staircase.isOnScreen()) { }
				staircase.click(true);
			}
			if (Areas.WARRIORS_GUILD_SECOND_FLOOR.contains(player.getLocation())) {
				if (player.getLocation().getX() <= 2852 && player.getLocation().getX() > 2852 && player.getPlane() == 2) {
					GcWarriorsGuild.logger.log("Player is behind heavy door, moving through door.");
					SceneObject door = SceneEntities.getNearest(HEAVY_DOOR_ID);
					if( door != null) {
						Camera.turnTo(door);
						if (door.isOnScreen()) {
							door.click(true);
						} else {
							GcWarriorsGuild.logger.log("Could not find door, stopping.");
							GcWarriorsGuild.problemFound = true;
						}
					}
					
				} else if (player.getLocation().getX() > 2852 && player.getPlane() == 2) {
					GcWarriorsGuild.logger.log("Player is behind door to shotput room, moving through door.");
					SceneObject door = SceneEntities.getNearest(SHOTPUT_DOOR_ID);
					if( door != null) {
						Camera.turnTo(door);
						if (door.isOnScreen()) {
							door.click(true);
						} else {
							GcWarriorsGuild.logger.log("Could not find door, stopping.");
							GcWarriorsGuild.problemFound = true;
						}
					}
					
				} else if (Areas.WARRIORS_GUILD_SHOTPUT_ROOM.contains(player.getLocation())) {
					GcWarriorsGuild.logger.log("Player is in the shotput room, continuing to shotput range.");
					Tile tile = Areas.WARRIORS_GUILD_SHOTPUT_AREA.getCentralTile();
					if (tile.canReach()) {
						Walking.walk(tile);
					} else {
						GcWarriorsGuild.logger.log("Could not reach shotput area, stopping.");
						GcWarriorsGuild.problemFound = true;
					}
				}
			}
			if (Areas.WARRIORS_GUILD_THIRD_FLOOR.contains(player.getLocation())) {
				GcWarriorsGuild.logger.log("Player is on third floor, moving downstairs.");
				if(Areas.WARRIORS_GUILD_CYCLOPS_AREA.contains(player.getLocation())) {
					GcWarriorsGuild.logger.log("Player is in cyclops area, leaving room.");
					SceneEntities.getNearest(CYCLOPS_DOOR_ID).click(true);
				}
				Walking.walk(staircaseTile);
				while (Walking.getDestination() != staircaseTile) {
					Walking.walk(staircaseTile);
				}
				while (!staircase.isOnScreen()) { }
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
