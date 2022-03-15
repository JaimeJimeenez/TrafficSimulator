package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	
	private static final String[] columnNames = { "Id", "Location", "Itinerary", "CO2 Class", "Speed", "Total CO2", "Distance" };
	
	private List<Vehicle> vehicles;
	
	VehiclesTableModel(Controller ctrl) {
		vehicles = new ArrayList<>();
		ctrl.addObserver(this);
	}
	
	@Override
	public int getRowCount() { return vehicles.size(); }
	
	@Override
	public String getColumnName(int i) { return columnNames[i]; } 
	
	@Override
	public int getColumnCount() { return columnNames.length; }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0:
			return vehicles.get(rowIndex).getId();
		case 1:
			return vehicles.get(rowIndex).getLocation();
		case 2:
			return vehicles.get(rowIndex).getItinerary();
		case 3:
			return vehicles.get(rowIndex).getContClass();
		case 4:
			return vehicles.get(rowIndex).getMaxSpeed();
		case 5:
			return vehicles.get(rowIndex).getTotalCO2();
		case 6:
			return vehicles.get(rowIndex).getTotalDistance();
		}
		
		return null;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		vehicles = map.getVehicles();
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		vehicles = map.getVehicles();
		fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		vehicles = map.getVehicles();
		fireTableDataChanged();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

	
}
