package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {

	private List<Road> inRoads;
	private Map<Junction, Road> outRoads;
	private List<List<Vehicle>> queuesVehicles;
	private Map<Road, List<Vehicle>> roadVehicles;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqsStrategy;
	private int indexGreen, lastLightSwitch, x, y;
	
	Junction(String id, LightSwitchingStrategy lsStrategy,
			DequeuingStrategy dqsStrategy, int x, int y) {
		super(id);
		
		if (lsStrategy == null || dqsStrategy == null || x < 0 || y < 0)
			throw new IllegalArgumentException("Error: Arguments not valid");
		
		this.inRoads = new ArrayList<>();
		this.outRoads = new HashMap<>();
		this.queuesVehicles = new ArrayList<>();
		this.roadVehicles = new HashMap<>();
		this.lsStrategy = lsStrategy;
		this.dqsStrategy = dqsStrategy;
		this.indexGreen = -1;
		this.lastLightSwitch = 0;
		this.x = x;
		this.y = y;
	}
	
	int getX() { return x; }
	
	int getY() { return y; }
	
	void addIncomingRoad(Road road) {
		if (road.getDest() != this)
			throw new IllegalArgumentException("Error: This road is not valid");
		
		inRoads.add(road);
		queuesVehicles.add(new ArrayList<>());
		roadVehicles.put(road, new ArrayList<>());
	}
	
	void addOutGoingRoad(Road road) {
		if (road.getSrc() != this || outRoads.containsKey(road.getDest()))
			throw new IllegalArgumentException("Error: Roads not valid");
		
		outRoads.put(road.getDest(), road);
	}
	
	void enter(Vehicle vehicle) { roadVehicles.get(vehicle.getRoad()).add(vehicle); }
	
	Road roadTo(Junction junction) { return outRoads.get(junction); }

	@Override
	void advance(int time) {
		
		// Dequeuing Strategy
		if (indexGreen != -1 && !queuesVehicles.isEmpty()) {
			List<Vehicle> vehicles = roadVehicles.get(inRoads.get(indexGreen));
			
			if (!vehicles.isEmpty()) {
				List<Vehicle> moveVehicles = dqsStrategy.dequeue(vehicles);
				
				for (Vehicle v : moveVehicles) {
					v.moveToNextRoad();
					vehicles.remove(v);
				}
			}
		}
		
		// Switch light
		int newIndex = lsStrategy.chooseNextGreen(inRoads, queuesVehicles, indexGreen, lastLightSwitch, time);
		
		if (newIndex != indexGreen) {
			indexGreen = newIndex;
			lastLightSwitch = time;
		}
	}
	
	private JSONObject getDataRoad(Road r) {
		JSONObject data = new JSONObject();
		JSONArray vehicles = new JSONArray();
		
		data.put("road", r.getId());
		for (Vehicle v : roadVehicles.get(r))
			vehicles.put(v.toString());
		data.put("vehicles", vehicles);
		
		return data;
	}
	
	private JSONArray getDataQueues() {
		JSONArray data = new JSONArray();
		
		for (Road r : inRoads) 
			data.put(getDataRoad(r));
			
		return data;
	}

	@Override
	public JSONObject report() {
		JSONObject data = new JSONObject();
		
		data.put("id", _id);
		if (indexGreen == -1)
			data.put("green", "none");
		else
			data.put("green", inRoads.get(indexGreen).getId());
		data.put("queues", getDataQueues());
		
		return data;
	}
	
	
}
