package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;

public class NewJunctionEventBuilder extends Builder<Event> {

    NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
        super("new_junction");
    }

    @Override
    protected Event createTheInstance(JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

}
