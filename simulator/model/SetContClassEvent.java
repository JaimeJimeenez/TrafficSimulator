package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetContClassEvent extends Event {

    private List<Pair<String, Integer>> cs;
	
	SetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		if (cs == null)
			throw new IllegalArgumentException("List null");
		this.cs = cs;
	}

	@Override
	void execute(RoadMap map) {
		for (Pair<String, Integer> c : cs) {
			if (map.getVehicle(c.getFirst()) == null)
				throw new IllegalArgumentException("Vehicle null");
			map.getVehicle(c.getFirst()).setContaminationClass(c.getSecond());
		}
	}
    
}
