package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;

	private static final String columnNames[] = { "Time", "Desc."};
	private List<Event> events;
	
	EventsTableModel(Controller ctrl) {
		events = new ArrayList<>();
		ctrl.addObserver(this);
	}
	
	public String getColumnName(int i) { return columnNames[i]; }
	
	@Override
	public int getRowCount() { return events.size(); }

	@Override
	public int getColumnCount() { return columnNames.length; }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		switch(columnIndex) {
		case 0:
			return events.get(rowIndex).getTime();
		case 1:
			return events.get(rowIndex).toString();
		}
		
		return null;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.events = events;
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		if (!this.events.contains(e))
			this.events.add(e);
		fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.events = new ArrayList<>();
		fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.events = events;
		fireTableDataChanged();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	
}
