package gcapi.utils.comparators;

import java.util.Comparator;

import org.powerbot.game.api.wrappers.interactive.NPC;

public class ClosestNpc implements Comparator<NPC> {
	public int compare(NPC n1, NPC n2) {
		return (int) n1.getLocation().distance(n2);
	}
}