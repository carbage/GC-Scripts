package gcscripts.gcwarriorsguild;

import gcapi.constants.Equipment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import org.powerbot.core.script.ActiveScript;

public class SelectorGui extends JFrame {

	public static ActiveScript parent;

	public SelectorGui(ActiveScript parent) {
		this.parent = parent;
		
		setTitle("GC Warriors' Guild - Select action");
		
		final JComboBox options = new JComboBox(new String[] {"Collect tokens", "Collect defenders"});
		final JComboBox defenders = new JComboBox(new String[] {"Bronze", "Iron", "Steel", "Black", "Mithril", "Adamant", "Rune", "Dragon"});
		
		options.setSelectedIndex(0);
		options.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        if(options.getSelectedIndex() == 1) {
		        	defenders.setEnabled(true);
		        }
		    }
		});
		
		JButton startButton = new JButton("Start script");
		startButton.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        if(options.getSelectedIndex() == 1) {
		        	GcWarriorsGuild.collectingTokens = false;
		        	GcWarriorsGuild.defenderId = Equipment.DEFENDER_IDS[defenders.getSelectedIndex()];
		        	GcWarriorsGuild.guiClosed = true;
		        } else {
		        	GcWarriorsGuild.guiClosed = true;
		        }
		    }
		});
		
		
		defenders.setEnabled(false);
		
	}
}

class WindowEventHandler extends WindowAdapter {
	public void windowClosing(WindowEvent evt) {
		if (SelectorGui.parent != null)
			SelectorGui.parent.shutdown();
	}
}
