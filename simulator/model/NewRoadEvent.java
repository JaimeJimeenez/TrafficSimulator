package simulator.model;

abstract public class NewRoadEvent extends Event {

    String id, srcJunc, destJunc;
	int length, co2Limit, maxSpeed;
	Weather weather;
	Junction dest, src;
	
	public NewRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		this.id = id;
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.length = length;
		this.co2Limit = co2Limit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;
	}

	@Override
	void execute(RoadMap map) {
		dest = map.getJunction(destJunc);
		src = map.getJunction(srcJunc);
		map.addRoad(createRoadObject());
	}
	
	abstract protected Road createRoadObject();
}
