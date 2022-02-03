package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {

    private List<Pair<String, Weather>> ws;
	
	SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
		super(time);
		if (ws.isEmpty())
			throw new IllegalArgumentException("List empty");
		this.ws = ws;
	}

	@Override
	void execute(RoadMap map) {
		for (Pair<String, Weather> w : ws) {
			if (map.getRoad(w.getFirst()) == null)
				throw new IllegalArgumentException("Road doesnt exist");
			map.getRoad(w.getFirst()).setWeather(w.getSecond());
		}
	}
    
}
