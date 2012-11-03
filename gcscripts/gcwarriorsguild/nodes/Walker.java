package gcscripts.gcwarriorsguild.nodes;

import gcapi.constants.Areas;
import gcapi.constants.interfaces.Dialogues;
import gcapi.constants.interfaces.Windows;
import gcapi.methods.GenericMethods;
import gcapi.methods.InputMethods;
import gcapi.methods.LocationMethods;
import gcscripts.gcwarriorsguild.GcWarriorsGuild;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.interactive.Player;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

public class Walker extends Node {

	Player player = Players.getLocal();

	private int[] STAIRCASE_IDS = { 66795, 66796, 66797 };

	private final static int HEAVY_DOOR = 15658;
	private final static int SHOTPUT_DOOR = 66758;
	private final static int[] CYCLOPS_DOOR = { 66599, 66601 };

	@Override
	public boolean activate() {
		if (Players.getLocal().isIdle() && Game.isLoggedIn() && !GcWarriorsGuild.isBanking) {
			return true;
		}
		return false;
	}

	@Override
	public void execute() {

		SceneObject staircase = SceneEntities.getNearest(STAIRCASE_IDS);
		if (staircase == null) {
			GcWarriorsGuild.logger.log("No staircases nearby, stopping script.");
			GcWarriorsGuild.problemFound = true;
		}

		if (GcWarriorsGuild.collectingTokens) {
			switch (GcWarriorsGuild.getFloor()) {
				case GROUND:
					LocationMethods.walkToObject(staircase);
					Camera.turnTo(staircase);
					GenericMethods.waitForCondition(staircase.isOnScreen(), 10000);
					if (staircase.isOnScreen()) {
						staircase.interact("Climb-up");
					}
					break;

				case MIDDLE:

					break;

				case TOP:
					if (Areas.WARRIORS_GUILD_CYCLOPS_AREA.contains(Players.getLocal())) {
						SceneObject door = SceneEntities.getNearest(CYCLOPS_DOOR);
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
			switch (GcWarriorsGuild.getFloor()) {
				case GROUND:
					LocationMethods.walkToObject(staircase);
					Camera.turnTo(staircase);
					GenericMethods.waitForCondition(staircase.isOnScreen(), 10000);
					if (staircase.isOnScreen()) {
						staircase.interact("Climb-up");
					}
					break;

				case MIDDLE:
					LocationMethods.walkToObject(staircase);
					Camera.turnTo(staircase);
					GenericMethods.waitForCondition(staircase.isOnScreen(), 10000);
					if (staircase.isOnScreen()) {
						staircase.interact("Climb-up");
					}
					break;

				case TOP:
					if (!Areas.WARRIORS_GUILD_CYCLOPS_AREA.contains(Players.getLocal())) {
						SceneObject door = SceneEntities.getNearest(CYCLOPS_DOOR);
						LocationMethods.walkToObject(door);
						Camera.turnTo(door);
						GenericMethods.waitForCondition(door.isOnScreen(), 10000);
						if (door.isOnScreen()) {
							door.interact("Open");
							GenericMethods.waitForCondition(Widgets.get(Dialogues.DIALOGUE_BOX_PARENT).validate(), 5000);
							InputMethods.sendKeys(" ");
							GenericMethods.waitForCondition(Widgets.get(Dialogues.DIALOGUE_BOX_PARENT).validate(), 5000);
							InputMethods.sendKeys(" ");
							GenericMethods.waitForCondition(Widgets.get(Dialogues.DIALOGUE_BOX_PARENT).validate(), 5000);
							InputMethods.sendKeys(" ");
							GenericMethods.waitForCondition(Widgets.get(Windows.WARRIORS_GUILD_CYCLOPES_PARENT).validate(), 5000);
							WidgetChild widget = Widgets.get(Windows.WARRIORS_GUILD_CYCLOPES_PARENT).getChild(Windows.WARRIORS_GUILD_CYCLOPES_SINGLE_TYPE);
							if (widget.getTextureId() == Windows.WARRIORS_GUILD_CYCLOPES_SINGLE_TYPE_SELECTED_TEXTURE) {
								widget.click(true);
							}
							widget = Widgets.get(Windows.WARRIORS_GUILD_CYCLOPES_PARENT).getChild(Windows.WARRIORS_GUILD_CYCLOPES_STRENGTH_TOKEN);
							if (widget.getTextureId() == Windows.WARRIORS_GUILD_CYCLOPES_STRENGTH_TOKEN_SELECTED_TEXTURE) {
								widget.click(true);
							}
							widget = Widgets.get(Windows.WARRIORS_GUILD_CYCLOPES_PARENT).getChild(Windows.WARRIORS_GUILD_CYCLOPES_ACCEPT);
							widget.click(true);
						}
					}
					break;

				default:
					GcWarriorsGuild.problemFound = true;
			}
		}
	}
}
