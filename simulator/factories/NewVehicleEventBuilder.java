package simulator.factories;

import java.util.List;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {

    public NewVehicleEventBuilder() {
        super("new_vehicle");
    }

    @Override
    protected Event createTheInstance(JSONObject data) {
        int time = data.getInt("time");
        String id = data.getString("id");
        int maxSpeed = data.getInt("maxspeed");
        int contClass = data.getInt("class");
        List<String> itinerary = (List<String>) data.get("itinerary");
        
        return new NewVehicleEvent(time, id, maxSpeed, contClass, itinerary);
    }

}
