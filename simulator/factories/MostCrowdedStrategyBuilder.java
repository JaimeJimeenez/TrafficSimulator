package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {

    public MostCrowdedStrategyBuilder() {
        super("most_crowded_lss");
    }

    @Override
    public LightSwitchingStrategy createTheInstance(JSONObject data) {
        return new MostCrowdedStrategy(data.has("timeSlot") ? data.getInt("timeSlot") : 1);
    }

}
