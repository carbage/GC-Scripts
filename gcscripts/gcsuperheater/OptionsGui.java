package gcscripts.gcsuperheater;

import gcapi.utils.Logger;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.powerbot.game.api.wrappers.node.Item;

public class OptionsGui extends JFrame {

	private static final int PADDING = 10;

	private Logger logger;

	JComboBox barList;
	
	private static final String[] BARS = {"Bronze", "Iron", "Silver", "Steel", "Gold", "Mithril", "Adamant", "Runite"};

	public OptionsGui(Item[] items, Logger logger) {
		this.logger = logger;

		this.logger.log("Initialised selection GUI");

		setTitle("Select item");
		setPreferredSize(new Dimension(200, 100));
		setResizable(false);
		setLayout(new FlowLayout(FlowLayout.LEADING, PADDING, PADDING));

		JPanel contentPane = new JPanel();
		barList = new JComboBox(BARS);

		JButton startButton = new JButton("Start script");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispatchEvent(new WindowEvent(OptionsGui.this, WindowEvent.WINDOW_CLOSING));
			}
		});

		setContentPane(contentPane);
		contentPane.add(barList);
		contentPane.add(startButton);
		pack();
		setVisible(true);

	}
}
