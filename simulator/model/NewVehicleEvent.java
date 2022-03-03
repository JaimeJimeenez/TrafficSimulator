package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {

	String id;
	int maxSpeed, contClass;
	List<String> itinerary;
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = itinerary;
	}

	@Override
	void execute(RoadMap map) {
		List<Junction> junctions = new ArrayList<>();
		
		for (String s : itinerary)
			junctions.add(map.getJunction(s));
		
		Vehicle newVehicle = new Vehicle(id, maxSpeed, contClass, junctions);
		
		map.addVehicle(newVehicle);
		newVehicle.moveToNextRoad();
	}

}
