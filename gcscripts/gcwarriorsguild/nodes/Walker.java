package gcscripts.gcwarriorsguild.nodes;

import gcapi.constants.Areas;
import gcscripts.gcwarriorsguild.GcWarriorsGuild;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.Players;

public class Walker extends Node {

	@Override
	public boolean activate() {
		return Game.isLoggedIn() && !GcWarriorsGuild.isBanking
								&& (Areas.WARRIORS_GUILD_FIRST_FLOOR.contains(Players.getLocal())
								|| Areas.WARRIORS_GUILD_SECOND_FLOOR.contains(Players.getLocal()) 
								|| Areas.WARRIORS_GUILD_THIRD_FLOOR.contains(Players.getLocal()));
	}

	@Override
	public void execute() {
		
	}

}
