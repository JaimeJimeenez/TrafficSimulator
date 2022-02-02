package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {

    RoundRobinStrategyBuilder() {
        super("round_robin_lss");
        //TODO 
    }

    @Override
    protected LightSwitchingStrategy createTheInstance(JSONObject data) {
        int timeSlot = data.has("timeSlot") ? data.getInt("timeSlot") : 1;
        return new RoundRobinStrategy(timeSlot);
    }

}
