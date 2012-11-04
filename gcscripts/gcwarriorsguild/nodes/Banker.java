package gcscripts.gcwarriorsguild.nodes;

import gcapi.constants.Areas;
import gcapi.constants.Equipment;
import gcapi.constants.Items;
import gcapi.constants.SceneObjects;
import gcapi.methods.GenericMethods;
import gcapi.methods.LocationMethods;
import gcscripts.gcwarriorsguild.GcWarriorsGuild;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.methods.widget.Bank.Amount;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.Player;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class Banker extends Node {

	Player player = Players.getLocal();
	private int[] STAIRCASE_IDS = { 66795, 66796, 66797 };
	private int CYCLOPS_DOOR_ID;

	@Override
	public boolean activate() {
		return GcWarriorsGuild.isBanking || !GcWarriorsGuild.hasDefender;
	}

	@Override
	public void execute() {
		SceneObject staircase = SceneEntities.getNearest(STAIRCASE_IDS);
		if (staircase == null) {
			GcWarriorsGuild.logger.log("No staircases nearby, stopping script.");
			GcWarriorsGuild.problemFound = true;
		}

		if (GcWarriorsGuild.isBanking) {
			switch (GcWarriorsGuild.getFloor()) {
				case GROUND:
					if (Areas.WARRIORS_GUILD_BANK_AREA.contains(Players.getLocal())) {
						if (Bank.open()) {
							GcWarriorsGuild.logger.log("Opened bank.");
							if (GcWarriorsGuild.isBanking && GcWarriorsGuild.hasDefender) {
								if (Inventory.contains(Equipment.DEFENDER_IDS)) {
									Bank.depositInventory();
								}
								Item food = Bank.getItem(Items.FOOD_IDS);
								if (food != null) {
									Bank.search(food.getName());
									food.getWidgetChild().interact("Withdraw-all");
									if(Inventory.isFull()) {
										Bank.close();
										GcWarriorsGuild.isBanking = false;										
									}
								} else {
									GcWarriorsGuild.logger.log("No food in bank, stopping.");
									GcWarriorsGuild.problemFound = true;
								}

							} else if (!GcWarriorsGuild.hasDefender) {
								Bank.search("defender");
								Item defender = Bank.getItem(GcWarriorsGuild.defenderId - 1);
								if (defender != null) {
									defender.getWidgetChild().click(true);
									GcWarriorsGuild.hasDefender = true;
									Bank.close();
									GcWarriorsGuild.isBanking = false;		
								} else {
									GcWarriorsGuild.logger.log("No defenders in bank, stopping.");
									GcWarriorsGuild.problemFound = true;
								}
							}
							GcWarriorsGuild.isBanking = false;
							GcWarriorsGuild.logger.log("Closed bank.");
						}
					} else {
						LocationMethods.walkToTile(Areas.WARRIORS_GUILD_BANK_AREA.getNearest());
					}
					break;

				case MIDDLE:
					if (Players.getLocal().getLocation().getX() <= 2852) {
						LocationMethods.walkToObject(staircase);
						Camera.turnTo(staircase);
						GenericMethods.waitForCondition(staircase.isOnScreen(), 10000);
						if (staircase.isOnScreen()) {
							staircase.interact("Climb-down");
						}
					} else if (Players.getLocal().getLocation().getX() >= 2853 && Players.getLocal().getLocation().getX() <= 2857) {
						SceneObject door = SceneEntities.getNearest(SceneObjects.HEAVY_DOOR);
						LocationMethods.walkToObject(door);
						Camera.turnTo(door);
						GenericMethods.waitForCondition(door.isOnScreen(), 10000);
						if (door.isOnScreen()) {
							door.interact("Open");
						}
					} else if (Areas.WARRIORS_GUILD_SHOTPUT_ROOM.contains(Players.getLocal())) {
						SceneObject door = SceneEntities.getNearest(SceneObjects.SHOTPUT_DOOR);
						if (door.getLocation().getX() == 2857) {
							LocationMethods.walkToObject(door);
							Camera.turnTo(door);
							GenericMethods.waitForCondition(door.isOnScreen(), 10000);
							if (door.isOnScreen()) {
								door.interact("Open");
							}
						} else {
							door = SceneEntities.getNearest(SceneObjects.HEAVY_DOOR);
							if (door != null) {
								LocationMethods.walkToObject(door);
								Camera.turnTo(door);
								GenericMethods.waitForCondition(door.isOnScreen(), 10000);
								if (door.isOnScreen()) {
									door.interact("Open");
								}
							}
						}

					}

					break;

				case TOP:
					if (Areas.WARRIORS_GUILD_CYCLOPS_AREA.contains(Players.getLocal())) {
						SceneObject door = SceneEntities.getNearest(SceneObjects.CYCLOPS_DOOR);
						LocationMethods.walkToObject(door);
						Camera.turnTo(door);
						GenericMethods.waitForCondition(door.isOnScreen(), 10000);
						if (door.isOnScreen()) {
							door.interact("Open");
						}
						break;
					}
					LocationMethods.walkToObject(staircase);
					Camera.turnTo(staircase);
					GenericMethods.waitForCondition(staircase.isOnScreen(), 10000);
					if (staircase.isOnScreen()) {
						staircase.interact("Climb-down");
					}
					break;

				default:
					GcWarriorsGuild.problemFound = true;
			}
		} else {
			Area staircaseArea = LocationMethods.getObjectBox(staircase);
			if (Players.getLocal().getHeight() == 0) {
				Walking.walk(Areas.WARRIORS_GUILD_BANK_AREA.getNearest());
			} else if (Players.getLocal().getHeight() == 1) {
				if (staircaseArea.contains(Players.getLocal().getLocation())) {
					staircase.interact("Climb-down");
				}
			} else if (Players.getLocal().getHeight() == 2) {
				if (!Areas.WARRIORS_GUILD_CYCLOPS_AREA.contains(player.getLocation())) {
					if (staircaseArea.contains(Players.getLocal().getLocation())) {
						staircase.interact("Climb-down");
					} else {
						Walking.walk(staircaseArea.getNearest());
					}
				} else {
					SceneObject cyclopsDoor = SceneEntities.getNearest(CYCLOPS_DOOR_ID);
					Walking.walk(cyclopsDoor);
					cyclopsDoor.click(true);
				}
			}
		}
	}
}
