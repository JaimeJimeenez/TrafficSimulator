package simulator.factories;

import java.util.List;

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
		List<Pair<String, Integer>> info = (List<Pair<String, Integer>>) data.get("info");
		return new SetContClassEvent(time, info);
	}

}
