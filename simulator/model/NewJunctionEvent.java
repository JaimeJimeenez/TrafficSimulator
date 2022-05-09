package simulator.model;

public class NewJunctionEvent extends Event {

	String id;
	LightSwitchingStrategy lsStrategy;
	DequeuingStrategy dqsStrategy;
	int xCoor;
	int yCoor;
	
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqsStrategy, int xCoor, int yCoor) {
		super(time);
		this.id = id;
		this.lsStrategy = lsStrategy;
		this.dqsStrategy = dqsStrategy;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
	}

	@Override
	void execute(RoadMap map) {
		Junction newJunc = new Junction(id, lsStrategy, dqsStrategy, xCoor, yCoor);
		map.addJunction(newJunc);
	}
	
	@Override
	public String toString() { return "New Junction '" + id + "'"; }
}
