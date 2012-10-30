package gcscripts.gcwarriorsguild.nodes;

import gcapi.constants.Areas;
import gcapi.constants.interfaces.Dialogues;
import gcapi.methods.GenericMethods;
import gcapi.methods.InputMethods;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.interactive.Player;

public class ThrowShotput extends Node {

    private final static int SHOTPUT_PILE_ID = 15665;

    @Override
    public boolean activate() {
	return Areas.WARRIORS_GUILD_SHOTPUT_AREA.contains(Players.getLocal().getLocation());
    }

    @Override
    public void execute() {
	SceneEntities.getNearest(SHOTPUT_PILE_ID).click(true);
	GenericMethods.waitForCondition(Widgets.get(Dialogues.THROW_SHOT_DIALOGUE_BOX).validate(), 10000);
	if(Widgets.get(Dialogues.THROW_SHOT_DIALOGUE_BOX).validate()) {
	    InputMethods.sendKeys(Integer.toString(3));
	}
    }

}
