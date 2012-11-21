package gcapi.methods;

import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;

public final class LocationMethods {

	public static void waitForPlayerToEnterArea(long millis, Area area) { // Waits
		// for
		// widget
		// to
		// disappear
		Timer t = new Timer(millis);
		while (t.isRunning() && !area.contains(Players.getLocal())) {
		}
	}

	public static Area getNpcBox(NPC npc) {
		return new Area(new Tile(npc.getLocation().getX() + 1, npc.getLocation().getY() + 1, 0), new Tile(npc.getLocation().getX() - 1, npc.getLocation().getY() - 1, 0));
	}

	public static Area getObjectBox(SceneObject object) {
		return new Area(new Tile(object.getLocation().getX() + 1, object.getLocation().getY() + 1, 0), new Tile(object.getLocation().getX() - 1, object.getLocation().getY() - 1, 0));
	}

	public static void walkToObject(SceneObject object) {
		if(object != null) {
			Tile objectTile = object.getLocation();
			Camera.turnTo(object);
			Walking.walk(LocationMethods.getObjectBox(object).getNearest());
			if (objectTile.isOnMap() && !object.isOnScreen()) {
				objectTile.clickOnMap();
			} else {
				if (objectTile.canReach()) Walking.findPath(objectTile).traverse();
			}
		}		
	}

	public static void walkToTile(Tile tile) {
		Camera.turnTo(tile);
		if (tile.isOnMap() && !tile.isOnScreen()) {
			tile.clickOnMap();
		} else {
			if (tile.canReach()) Walking.findPath(tile).traverse();
		}
	}

	public static boolean isInArea(Area area) {
		if (area.contains(Players.getLocal())) return true;
		return false;
	}

}
