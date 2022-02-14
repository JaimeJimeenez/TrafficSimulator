package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {

	private List<Junction> itinerary;
	private int maxSpeed;
	private int speed;
	private VehicleStatus status;
	private Road road;
	private int location;
	private int contClass;
	private int totalCont;
	private int totalDistance;
	private int index;
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		if (maxSpeed < 0 || contClass < 0 || contClass > 10  || itinerary.size() < 2)
			throw new IllegalArgumentException("Arguments not valid");
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		this.index = 0;
	}

	public int getLocation() { return location; }
	
	public int getSpeed() { return speed; }
	
	public int getMaxSpeed() { return maxSpeed; }
	
	public int getContClass() { return contClass; }
	
	public VehicleStatus getStatus() { return status; }
	
	public int getTotalCO2() { return totalCont; }
	
	public List<Junction> getItinerary() { return itinerary; }
	
	public Road getRoad() { return road; }
	
	void setSpeed(int s) {
		if (s < 0)
			throw new IllegalArgumentException("Argument not valid");
		speed = (maxSpeed > s) ? s : maxSpeed;
	}
	
	void setContaminationClass(int c) {
		if (c < 0 || c > 10)
			throw new IllegalArgumentException("Argument not valid");
		this.contClass = c;
	}
	
	@Override
	void advance(int time) {
		if (status == VehicleStatus.TRAVELING) {
			//Distance
			int aux = location;
			location = ((location + speed) > road.getLength()) ? road.getLength() : location + speed;

			//Contamination produced
			int contProduced = contClass * (aux - location);
			totalCont += contProduced;
			road.addContamination(totalCont);

			//Vehicle arrived to the junction
			if (location >= road.getLength()) {
				status = VehicleStatus.WAITING;
				itinerary.get(0).enter(this); 
			}
		}
	}

	public void moveToNextRoad() {
		if (status == VehicleStatus.PENDING) 
			itinerary.get(0).enter(this);
		else {
			getRoad().exit(this);
			if (index != itinerary.size()) {
				itinerary.get(index).enter(this);
				index++;
			}
			else
				status = VehicleStatus.ARRIVED;
		}
	}

	@Override
	public JSONObject report() {
		JSONObject data = new JSONObject();
		
		data.put("id", getId());
		data.put("speed", speed);
		data.put("distance", totalDistance);
		data.put("co2", totalCont);
		data.put("class", contClass);
		data.put("status", status);

		if (status != VehicleStatus.PENDING && status != VehicleStatus.ARRIVED) {
			data.put("road", road.getId());
			data.put("location", location);
		}
		
		return data;
	}

	public String toString() { return getId(); }

	public static class CompareLocation implements Comparator<Vehicle> {

		@Override
		public int compare(Vehicle o1, Vehicle o2) {
			if (o1.getLocation() > o2.getLocation())
				return 1;
			else if (o1.getLocation() == o2.getLocation())
				return 0;
			return -1;
		}

	}
}
