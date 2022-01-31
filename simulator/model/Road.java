package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

abstract public class Road {

	String id;
	Junction srcJunc;
	Junction destJunc;
	int length;
	int maxSpeed;
	int limitSpeed;
	int contLimit;
	int totalCont;
	Weather weather;
	List<Vehicle> vehicles;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		if (maxSpeed < 0 || contLimit < 0 || length < 0 || srcJunc != null || destJunc != null || weather != null)
			throw new IllegalArgumentException("Arguments not valid");
		this.id = id;
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.length = length;
		this.maxSpeed = maxSpeed;
		this.limitSpeed = maxSpeed;
		this.contLimit = contLimit;
		this.totalCont = 0;
		this.weather = weather;
		this.vehicles = new ArrayList<>();
	}
	
	String getId() { return id; }
	
	int getLength() { return length; }
	
	Junction getDest() { return destJunc; }
	
	Junction getSrc() { return srcJunc; }
	
	Weather getWeather() { return weather; }
	
	int getContLimit() { return contLimit; }
	
	int getMaxSpeed() { return maxSpeed; }
	
	int getTotalCO2() { return totalCont; }
	
	int getSpeedLimit() { return limitSpeed; }
	
	List<Vehicle> getVehicles() { return Collections.unmodifiableList(new ArrayList<>(vehicles)); }
	
	void enter(Vehicle v) {
		if (v.getLocation() != 0 && v.getSpeed() != 0)
			throw new IllegalArgumentException("Vehicle's speed and/or locaction is not 0");
		vehicles.add(v);
	}
	
	void exit(Vehicle v) {	
		if (vehicles.isEmpty())
			throw new IllegalArgumentException("There are no vehicles on the road");
		vehicles.remove(v); 
	}
	
	void setWeather(Weather w) {
		if (w != null)
			throw new IllegalArgumentException("Weather is null");
		this.weather = w;
	}
	
	void addContamination(int c) {
		if (c < 0)
			throw new IllegalArgumentException("Contamination negative");
		totalCont += c;
	}
	
	abstract void reduceTotalContamination();
	
	abstract void updateSpeedLimit();
	
	abstract int calculateVehicleSpeed(Vehicle v);
	
	void advance(int time) {
		
		reduceTotalContamination();
		
		updateSpeedLimit();
		
		for (Vehicle v : vehicles) {
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
		}
	}
	
	public JSONObject report() {
		JSONObject data = new JSONObject();
		
		data.put("id", id);
		data.put("speedlimit", maxSpeed);
		data.put("weather", weather);
		data.put("co2", totalCont);
		data.put("vehicles", vehicles.toString());
		
		return data;
	}
}
