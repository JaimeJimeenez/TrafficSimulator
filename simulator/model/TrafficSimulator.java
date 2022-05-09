package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver> {
	
	RoadMap roads;
	List<Event> events;
	List<TrafficSimObserver> observer;
	int time;
	
	public TrafficSimulator() {
		observer = new ArrayList<>();
		reset();
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		if (!observer.contains(o))
			observer.add(o);
	}

	@Override
	public void removeObserve(TrafficSimObserver o) {
		if (observer.contains(o))
			observer.remove(o);
	}
	
	public void reset() {
		roads = new RoadMap();
		events = new SortedArrayList<>();
		time = 0;
		
		for (TrafficSimObserver obs : observer)
			obs.onReset(roads, events, time);
	}
	
	public void addEvent(Event event) {
		events.add(event);
		
		for (TrafficSimObserver obs : observer)
			obs.onEventAdded(roads, events, event, time);
	}
	
	public void advance() {
		try {
			time++;
			
			for (TrafficSimObserver obs : observer)
				obs.onAdvanceStart(roads, events, time);
			
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
			
			for (TrafficSimObserver obs : observer)
				obs.onAdvanceEnd(roads, events, time);
		
		} catch (Exception e) {
			
			for (TrafficSimObserver obs : observer)
				obs.onError(e.getMessage());
			
			throw e;
		}
	}
	
	public JSONObject report() {
		JSONObject data = new JSONObject();
		
		data.put("time", time);
		data.put("state", roads.report());
		
		return data;
	}

}
