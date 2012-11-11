package gcscripts.gcsuperheater.nodes;

import gcapi.constants.Animations;
import gcapi.constants.widgets.tabs.Magic;
import gcapi.methods.GenericMethods;
import gcscripts.gcsuperheater.GcSuperheater;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;

public class Superheater extends Node {

	Item item = null;

	@Override
	public boolean activate() {
		return Game.isLoggedIn() && Players.getLocal().getAnimation() == Animations.IDLE && !GcSuperheater.isBanking;
	}

	@Override
	public void execute() {

		item = Inventory.getItem(GcSuperheater.primaryOre[0]);

		if (item == null) {
			GcSuperheater.logger.log("Out of ore, banking.");
			GcSuperheater.isBanking = true;
			return;
		}
		if (!Tabs.MAGIC.isOpen()) {
			Tabs.MAGIC.open();
		}
		Widgets.get(Magic.PARENT).getChild(Magic.SUPERHEAT).click(true);
		GenericMethods.waitForCondition(Tabs.INVENTORY.isOpen(), 5000);
		item.getWidgetChild().click(true);
		++GcSuperheater.casts;
		GcSuperheater.gui.updateRows(GcSuperheater.getData());
		Task.sleep(1000);
	}
}
