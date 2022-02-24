package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {

    public SetWeatherEventBuilder() {
		super("set_weather");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		JSONArray json = data.getJSONArray("info");
		List<Pair<String, Weather>> info = new ArrayList<>();

		for (int i = 0; i < json.length(); i++)
			info.add(new Pair<String, Weather> (json.getJSONObject(i).getString("road"), Weather.valueOf(json.getJSONObject(i).getString("weather").toUpperCase())));
		
		return new SetWeatherEvent(time, info);
	}

}
