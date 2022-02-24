package simulator.model;

import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator {
	
	RoadMap roads;
	List<Event> events;
	int time;
	
	public TrafficSimulator() {
		reset();
	}
	
	public void reset() {
		roads = new RoadMap();
		events = new SortedArrayList<>();
		time = 0;
	}
	
	public void addEvent(Event event) {
		events.add(event);
	}
	
	public void advance() {
		time++;
		
		while (!events.isEmpty()) {
			if (events.get(0).getTime() > time)
				break;
			events.get(0).execute(roads);
			events.remove(0);
		}
		
		for (Junction junc : roads.getJunctions()) 
			junc.advance(time);
		
		for (Road road : roads.getRoads())
			road.advance(time);
	}
	
	public JSONObject report() {
		JSONObject data = new JSONObject();
		
		data.put("time", time);
		data.put("state", roads.report());
		
		return data;
	}

}
