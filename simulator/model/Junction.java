package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject {

	
	private List<Road> roads;
	private Map<Junction, Road> outGoingRoads;
	private List<List<Vehicle>> queueVehicles;
	private Map<Road, List<Vehicle>> roadVehicles;
	private int indexLightSwitching;
	private int lastLightSwitching;
	private LightSwitchingStrategy light;
	private DequeuingStrategy dq;
	private int x;
	private int y;
	
	Junction(String id, LightSwitchingStrategy light, DequeuingStrategy dq, int x, int y) {
		super(id);
		this.light = light;
		this.dq = dq;
		this.x = x;
		this.y = y;
		this.roads = new ArrayList<>();
		this.outGoingRoads = new HashMap<>();
		this.queueVehicles = new ArrayList<>();
		this.roadVehicles = new HashMap<>();
		this.indexLightSwitching = -1;
		this.lastLightSwitching = -1;
	}
	
	int getX() { return x; }
	
	int getY() { return y; }
	
	public void addIncomingRoad(Road road) {
		if (road.getDest() != this)
			throw new IllegalArgumentException("This road is not valid");
		roads.add(road);
		queueVehicles.add(new ArrayList<Vehicle>());
		roadVehicles.put(road, new ArrayList<Vehicle>());
	}
	
	public void addOutgoingRoad(Road road) {
		if (outGoingRoads.containsValue(road.getDest()) || road.getSrc() != this)
			throw new IllegalArgumentException("Error: This road is not valid");
		outGoingRoads.put(road.getDest(), road);
	}
	
	void enter(Vehicle v) {
		for (int i = 0; i < roads.size(); i++) 
			if (roads.get(i) == v.getRoad())
				queueVehicles.get(i).add(v);
		roadVehicles.get(v.getRoad()).add(v);
	}
	
	Road roadTo(Junction junction) { return outGoingRoads.get(junction); }
	
	@Override
	public void advance(int time) {
		List<Vehicle> vehicles = dq.dequeue(roadVehicles.get(this));
		int nextGreen = light.chooseNextGreen(roads, queueVehicles, indexLightSwitching, lastLightSwitching, time);
		
		for (Vehicle v : vehicles) {
			v.advance(time);
			v.moveToNextRoad();
		}
		
		if (nextGreen != indexLightSwitching) {
			indexLightSwitching = nextGreen;
			lastLightSwitching = time;
		}
	}

	@Override
	public JSONObject report() {
		JSONObject data = new JSONObject();
		
		data.put("id", getId());
		
		if (roads.get(indexLightSwitching).getId() != null)
			data.put("green", roads.get(indexLightSwitching).getId());
		else 
			data.put("green", "none");
		
		
		return data;
	}
}
