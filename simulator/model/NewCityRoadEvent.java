package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent {

    String id, srcJunc, destJunc;
	int length, co2Limit, maxSpeed;
	Weather weather;
	
	public NewCityRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
	}

	@Override
	protected Road createRoadObject() {
		//return new CityRoad()...
		return null;
	}

}
