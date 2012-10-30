package gcscripts.gcwarriorsguild.nodes;

import gcscripts.gcwarriorsguild.GcWarriorsGuild;

import org.powerbot.core.script.job.state.Node;

public class Banker extends Node {

	@Override
	public boolean activate() {
		return GcWarriorsGuild.isBanking;
	}

	@Override
	public void execute() {
		
	}

}
