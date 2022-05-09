package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Traffic Simulator");
		_ctrl = ctrl;
		initGUI();
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		
		mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(_ctrl), BorderLayout.PAGE_END);
		
		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);
		
		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);
		
		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);
		
		// Tables
		JPanel eventsView = createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
		eventsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(eventsView);
		
		JPanel vehiclesTable = createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Vehicles");
		vehiclesTable.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(vehiclesTable);
		
		JPanel roadsTable = createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Roads");
		roadsTable.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(roadsTable);
		
		JPanel junctionsTable = createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Junctions");
		junctionsTable.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(junctionsTable);
		
		// Maps
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapView);
		
		JPanel mapRoads = createViewPanel(new MapByRoadComponent(_ctrl), "Map By Road");
		mapRoads.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapRoads);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel(new BorderLayout());
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		p.setBorder(BorderFactory.createTitledBorder(border, title));
		p.add(new JScrollPane(c));
		return p;
	}
}
