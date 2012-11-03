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
		for (InterfaceListener l : listeners) {
			l.onInterfaceOpenedEvent(index);
		}
	}

	public void fireInterfaceClosedEvent() {
		for (InterfaceListener l : listeners) {
			l.onInterfaceClosedEvent();
		}
	}

	private List _listeners = new ArrayList();

	public synchronized void addEventListener(InterfaceListener listener) {
		_listeners.add(listener);
	}

	public synchronized void removeEventListener(InterfaceListener listener) {
		_listeners.remove(listener);
	}
}