package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
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
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		setBorder(BorderFactory.createBevelBorder(1));
		
		// Time
		JPanel timePanel = new JPanel();
		add(timePanel);
		
		timeLabel = new JLabel("Time: 0");
		timePanel.add(timeLabel);
		
		// Event:
		JPanel eventPanel = new JPanel();
		add(eventPanel);
		
		eventLabel = new JLabel("Welcome!");
		eventPanel.add(eventLabel);
		
		setVisible(true);
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		timeLabel.setText("Time: " + time);
		eventLabel.setText("");
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
		timeLabel.setText("Time: " +  time);
		eventLabel.setText("Welcome!");
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onError(String err) {

	}

}