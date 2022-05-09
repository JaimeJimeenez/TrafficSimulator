package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {

	public SetContClassEventBuilder() {
		super("set_cont_class");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		JSONArray json = data.getJSONArray("info");
		List<Pair<String, Integer>> info = new ArrayList<>();
		
		for (int i = 0; i < json.length(); i++) 
			info.add(new Pair<String, Integer>(json.getJSONObject(i).getString("vehicle"), json.getJSONObject(i).getInt("class")));
		
		return new SetContClassEvent(time, info);
	}

}
