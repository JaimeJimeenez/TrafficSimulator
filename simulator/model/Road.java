package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

abstract public class Road {

	private String id;
	private Junction srcJunc;
	private Junction destJunc;
	private int length;
	private int maxSpeed;
	private int limitSpeed;
	private int contAlarm;
	private int totalCont;
	private Weather weather;
	private List<Vehicle> vehicles;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		this.id = id;
	}
	
	void enter(Vehicle v) {
		
	}
	
	void exit(Vehicle v) {
		
	}
	
	void setWeather(Weather w) {
		
	}
	
	void addContamination(int c) {
		
	}
	
	abstract void reduceTotalContamination();
	
	abstract void updateSpeedLimit();
	
	abstract int calculateVehicleSpeed(Vehicle v);
	
	void advance(int time) {
		
	}
	
	int getLength() { return length; }
	
	Junction getDest() { return destJunc; }
	
	Junction getSrc() { return srcJunc; }
	
	Weather getWeather() { return weather; }
	
	int getContLimit() { return contAlarm; }
	
	int getMaxSpeed() { return maxSpeed; }
	
	int getTotalCO2() { return totalCont; }
	
	int getSpeedLimit() { return limitSpeed; }
	
	List<Vehicle> getVehicles() { return Collections.unmodifiableList(new ArrayList<>(vehicles)); }
	
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
