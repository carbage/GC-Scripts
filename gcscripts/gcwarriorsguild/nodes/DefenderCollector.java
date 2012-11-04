package gcscripts.gcwarriorsguild.nodes;

import gcapi.constants.Equipment;
import gcapi.constants.Items;
import gcscripts.gcwarriorsguild.GcWarriorsGuild;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.GroundItem;

public class DefenderCollector extends Node {

	private int defenderCount;

	@Override
	public boolean activate() {
		return true;
	}

	@Override
	public void execute() {
		GroundItem defender = GroundItems.getNearest(Equipment.DEFENDER_IDS);
		if (defender != null) {
			GcWarriorsGuild.logger.log("Found defender on floor");
			if (Inventory.contains(Items.FOOD_IDS) && Inventory.isFull()) {
				Inventory.getItem(Items.FOOD_IDS).getWidgetChild().click(true);
				GcWarriorsGuild.logger.log("Inventory full, making space for defender");
			} else if (Inventory.isFull()) {
				GcWarriorsGuild.isBanking = true;
			}
			if (!defender.isOnScreen()) {
				Camera.turnTo(defender);
			}
			if (defender.interact("Take", "defender")) {
				GcWarriorsGuild.defendersCollected++;
				GcWarriorsGuild.logger.log("Defender collected");
			} else {
				GcWarriorsGuild.logger.log("Failed to pick up defender.");
			}
		}
	}

	public int getDefenderCount() {
		return defenderCount;
	}
}
