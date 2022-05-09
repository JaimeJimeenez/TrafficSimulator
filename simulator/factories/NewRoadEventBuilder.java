package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event> {

	int time, length, CO2Limit, maxSpeed;
	String id, src, dest;
	Weather weather;
	
	NewRoadEventBuilder(String type) {
		super(type);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		this.time = data.getInt("time");
		this.id = data.getString("id");
		this.src = data.getString("src");
		this.dest = data.getString("dest");
		this.length = data.getInt("length");
		this.CO2Limit = data.getInt("co2limit");
		this.maxSpeed = data.getInt("maxspeed");
		this.weather = Weather.valueOf(data.getString("weather").toUpperCase());
		return createTheRoad();
	}
	
	abstract Event createTheRoad();

}
