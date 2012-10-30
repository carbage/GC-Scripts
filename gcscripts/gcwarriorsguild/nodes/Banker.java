package gcscripts.gcwarriorsguild.nodes;

import gcapi.constants.Areas;
import gcapi.constants.Equipment;
import gcapi.constants.Items;
import gcapi.constants.SceneObjects;
import gcapi.methods.LocationMethods;
import gcscripts.gcwarriorsguild.GcWarriorsGuild;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.interactive.Player;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class Banker extends Node {

	Player player = Players.getLocal();
	private int[] STAIRCASE_IDS = { 66795, 66796, 66797 };

	@Override
	public boolean activate() {
		return GcWarriorsGuild.isBanking || !GcWarriorsGuild.hasDefender;
	}

	@Override
	public void execute() {
		if (Areas.WARRIORS_GUILD_BANK_AREA.contains(player) && !Bank.isOpen()) {
			Bank.open();
			if (GcWarriorsGuild.isBanking && GcWarriorsGuild.hasDefender) {
				if (Inventory.contains(Equipment.DEFENDER_IDS)) {
					Inventory.getItem(Equipment.DEFENDER_IDS).getWidgetChild()
							.interact("Deposit-all");
				}
				Item food = Bank.getItem(Items.FOOD_IDS);
				if (food != null) {
					food.getWidgetChild().interact("Withdraw-all");
				} else {
					GcWarriorsGuild.logger.log("No food in bank, stopping.");
					GcWarriorsGuild.problemFound = true;
				}

			} else if (!GcWarriorsGuild.hasDefender) {
				Item defender = Bank.getItem(GcWarriorsGuild.defenderId - 1);
				if (defender != null) {
					defender.getWidgetChild().click(true);
				} else {
					GcWarriorsGuild.logger
							.log("No defenders in bank, stopping.");
					GcWarriorsGuild.problemFound = true;
				}
			}
		} else {
			SceneObject staircase = SceneEntities.getNearest(STAIRCASE_IDS);
			Area staircaseArea = LocationMethods.getObjectBox(staircase);
			if (player.getHeight() == 0) {
				Walking.walk(Areas.WARRIORS_GUILD_BANK_AREA.getNearest());
			} else if (player.getHeight() == 1) {
				if (staircaseArea.contains(player.getLocation())) {
					staircase.interact("Climb-down");
				}
			} else if (player.getHeight() == 2 && !Areas.WARRIORS_GUILD_CYCLOPS_AREA.contains(player.getLocation())) {
				if (staircaseArea.contains(player.getLocation())) {
					staircase.interact("Climb-down");
				} else {
					Walking.walk(staircaseArea.getNearest());
				}
			}
		}
	}
}
