package gcapi.listeners.interfacelistener;

import java.util.ArrayList;
import java.util.List;


public class InterfaceHandler {
	List<InterfaceListener> listeners = new ArrayList<InterfaceListener>();
	private int index;

		public void addListener(InterfaceListener toAdd) {
			listeners.add(toAdd);
		}

		public void fireInterfaceOpenedEvent(int index) {
			for (InterfaceListener hl : listeners) { hl.onInterfaceOpenedEvent(index); }
		}

		public void fireInterfaceClosedEvent() {
			for (InterfaceListener hl : listeners) { hl.onInterfaceClosedEvent(); }
		}
}