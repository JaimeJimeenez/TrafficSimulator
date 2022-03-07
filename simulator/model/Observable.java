package simulator.model;

public interface Observable<T> {
	
	void addObserver(T o);
	void removeObserve(T o);
	
}
