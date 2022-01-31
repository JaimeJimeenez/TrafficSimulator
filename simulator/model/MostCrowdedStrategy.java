package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {

    private int timeSlot;

    MostCrowdedStrategy(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    @Override
    public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
        
        if (roads.isEmpty())
            return -1;
        //TODO
        return 0;
    }

}
