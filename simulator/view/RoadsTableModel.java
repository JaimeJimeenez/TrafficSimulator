package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;

	private static final String[] columnNames = { "Id", "Length", "Weather", "Max. Speed", "Speed Limit", "Total CO2", "CO2 Limit" };

	private List<Road> roads;
	
	RoadsTableModel(Controller ctrl) {
		roads = new ArrayList<>();
		ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() { return roads.size(); }

	@Override
	public String getColumnName(int i) { return columnNames[i]; }
	
	@Override
	public int getColumnCount() { return columnNames.length; }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0:
			return roads.get(rowIndex).getId();
		case 1:
			return roads.get(rowIndex).getLength();
		case 2:
			return roads.get(rowIndex).getWeather();
		case 3:
			return roads.get(rowIndex).getMaxSpeed();
		case 4:
			return roads.get(rowIndex).getSpeedLimit();
		case 5:
			return roads.get(rowIndex).getTotalCO2();
		case 6:
			return roads.get(rowIndex).getCO2Limit();
		}
		
		return null;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.roads = map.getRoads();
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.roads = new ArrayList<>();
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
