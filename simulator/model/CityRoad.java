package simulator.model;

public class CityRoad extends Road {

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		totalCO2 = (weather == Weather.STORM || weather == Weather.WINDY) ? totalCO2 - 10 : totalCO2 - 2;
		if (totalCO2 < 0)
			throw new IllegalArgumentException("Contamination negative");
	}

	@Override
	void updateSpeedLimit() { }

	@Override
	int calculateVehicleSpeed(Vehicle v) { return ((11 - v.getContClass()) * maxSpeed)/11; }

}
