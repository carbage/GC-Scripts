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
	private JLabel runtimeTimeLabel = new JLabel() {
		@Override
		protected void paintComponent(Graphics g) {
			runtimeTimeLabel.setText(getTimeRunning());
			System.out.println(getTimeRunning());
			super.paintComponent(g);
		}
	};

	private JTabbedPane tabArea = new JTabbedPane();
	private JScrollPane tableScrollPane;
	private AbstractTableModel model;
	private JTable table;

	private String[] columns = new String[] { "Data", "Value" };
	private Object[][] tableData;

	private long startTime;
	private Timer runTime;

	private int PADDING = 5;

	@SuppressWarnings("serial")
	public Gui(String scriptName, Logger logger, Object[][] data) {

		startTime = System.currentTimeMillis();

		logger.log("Initialised GUI.");

		runTime = new Timer(0);

		setTitle("GC GUI");
		setPreferredSize(new Dimension(400, 350));
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

		model = new AbstractTableModel() {
			public String getColumnName(int col) {
				return columns[col].toString();
			}

			public int getRowCount() {
				return tableData.length;
			}

			public int getColumnCount() {
				return columns.length;
			}

			public Object getValueAt(int row, int col) {
				return tableData[row][col];
			}

			public boolean isCellEditable(int row, int col) {
				return true;
			}

			public void setValueAt(Object value, int row, int col) {
				tableData[row][col] = value;
				fireTableCellUpdated(row, col);
			}
		};

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

	public void updateRows(Object[][] data) {
		tableData = data;
		table.revalidate();
		model.fireTableDataChanged();
	}

	String getTimeRunning() {
		return runTime.toElapsedString();
	}

}
