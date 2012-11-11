package gcscripts.gcwarriorsguild.nodes;

import gcapi.constants.items.Food;
import gcscripts.gcwarriorsguild.GcWarriorsGuild;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;

public class CheckForFood extends Node {

	@Override
	public boolean activate() {
		return true;
	}

	@Override
	public void execute() {
		if (Players.getLocal().getHpPercent() < 45) {
			if (!Inventory.contains(Food.FOOD_IDS)) {
				GcWarriorsGuild.isBanking = true;
			} else {
				Inventory.getItem(Food.FOOD_IDS).getWidgetChild().click(true);
			}
		} else if (Inventory.isFull() && Inventory.contains(Food.FOOD_IDS)) {
			Inventory.getItem(Food.FOOD_IDS).getWidgetChild().click(true);
		}
	}

}
