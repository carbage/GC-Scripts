package gcapi.methods;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.Player;
import org.powerbot.game.api.wrappers.map.TilePath;

public final class PlayerMethods {

	public static Player findPlayer(final String playerName) {
		return Players.getNearest(new Filter<Player>() {
			@Override
			public boolean accept(Player found) {
				String fn = found.getName().replace("\u00A0", " ");
				return (fn.equals(playerName));
			}
		});
	}

	public static void waitForPlayerAnimation(Player target, int animationID) { // Waits for target to perform animation
		//Timer t = new Timer(millis);
		//while (t.isRunning() && target.getAnimation() != animationID) {}
		while (target.getAnimation() != animationID) {
		}
	}

	public static void waitForPlayerToEnterCombat() {
		while (!Players.getLocal().isInCombat()) {
		}
	}

	public static void walkPath(final Tile[] tiles) {
		TilePath path = Walking.newTilePath(tiles);
		path.randomize(2, 2);
		while (Players.getLocal().getLocation() != path.getEnd() && Calculations.distanceTo(path.getNext()) < 10) {
			path.traverse();
		}
	}

	public static void walkPathReverse(final Tile[] tiles) {
		TilePath path = Walking.newTilePath(tiles);
		path.randomize(2, 2);
		path.reverse();
		while (!Players.getLocal().isMoving() && Players.getLocal().getLocation() != path.getEnd()) {
			path.traverse();
		}
	}

	public static void walkPathAndReturn(final Tile[] tiles) {
		TilePath path = Walking.newTilePath(tiles);
		path.randomize(2, 2);
		while (!Players.getLocal().isMoving() && Players.getLocal().getLocation() != path.getEnd()) {
			path.traverse();
		}
		path.reverse();
		while (!Players.getLocal().isMoving() && Players.getLocal().getLocation() != path.getEnd()) {
			path.traverse();
		}
	}

}
