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
			totalCO2 = ((100 - 2) * totalCO2)/100;
			break;
		case CLOUDY:
			totalCO2= ((100 - 3) * totalCO2)/100;
			break;
		case RAINY:
			totalCO2 = ((100 - 10) * totalCO2)/100;
			break;
		case WINDY:
			totalCO2 = ((100 - 15) * totalCO2)/100;
			break;
		case STORM:
			totalCO2 = ((100 - 20) * totalCO2)/100;
			break;
		default:
			break;
		}
	}

	@Override
	void updateSpeedLimit() { speedLimit = totalCO2 > contLimit ? maxSpeed/2 : maxSpeed; }

	@Override
	int calculateVehicleSpeed(Vehicle v) { return (weather == Weather.STORM) ? speedLimit *8/10 : speedLimit; }

}
