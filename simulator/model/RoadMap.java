package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	
	private List<Junction> junctions;
	private List<Road> roads;
	private List<Vehicle> vehicles;
	private Map<String, Junction> junctionsMap;
	private Map<String, Road> roadsMap;
	private Map<String, Vehicle> vehiclesMap;

	RoadMap() { reset(); }
	
	void reset() {
		junctions = new ArrayList<>();
		roads = new ArrayList<>();
		vehicles = new ArrayList<>();
		junctionsMap = new HashMap<>();
		roadsMap = new HashMap<>();
		vehiclesMap = new HashMap<>();
	}

	void addJunction(Junction junction) {
		if (junctionsMap.containsKey(junction.getId()))
			throw new IllegalArgumentException("Error: Some junction already has the same id");
		
		junctions.add(junction);
		junctionsMap.put(junction.getId(), junction);
	}
	
	private boolean checkJunctionsRoad(Road road) { return junctionsMap.containsValue(road.getSrc()) && junctionsMap.containsValue(road.getDest()); }
	
	void addRoad(Road road) {
		if (roadsMap.containsKey(road.getId()) || !checkJunctionsRoad(road))
			throw new IllegalArgumentException("Error: Junctions don't exist or some road already has the same id");
		
		roads.add(road);
		roadsMap.put(road.getId(), road);
	}
	
	private boolean checkItinerary(List<Junction> itinerary) { 
		
		for (Junction j : itinerary)
			if (!junctionsMap.containsValue(j))
				return false;
		
		return true;
	}
	
	void addVehicle(Vehicle vehicle) {
		if (vehiclesMap.containsKey(vehicle.getId()) || !checkItinerary(vehicle.getItinerary()))
			throw new IllegalArgumentException("Error: Itinerary not valid or some vehicle already has the same id");
		
		vehicles.add(vehicle);
		vehiclesMap.put(vehicle.getId(), vehicle);
	}
	
	public Junction getJunction(String id) { return junctionsMap.get(id); }
	
	public Road getRoad(String id) { return roadsMap.get(id); }
	
	public Vehicle getVehicle(String id) { return vehiclesMap.get(id); }
	
	public List<Junction> getJunctions() { return Collections.unmodifiableList(junctions); }
	
	public List<Road> getRoads() { return Collections.unmodifiableList(roads); }
	
	public List<Vehicle> getVehicles() { return Collections.unmodifiableList(vehicles); }
	
	private JSONArray getDataJunctions() {
		JSONArray data = new JSONArray();
		
		for (Junction j : junctions) 
			data.put(j.report());
		
		return data;
	}
	
	private JSONArray getDataRoads() {
		JSONArray data = new JSONArray();
		
		for (Road r : roads)
			data.put(r.report());
		
		return data;
	}
	
	private JSONArray getDataVehicles() {
		JSONArray data = new JSONArray();
		
		for (Vehicle v : vehicles)
			data.put(v.report());
		
		return data;
	}
	
	public JSONObject report() {
		JSONObject data = new JSONObject();
		
		data.put("junctions", getDataJunctions());
		data.put("roads", getDataRoads());
		data.put("vehicles", getDataVehicles());
		
		return data;
	}
	
}
