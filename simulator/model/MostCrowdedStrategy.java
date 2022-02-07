package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {

	private int timeSlot;
	
	public MostCrowdedStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		if (roads.isEmpty())
			return -1;
		else if (currGreen == -1) {
			int maxSize = -1;
			for (int i = 0; i < qs.size(); i++) {
				if (qs.get(i).size() > maxSize) 
					maxSize = i;
			}
			return maxSize;
		}
		else if (currTime - lastSwitchingTime < timeSlot)
			return currGreen;
		else {
			int next = -1;
			for (int i = currGreen + 1; i < qs.size(); i++) {
				if (qs.get(i).size() > next)
					next = i;
				if (i + 1 == qs.size())
					i = 0;
				if (i == currGreen)
					break;
			}
			return next;
		}
	}

}
