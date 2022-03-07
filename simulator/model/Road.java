package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Vehicle.CompareLocation;

abstract public class Road extends SimulatedObject {
	
	int length, contLimit, maxSpeed, totalCO2, speedLimit;
	Junction dest, src;
	Weather weather;
	List<Vehicle> vehicles;
	
	Road(String id, Junction src, Junction dest, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		if (maxSpeed <= 0 || contLimit <= 0 || length <= 0 || src == null || dest == null || weather == null)
			throw new IllegalArgumentException("Argument(s) not valid");
		
		this.dest = dest;
		this.src = src;
		this.length = length;
		this.contLimit = contLimit;
		this.maxSpeed = maxSpeed;
		this.totalCO2 = 0;
		this.speedLimit = maxSpeed;
		this.weather = weather;
		this.vehicles = new ArrayList<Vehicle>();
		
		src.addOutGoingRoad(this);
		dest.addIncomingRoad(this);
	}
	
	public int getLength() { return length; }
	
	public Junction getSrc() { return src; }
	
	public Junction getDest() { return dest; }
	
	Weather getWeather() { return weather; }
	
	public int getContLimit() { return contLimit; }
	
	int getMaxSpeed() { return maxSpeed; }
	
	public int getTotalCO2() { return totalCO2; }
	
	int getSpeedLimit() { return speedLimit; }
	
	public List<Vehicle> getVehicles() { return Collections.unmodifiableList(vehicles); }
	
	void setWeather(Weather weather) {
		if (weather == null)
			throw new IllegalArgumentException("Weather is null");
		this.weather = weather;
	}
	
	void addContamination(int cont) {
		if (cont < 0)
			throw new IllegalArgumentException("Contamination negative");
		totalCO2 += cont;
	}
	
	void enter(Vehicle v) {
		if (v.getLocation() != 0 || v.getSpeed() != 0)
			throw new IllegalArgumentException("Location or speed not 0");
		vehicles.add(v);
	}
	
	void exit(Vehicle v) {
		if (!vehicles.contains(v))
			throw new IllegalArgumentException("Vehicle doesnt exist");
		vehicles.remove(v);
	}
	
	public void advance(int time) {
		if (totalCO2 != 0)
			reduceTotalContamination();
		updateSpeedLimit();
		
		for (Vehicle v : vehicles) {
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
		}
		
		Collections.sort(vehicles, new CompareLocation());
	}
	
	protected JSONArray getDataVehicles() {
		JSONArray data = new JSONArray();
		
		for (Vehicle v : vehicles)
			data.put(v.toString());
		
		return data;
	}
	
	public JSONObject report() {
		JSONObject data = new JSONObject();
		
		data.put("id", getId());
		data.put("speedlimit", speedLimit);
		data.put("weather", weather.toString());
		data.put("co2", totalCO2);
		data.put("vehicles", getDataVehicles());
		
		return data;
	}
	
	abstract void reduceTotalContamination();
	
	abstract void updateSpeedLimit();
	
	abstract int calculateVehicleSpeed(Vehicle v);
	
	
}
