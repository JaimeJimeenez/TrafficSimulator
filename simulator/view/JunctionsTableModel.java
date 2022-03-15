package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	
	private static final String[] columnNames = { "Id", "Green", "Queues" };
	
	private List<Junction> junctions;
	
	JunctionsTableModel(Controller ctrl) {
		junctions = new ArrayList<>();
		ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() { return junctions.size(); }

	@Override
	public String getColumnName(int i) { return columnNames[i]; }
	
	@Override
	public int getColumnCount() { return columnNames.length; }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
			case 0:
				return junctions.get(rowIndex).getId();
			case 1:
				return junctions.get(rowIndex).getGreenLightIndex();
			case 2:
				return junctions.get(rowIndex).getInRoads();
		}
		
		return null;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.junctions = map.getJunctions();
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.junctions = new ArrayList<>();
		fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
