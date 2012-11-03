package gcapi.listeners.interfacelistener;

import org.powerbot.concurrent.strategy.Strategy;

public class InterfaceCheckerStrategy extends Strategy {

	private boolean init = false;

	public void run() {
		if (!init) {
			InterfaceChecker listener = new InterfaceChecker();
			init = true;
		}
	}

	public boolean validate() {
		return !init;
	}

}
