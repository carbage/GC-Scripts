package gcscripts.gcwarriorsguild.nodes;

import gcapi.constants.Areas;
import gcapi.constants.SceneObjects;
import gcapi.constants.interfaces.Dialogues;
import gcapi.methods.GenericMethods;
import gcapi.methods.InputMethods;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class ThrowShotput extends Node {

	@Override
	public boolean activate() {
		return Areas.WARRIORS_GUILD_SHOTPUT_ROOM.contains(Players.getLocal()) && Players.getLocal().getAnimation() == -1;
	}

	@Override
	public void execute() {
		SceneObject shotput = SceneEntities.getNearest(SceneObjects.SHOTPUT_PILE_ID);
		if (Widgets.get(Dialogues.THROW_SHOT_DIALOGUE_BOX).validate()) {
			InputMethods.sendKeys(Integer.toString(3));
			Time.sleep(5000);
			GenericMethods.waitForCondition(Players.getLocal().isIdle(), 10000);
		} else {
			if (shotput.isOnScreen()) {
				shotput.click(true);
				GenericMethods.waitForCondition(Widgets.get(Dialogues.THROW_SHOT_DIALOGUE_BOX).validate(), 3000);
			} else {
				Camera.turnTo(shotput);
			}
		}

	}

}
