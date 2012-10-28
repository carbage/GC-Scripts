package gcscripts.gcpowerminer;

import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.util.Random;

@Manifest(authors = { "Fuz" }, name = "GC Power Miner", description = "Mines iron from the closest nodes.", version = 1.0)
public class GcPowerMiner extends ActiveScript {

	@Override
	public int loop() {
		// TODO Auto-generated method stub
		return Random.nextInt(10, 50);
	}

}
