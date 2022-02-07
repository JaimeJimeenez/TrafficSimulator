package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;

abstract public class NewRoadEventBuilder extends Builder<Event> {

    NewRoadEventBuilder(String type) {
        super(type);
        //TODO Auto-generated constructor stub
    }

    @Override
    protected Event createTheInstance(JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    abstract Event createTheRoad();
}
