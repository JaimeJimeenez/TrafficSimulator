package simulator.model;

import java.util.List;

public interface DequeingStrategy {
	List<Vehicle> dequeue(List<Vehicle> q);
}
