package simulator.factories;

import simulator.model.Event;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder {

    public NewCityRoadEventBuilder(String type) {
        super(type);
        //TODO Auto-generated constructor stub
    }

    @Override
    Event createTheRoad() {
        return null;
    }
}
