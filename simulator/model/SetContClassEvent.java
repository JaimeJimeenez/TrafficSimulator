package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetContClassEvent extends Event {

	private List<Pair<String, Integer>> cs;
	
	public SetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		if (cs == null)
			throw new IllegalArgumentException("List null");
		this.cs = cs;
	}

	@Override
	void execute(RoadMap map) {
		for (Pair<String, Integer> p : cs) {
			if (map.getVehicle(p.getFirst()) == null)
				throw new IllegalArgumentException("Error: This vehicle doesn't exist");
			map.getVehicle(p.getFirst()).setContaminationClass(p.getSecond());
		}	
	}

}
