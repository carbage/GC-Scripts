package gcscripts.gcwarriorsguild;

import gcapi.constants.Equipment;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.powerbot.core.script.ActiveScript;

public class SelectorGui extends JFrame {

    public static ActiveScript parent;

    public static Object optionsBox;

    public static Object defendersBox;

    private int PADDING = 10;

    private String[] options = new String[] { "Collect tokens",
	    "Collect defenders" };
    static String[] defenders = new String[] { "Bronze", "Iron", "Steel",
	    "Black", "Mithril", "Adamant", "Rune", "Dragon" };

    public SelectorGui(ActiveScript parent) {
	this.parent = parent;

	setTitle("GC Warriors' Guild - Select action");
	setPreferredSize(new Dimension(200, 100));
	setResizable(false);
	setLayout(new FlowLayout(FlowLayout.LEADING, PADDING, PADDING));
	addWindowListener(new WindowEventHandler());
	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	JPanel contentPane = new JPanel();

	final JComboBox optionsBox = new JComboBox(options);
	final JComboBox defendersBox = new JComboBox(defenders);

	optionsBox.setSelectedIndex(0);
	optionsBox.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (optionsBox.getSelectedIndex() == 1) {
		    defendersBox.setEnabled(true);
		}
	    }
	});

	JButton startButton = new JButton("Start script");
	startButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (optionsBox.getSelectedIndex() == 1) {
		    GcWarriorsGuild.collectingTokens = false;
		    GcWarriorsGuild.defenderId = Equipment.DEFENDER_IDS[defendersBox
			    .getSelectedIndex()];
		    GcWarriorsGuild.defenderType = defenders[defendersBox
			    .getSelectedIndex()];
		    GcWarriorsGuild.guiClosed = true;
		    dispose();
		} else {
		    GcWarriorsGuild.guiClosed = true;
		    dispose();
		}
	    }
	});

	defendersBox.setEnabled(false);

	setContentPane(contentPane);

	contentPane.add(optionsBox);
	contentPane.add(defendersBox);
	contentPane.add(startButton);
	pack();
	setVisible(true);

    }
}

class WindowEventHandler extends WindowAdapter {
    public void windowClosing(WindowEvent evt) {
	if (((JComboBox) SelectorGui.optionsBox).getSelectedIndex() == 1) {
	    GcWarriorsGuild.collectingTokens = false;
	    GcWarriorsGuild.defenderId = Equipment.DEFENDER_IDS[((JComboBox) SelectorGui.defendersBox)
		    .getSelectedIndex()];
	    GcWarriorsGuild.defenderType = SelectorGui.defenders[((JComboBox) SelectorGui.defendersBox)
		    .getSelectedIndex()];
	    GcWarriorsGuild.guiClosed = true;
	} else {
	    GcWarriorsGuild.guiClosed = true;
	}
    }
}
