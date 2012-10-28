package gcapi.methods;

import java.awt.event.KeyEvent;

import org.powerbot.game.api.methods.input.Keyboard;

public final class InputMethods {
	
	public static final void sendKeys(String string) { //Sends keys faster than Keyboard.sendText(String), basically instantly
		for (final char c : string.toCharArray()) { Keyboard.sendKey(c); }
		Keyboard.sendKey((char) KeyEvent.VK_ENTER);
	}
	
}
