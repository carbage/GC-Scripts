package gcscripts.gcwarriorsguild.nodes;

import gcapi.constants.Items;
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
		if (Players.getLocal().getHpPercent() > 25) {
			if (!Inventory.contains(Items.FOOD_IDS)) {
				GcWarriorsGuild.isBanking = true;
			} else {
				Inventory.getItem(Items.FOOD_IDS).getWidgetChild().click(true);
			}
		} else if (Inventory.isFull() && Inventory.contains(Items.FOOD_IDS)) {
			Inventory.getItem(Items.FOOD_IDS).getWidgetChild().click(true);
		}
	}

}
