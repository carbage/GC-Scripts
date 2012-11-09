package gcscripts.gcalchemy.nodes;

import gcapi.constants.widgets.tabs.Magic;
import gcapi.methods.GenericMethods;
import gcscripts.gcalchemy.GcAlchemy;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;

public class Alchemiser extends Node {

	@Override
	public boolean activate() {
		return Game.isLoggedIn() && Players.getLocal().isIdle();
	}

	@Override
	public void execute() {
		if (GcAlchemy.itemToAlch.getStackSize() < 1) {
			GcAlchemy.logger.log("Out of items to alch, stopping.");
			GcAlchemy.problemFound = true;
			return;
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
		GcAlchemy.itemToAlch.getWidgetChild().click(true);
		GenericMethods.waitForCondition(!Players.getLocal().isIdle(), 10000);

	}

}
