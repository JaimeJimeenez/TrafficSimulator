package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.model.Event;
import simulator.factories.Factory;
import simulator.model.TrafficSimulator;

public class Controller {
	
	TrafficSimulator simulator;
	Factory<Event> eventsFactory;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		this.simulator = sim;
		this.eventsFactory = eventsFactory;
	}
	
	public void reset() { simulator.reset(); }
	
	public void loadEvents(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		JSONArray events = jsonInput.getJSONArray("events");
		
		for (int i = 0; i < events.length(); i++) 
			simulator.addEvent(eventsFactory.createInstance(events.getJSONObject(i)));
		
	}
	
	public void run(int steps, OutputStream out) {
		
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("  \"states\": [");
		
		for (int i = 0; i < steps - 1; i++) {
			simulator.advance();
			p.println(simulator.report().toString() + ",");
		}
		
		simulator.advance();
		p.println(simulator.report().toString());
		p.println("]");
		p.println("}");
		
	}
	
}
