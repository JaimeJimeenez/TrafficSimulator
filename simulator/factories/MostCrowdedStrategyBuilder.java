package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {

    MostCrowdedStrategyBuilder() {
        super("most_crowded_lss");
    }

    @Override
    protected LightSwitchingStrategy createTheInstance(JSONObject data) {
        int timeSlot = data.has("timeSlot") ? data.getInt("timeSlot") : 1;
        return new MostCrowdedStrategy(timeSlot);
    }

}
