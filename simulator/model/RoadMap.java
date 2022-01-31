package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		junctionMap = new HashMap();
		vehiclesMap = new HashMap();
	}

	void addJunction(Junction junction) {
		if (!junctionMap.containsKey(junction.getId())) {
			junctionMap.put(junction.getId(), junction);
		}
	}

	void addRoad(Road road) {

	}

	void addVehicle(Vehicle v) {

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

	public List<Junction> getJunction() { return Collections.unmodifiableList(new ArrayList<>(junctions)); }
	

	public JSONObject report() {
		JSONObject data = new JSONObject();

		data.put("junctions", junctions.toString());
		data.put("road", roads.toString());
		data.put("vehicles", vehicles.toString());

		return data;
	}
}
