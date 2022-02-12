package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
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
        JSONObject expOutJO = null;

        if (out == null) {
            out = new OutputStream() {
                @Override
                public void write(int b) throws IOException { }
            };
        }

        PrintStream p = new PrintStream(out);
        p.println("{");
        p.println("\"states\": [");

        JSONObject currState = null;
        JSONObject expState = null;

        currState = simulator.report();
        p.print(currState);
        if (expOutJO != null)
            expState = expOutJO.getJSONArray("states").getJSONObject(0);
        
        for (int i = 0; i < expOutJO.length(); i++)
            expState = expOutJO.getJSONArray("states").getJSONObject(i);
    }
}