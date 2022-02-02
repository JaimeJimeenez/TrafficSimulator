package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveAllStrategy;

public class MoveAllStrategyBuilder extends Builder<DequeuingStrategy> {

    MoveAllStrategyBuilder() {
        super("most_all_dqs");
    }

    @Override
    protected DequeuingStrategy createTheInstance(JSONObject data) {
       return new MoveAllStrategy();
    }

}
