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

	private static final int PADDING = 10;

	private Logger logger;

	public OptionsGui() {
		this.logger = logger;

		this.logger.log("Initialised selection GUI");

		setTitle("GC Warriors' Guild - Select action");
		setPreferredSize(new Dimension(200, 100));
		setResizable(false);
		setLayout(new FlowLayout(FlowLayout.LEADING, PADDING, PADDING));

		JPanel contentPane = new JPanel();


		JButton startButton = new JButton("Start script");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispatchEvent(new WindowEvent(OptionsGui.this, WindowEvent.WINDOW_CLOSING));
			}
		});

		setContentPane(contentPane);
		contentPane.add(startButton);
		pack();
		setVisible(true);

	}
}
