package gcscripts.gcalchemy.nodes;

import gcapi.constants.Animations;
import gcapi.constants.widgets.tabs.Magic;
import gcapi.methods.GenericMethods;
import gcscripts.gcalchemy.GcAlchemy;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;

public class Alchemiser extends Node {

	Item item = null;

	@Override
	public boolean activate() {
		return Game.isLoggedIn() && Players.getLocal().getAnimation() == Animations.IDLE;
	}

	@Override
	public void execute() {
		item = Inventory.getItem(GcAlchemy.itemToAlch.getId());
		if (item == null) {
			GcAlchemy.logger.log("Out of items to alch, stopping.");
			GcAlchemy.problemFound = true;
			return;
		} else {
			item = GcAlchemy.itemToAlch;
		}
		if (!Tabs.MAGIC.isOpen()) {
			Tabs.MAGIC.open();
		}
		if (GcAlchemy.highAlch) {
			Widgets.get(Magic.PARENT).getChild(Magic.HIGH_ALCH).click(true);
		} else {
			Widgets.get(Magic.PARENT).getChild(Magic.LOW_ALCH).click(true);
		}
		GenericMethods.waitForCondition(Tabs.INVENTORY.isOpen(), 10000);
		item.getWidgetChild().click(true);
		//if (GcAlchemy.highAlch) {
		//	GenericMethods.waitForCondition(Players.getLocal().getAnimation() == Animations.HIGH_ALCH, 5000);
		//} else {
		//	GenericMethods.waitForCondition(Players.getLocal().getAnimation() == Animations.LOW_ALCH, 5000);
		//}
		++GcAlchemy.casts;
		GcAlchemy.gui.updateRows(GcAlchemy.getData());
		Task.sleep(1000);
	}
}
