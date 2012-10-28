/*
 * This should work, but reflection is disallowed in Powerbot/RSBot.
 * FUCK YOU TIMER!
 */

package gcapi.listeners.interfacelistener;

import gcapi.constants.interfaces.Windows;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.util.Time;


public class InterfaceChecker extends Strategy implements InterfaceListener {
	
	private ArrayList<Integer> interfaces = null;
	
	private boolean fieldsAdded = false;

	private InterfaceHandler handler;

	private boolean busy = false;
	
	private int currentInterface = (Integer) null;
	
	public InterfaceChecker() {
		System.out.println("Started Interface Checker...");

        handler = new InterfaceHandler();

        handler.addListener(this);
        
		for(Field field : Windows.class.getFields()) {
			System.out.println("Found interface constant:" + field.getName().toString());
			if(field.getName().toString().contains("PARENT")) {
				try {
					interfaces.add(field.getInt(field));
					System.out.println("Added interface: " + field.getName() + " with ID: " + field.getInt(field));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		
		fieldsAdded = true;
		
	}
	
	private int currentInterfaceId() {
		// TODO Auto-generated method stub
		for(int i = 0; i < interfaces.size(); i++) {
			if(interfaces.get(i) != null) {
				if(Widgets.get(interfaces.get(i)).validate()) {
					//Interface opened event
					if(handler != null) {
						return interfaces.get(i);
					}
				}
			}
		}
		return -1;
	}
	
	private boolean interfaceOpen() {
		return currentInterface == -1 && currentInterfaceId() != -1;
	}

	@Override
	public void onInterfaceOpenedEvent(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInterfaceClosedEvent() {
		// TODO Auto-generated method stub
		
	}

	public void run() {
		if(fieldsAdded) {
			if(!busy ) {
				if(interfaceOpen()) {
					currentInterface = currentInterfaceId();
					handler.fireInterfaceOpenedEvent(currentInterfaceId());
					busy = true;
				}
			} else {
				if(!interfaceOpen()) {
					Time.sleep(1000);
					if(currentInterfaceId() == -1) {
						handler.fireInterfaceClosedEvent();
					}
				}
			}
		}
		Time.sleep(250);
	}
	
	public boolean validate() {
		return true;
	}
}
