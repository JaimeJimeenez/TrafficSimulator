package simulator.model;

import java.util.List;

public class NewVehicleEvent extends Event {

    private String id;
    private List<Junction> itinerary;
    private int maxSpeed, speed, location, contClass, totalCont, totalDistance;
    private VehicleStatus status;
    private Road road;

    public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<Junction> itinerary) {
        super(time);
        this.id = id;
        this.maxSpeed = maxSpeed;
        this.contClass = contClass;
        this.itinerary = itinerary;
    }

    @Override
    void execute(RoadMap map) {
        Vehicle v = new Vehicle(id, maxSpeed, contClass, itinerary);
        map.addVehicle(v);
        v.moveToNextRoad();
    }
    
}
