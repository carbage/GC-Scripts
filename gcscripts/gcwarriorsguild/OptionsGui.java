package gcscripts.gcwarriorsguild;

import gcapi.utils.Logger;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class OptionsGui extends JFrame {

	private JComboBox optionsBox;

	private JComboBox defendersBox;

	private JComboBox foodBox;

	private static final int PADDING = 10;

	private Logger logger;

	private static final String[] OPTIONS = new String[] { "Collect tokens",
			"Collect defenders" };

	private static final String[] DEFENDERS = new String[] { "Bronze", "Iron",
			"Steel", "Black", "Mithril", "Adamant", "Rune", "Dragon" };

	private static final String[] FOOD = new String[] { "Lobster", "Shark",
			"Manta ray", "Monkfish", "Rocktail" };

	public OptionsGui(Logger logger) {
		this.logger = logger;

		this.logger.log("Initialised selection GUI");

		setTitle("GC Warriors' Guild - Select action");
		setPreferredSize(new Dimension(200, 100));
		setResizable(false);
		setLayout(new FlowLayout(FlowLayout.LEADING, PADDING, PADDING));

		JPanel contentPane = new JPanel();

		optionsBox = new JComboBox(getOptions());
		defendersBox = new JComboBox(getDefenders());
		foodBox = new JComboBox(getFood());

		optionsBox.setSelectedIndex(0);
		optionsBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (optionsBox.getSelectedIndex() == 1) {
					defendersBox.setEnabled(true);
				} else {
					defendersBox.setEnabled(false);
				}
			}
		});

		JButton startButton = new JButton("Start script");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispatchEvent(new WindowEvent(OptionsGui.this, WindowEvent.WINDOW_CLOSING));
			}
		});

		defendersBox.setEnabled(false);

		setContentPane(contentPane);

		contentPane.add(optionsBox);
		contentPane.add(defendersBox);
		contentPane.add(foodBox);
		contentPane.add(startButton);
		pack();
		setVisible(true);

	}

	public JComboBox getOptionsBox() {
		return optionsBox;
	}

	public JComboBox getDefendersBox() {
		return defendersBox;
	}
	
	public JComboBox getFoodBox() {
		return foodBox;
	}

	public String[] getOptions() {
		return OPTIONS;
	}

	public String[] getDefenders() {
		return DEFENDERS;
	}

	public String[] getFood() {
		return FOOD;
	}
}
