package simulator.model;

public class InterCityRoad extends Road {

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		switch(this.getWeather()) {
			case SUNNY:
				totalCont = ((100 - 2) * totalCont)/100;
				break;
			case CLOUDY:
				totalCont = ((100 - 3) * totalCont)/100;
				break;
			case RAINY:
				totalCont = ((100 - 10) * totalCont)/100;
				break;
			case WINDY:
				totalCont = ((100 - 15) * totalCont)/100;
				break;
			case STORM:
				totalCont = ((100 - 20) * totalCont)/100;
				break;
			default:
				break;
			}
	}

	@Override
	void updateSpeedLimit() { maxSpeed = totalCont > contLimit ? maxSpeed/2 : maxSpeed; }

	@Override
	int calculateVehicleSpeed(Vehicle v) { return weather == Weather.STORM ? limitSpeed*8/10 : limitSpeed; }

}
