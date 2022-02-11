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
	private Map<String, Junction> junctionMap;
	private Map<String, Vehicle> vehiclesMap;

	RoadMap() {
		reset();
	}
	
	void reset() {
		junctions = new ArrayList<>();
		roads = new ArrayList<>();
		vehicles = new ArrayList<>();
		junctionMap = new HashMap<>();
		vehiclesMap = new HashMap<>();
	}
	
	void addJunction(Junction junction) {
		if (junctions.contains(junction))
			throw new IllegalArgumentException("Junction already exists");
		junctionMap.put(junction.getId(), junction);
		junctions.add(junction);
	}
	
	void addRoad(Road road) {
		if (roads.contains((road))) // TODO Complete the if clause
			throw new IllegalArgumentException("Error: Cannot add this road");
		roads.add(road);
	}
	
	void addVehicle(Vehicle v) {
		if (vehicles.contains(v)) // TODO Complete the if clause
			throw new IllegalArgumentException("Error: Cannot add this vahicle");
		vehicles.add(v);
		vehiclesMap.put(v.getId(), v);
	}
	
	public Junction getJunction(String id) { return junctionMap.get(id); }
	
	public Road getRoad(String id) { 
		for (Road r : roads) 
			if (r.getId() == id)
				return r;
		return null;
	}
	
	public Vehicle getVehicle(String id) { return vehiclesMap.get(id); }
	
	public List<Vehicle> getVehicles() { return Collections.unmodifiableList(new ArrayList<>(vehicles)); }
	
	public List<Road> getRoads() { return Collections.unmodifiableList(new ArrayList<>(roads)); }
	
	public List<Junction> getJunctions() { return Collections.unmodifiableList(new ArrayList<>(junctions)); }
	
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
		data.put("road", getDataRoads());
		data.put("vehicles", getDataVehicles());
		
		return data;
	}
}
