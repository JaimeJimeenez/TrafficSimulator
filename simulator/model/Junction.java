package simulator.model;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject {

	private List<Road> roads;
	private Map<Junction, Road> mapRoads;
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
	}
	
	int getX() { return x; }
	
	int getY() { return y; }
	
	public void addIncomingRoad(Road road) {
		
	}
	
	public void addOutgoingRoad(Road road) {
		
	}
	
	void enter(Vehicle v) {
		
	}
	
	void roadTo(Junction junction) {
		
	}
	
	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}
}