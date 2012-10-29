package gcapi.gui;

import gcapi.utils.Logger;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.powerbot.game.api.util.Timer;

public class Gui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane = new JPanel();

	private JLabel scriptLabel = new JLabel();
	private JLabel scriptNameLabel = new JLabel();

	private JLabel runtimeLabel = new JLabel();
	private JLabel runtimeTimeLabel = new JLabel();

	private JTabbedPane tabArea = new JTabbedPane();
	private JScrollPane tableScrollPane;
	private DefaultTableModel model;
	private JTable table;

	private String[] columns = new String[] { "Data", "Value" };
	private Object[][] tableData;

	private long startTime;
	private Timer runTime;

	private int PADDING = 5;

	private Logger logger;

	@SuppressWarnings("serial")
	public Gui(String scriptName, Logger logger, Object[][] data) {

		this.logger = logger;

		startTime = System.currentTimeMillis();

		logger.log("Initialised GUI.");

		runTime = new Timer(0);

		setTitle("GC GUI");
		setPreferredSize(new Dimension(350, 300));
		setResizable(false);
		setLayout(new FlowLayout(FlowLayout.LEADING, PADDING, PADDING));

		scriptLabel.setText("Script Name:");
		scriptLabel.setLabelFor(scriptNameLabel);
		scriptLabel.setPreferredSize(new Dimension(120, 20));
		scriptNameLabel.setText(scriptName);
		scriptNameLabel.setPreferredSize(new Dimension(120, 20));

		runtimeLabel.setText("Run time:");
		runtimeLabel.setLabelFor(runtimeTimeLabel);
		runtimeLabel.setPreferredSize(new Dimension(120, 20));
		runtimeTimeLabel.setPreferredSize(new Dimension(120, 20));
		new Thread() {
			public void run() {
				while (true) {
					runtimeTimeLabel.setText(getTimeRunning());
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}.start();

		model = new DefaultTableModel(data, columns);

		tableData = data;
		table = new JTable(model) {
			public boolean isCellEditable(int nRow, int nCol) {
				return false;
			}
		};

		tableScrollPane = new JScrollPane(table);
		tabArea.addTab("Script Info", tableScrollPane);
		tableScrollPane.setPreferredSize(new Dimension(350, 200));

		setContentPane(contentPane);

		contentPane.setBorder(BorderFactory.createTitledBorder("Script Info"));
		contentPane.add(scriptLabel);
		contentPane.add(scriptNameLabel);

		contentPane.add(runtimeLabel);
		contentPane.add(runtimeTimeLabel);

		contentPane.add(tabArea);

		pack();
		setVisible(true);

	}

	public void updateRows(final Object[][] data) {
		// if(logger != null) logger.log("Updating table data");
		tableData = data;	// tableData is the field which was used in the
							// table's initial construction
		model = new DefaultTableModel(data, columns);
		model.setRowCount(0);
		for (int i = 0; i < data.length; i++) {
			model.addRow(data[i]);
		}
		model.fireTableRowsUpdated(0, model.getRowCount());
		table.setModel(model);
	}

	public Object[][] getTableData() {
		return tableData;

	}

	String getTimeRunning() {
		return runTime.toElapsedString();
	}

}
