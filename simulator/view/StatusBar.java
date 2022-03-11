package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	
	private JLabel timeLabel;
	private JLabel eventLabel;
	

	StatusBar(Controller ctrl) {
		initGui();
		ctrl.addObserver(this);
	}
	
	private void initGui() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		add(mainPanel);
		
		// Time
		JPanel timePanel = new JPanel();
		timePanel.setPreferredSize(new Dimension(80, 20));
		mainPanel.add(timePanel);
		
		timeLabel = new JLabel("Time: 0");
		timePanel.add(timeLabel);
		
		// Events
		JPanel eventsPanel = new JPanel();
		mainPanel.add(eventsPanel);
		
		eventLabel = new JLabel("Welcome!");
		eventsPanel.add(eventLabel);
		
		
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		timeLabel.setText("Time: " + time);
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		eventLabel.setText("Event added: " + e.toString());
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		eventLabel.setText("Welcome!");
		timeLabel.setText("Time:" + time);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onError(String err) {

	}

}
