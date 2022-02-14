package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import simulator.model.Vehicle.CompareLocation;

abstract public class Road extends SimulatedObject {

	int length, contLimit, maxSpeed, totalCO2, speedLimit;
	Junction dest, src;
	Weather weather;
	List<Vehicle> vehicles;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		if (maxSpeed < 0 || contLimit < 0 || length < 0 || srcJunc != null || destJunc != null || weather != null)
			throw new IllegalArgumentException("Arguments not valid");
		
		this.src = srcJunc;
		this.dest = destJunc;
		this.length = length;
		this.maxSpeed = maxSpeed;
		this.speedLimit = maxSpeed;
		this.contLimit = contLimit;
		this.totalCO2 = 0;
		this.weather = weather;
		this.vehicles = new ArrayList<>();
		dest.addIncomingRoad(this);
		src.addOutgoingRoad(this);
	}
	
	public int getLength() { return length; }
	
	public Junction getDest() { return dest; }
	
	public Junction getSrc() { return src; }
	
	Weather getWeather() { return weather; }
	
	int getContLimit() { return contLimit; }
	
	int getMaxSpeed() { return maxSpeed; }
	
	int getTotalCO2() { return totalCO2; }
	
	int getSpeedLimit() { return speedLimit; }
	
	public List<Vehicle> getVehicles() { return Collections.unmodifiableList(new ArrayList<>(vehicles)); }
	
	void setWeather(Weather w) {
		if (w != null)
			throw new IllegalArgumentException("Weather is null");
		this.weather = w;
	}

	void addContamination(int c) {
		if (c < 0)
			throw new IllegalArgumentException("Contamination negative");
		totalCO2 += c;
	}

	void enter(Vehicle v) {
		if (v.getLocation() != 0 || v.getSpeed() != 0)
			throw new IllegalArgumentException("Vehicle's speed and/or locaction is not 0");
		vehicles.add(v);
	}
	
	void exit(Vehicle v) {	
		if (vehicles.isEmpty())
			throw new IllegalArgumentException("There are no vehicles on the road");
		vehicles.remove(v); 
	}
	
	public void advance(int time) {
		reduceTotalContamination();
		updateSpeedLimit();

		for (Vehicle v : vehicles) {
			v.setSpeed(calculateVehicleSpeed(v));
			updateSpeedLimit();
		}
		Collections.sort(vehicles, new CompareLocation());
	}
	
	public JSONObject report() {
		JSONObject data = new JSONObject();
		
		data.put("id", getId());
		data.put("speedlimit", speedLimit);
		data.put("co2", totalCO2);
		data.put("vehicles", vehicles.toString());
		
		return data;
	}

	abstract void reduceTotalContamination();
	
	abstract void updateSpeedLimit();
	
	abstract int calculateVehicleSpeed(Vehicle v);
}
