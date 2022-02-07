package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {

    Factory<LightSwitchingStrategy> lssFactory;
    Factory<DequeuingStrategy> dqsStrategy;

    public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
        super("new_junction");
        this.lssFactory = lssFactory;
        this.dqsStrategy = dqsFactory;
    }

    @Override
    protected Event createTheInstance(JSONObject data) {
        int time = data.getInt("time");
        String id = data.getString("id");
        int[] coor = (int[]) data.get("coor");
        LightSwitchingStrategy ls_strategy = lssFactory.createInstance(data.getJSONObject("ls_strategy"));
        DequeuingStrategy dqs_strategy = dqsStrategy.createInstance(data.getJSONObject("dqs_strategy"));
        return new NewJunctionEvent(time, id, ls_strategy, dqs_strategy, coor[0], coor[1]);
    }

}
