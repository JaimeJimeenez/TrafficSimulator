package simulator.factories;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder {

	public NewInterCityRoadEventBuilder() {
		super("new_inter_city_road");
	}

	@Override
	Event createTheRoad() {
		return new NewInterCityRoadEvent(time, id, src, dest, length, CO2Limit, maxSpeed, weather);
	}
}
