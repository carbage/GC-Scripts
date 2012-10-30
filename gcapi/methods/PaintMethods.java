package gcapi.methods;

import java.awt.Graphics;
import java.awt.Point;

import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Tile;

public final class PaintMethods {

    public static void drawPath(Graphics g, Tile[] path) {
	Point last = tileToMap(path[0]);
	for (Tile curr : path) {
	    Point onmap = tileToMap(curr);
	    g.drawLine(last.x, last.y, onmap.x, onmap.y);
	    last = onmap;
	}
    }

    public static Point tileToMap(Tile tile) {
	double minimapAngle = -1 * Math.toRadians(Camera.getPitch());
	int x = (tile.getX() - Players.getLocal().getLocation().getX()) * 4 - 2;
	int y = (Players.getLocal().getLocation().getY() - tile.getY()) * 4 - 2;
	return new Point((int) Math.round(x * Math.cos(minimapAngle) + y
		* Math.sin(minimapAngle) + 628), (int) Math.round(y
		* Math.cos(minimapAngle) - x * Math.sin(minimapAngle) + 87));

    }

}
