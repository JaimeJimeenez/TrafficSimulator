package simulator.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import simulator.control.Controller;
import simulator.factories.Builder;
import simulator.factories.BuilderBasedFactory;
import simulator.factories.Factory;
import simulator.factories.MostCrowdedStrategyBuilder;
import simulator.factories.MoveAllStrategyBuilder;
import simulator.factories.MoveFirstStrategyBuilder;
import simulator.factories.NewCityRoadEventBuilder;
import simulator.factories.NewInterCityRoadEventBuilder;
import simulator.factories.NewJunctionEventBuilder;
import simulator.factories.NewVehicleEventBuilder;
import simulator.factories.RoundRobinStrategyBuilder;
import simulator.factories.SetContClassEventBuilder;
import simulator.factories.SetWeatherEventBuilder;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.TrafficSimulator;
import simulator.view.MainWindow;

public class Main {

	private final static Integer _timeLimitDefaultValue = 10;
	
	private static String _inFile = null;
	private static String _outFile = null;
	private static String _mode = null;
	
	private static Factory<Event> _eventsFactory = null;	
	private static Factory<LightSwitchingStrategy> _lightSwitchingFactory = null;
	private static Factory<DequeuingStrategy> _dequeuingFactory = null;
	
	private static Integer _steps = null;
	
	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseTicksOption(line);
			parseModeOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}
	
	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Mode of view of the simulation").build());
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg().desc("Ticks to the simulator's main loop (default value is 10)").build());
		
		return cmdLineOptions;
	}

	private static void parseTicksOption(CommandLine line) {
		
		if (line.hasOption("t")) 
			_steps = Integer.parseInt(line.getOptionValue("t"));
		else
			_steps = _timeLimitDefaultValue;
		
	}
	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null && _mode == "console") {
			throw new ParseException("An events file is missing");
		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
	}

	private static void parseModeOption(CommandLine line) throws ParseException {
		if (line.hasOption("m")) 
			_mode = line.getOptionValue("m");
	}
	
	private static void initFactories() {

		ArrayList<Builder<LightSwitchingStrategy>> lightsBuilder = new ArrayList<>();
		lightsBuilder.add(new MostCrowdedStrategyBuilder());
		lightsBuilder.add(new RoundRobinStrategyBuilder());
		_lightSwitchingFactory = new BuilderBasedFactory<LightSwitchingStrategy>(lightsBuilder);
		
		ArrayList<Builder<DequeuingStrategy>> dequeuingBuilder = new ArrayList<>();
		dequeuingBuilder.add(new MoveAllStrategyBuilder());
		dequeuingBuilder.add(new MoveFirstStrategyBuilder());
		_dequeuingFactory = new BuilderBasedFactory<DequeuingStrategy>(dequeuingBuilder);
		
		ArrayList<Builder<Event>> eventsBuilder = new ArrayList<>();
		eventsBuilder.add(new NewCityRoadEventBuilder());
		eventsBuilder.add(new NewInterCityRoadEventBuilder());
		eventsBuilder.add(new NewVehicleEventBuilder());
		eventsBuilder.add(new NewJunctionEventBuilder(_lightSwitchingFactory, _dequeuingFactory));
		eventsBuilder.add(new SetContClassEventBuilder());
		eventsBuilder.add(new SetWeatherEventBuilder());
		_eventsFactory = new BuilderBasedFactory<Event>(eventsBuilder);
		
	}

	private static void startBatchMode() throws IOException {
		TrafficSimulator sim = new TrafficSimulator();
		Controller ctrl = new Controller(sim, _eventsFactory);
		
		try (InputStream is = new FileInputStream(new File(_inFile));) {
			
			ctrl.loadEvents(is);
			
			OutputStream out = _outFile == null ? System.out : new FileOutputStream(_outFile);
			
			ctrl.run(_steps, out);
			
			is.close();
			out.close();
		} catch (FileNotFoundException e) {
			throw new IOException("Invalid Input File");
		}
		
	}

	private static void startGuiMode() throws IOException {
		TrafficSimulator sim = new TrafficSimulator();
		Controller ctrl = new Controller(sim, _eventsFactory);
		
		if (_inFile != null) {
			try (InputStream is = new FileInputStream(new File(_inFile));) {
				ctrl.loadEvents(is);
				is.close();
				
			} catch (FileNotFoundException e) {
				throw new IOException("Invalid Input File");
			}
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainWindow(ctrl);
			}
		});
		
	}
	
	private static void start(String[] args) throws IOException {
		initFactories();
		parseArgs(args);
		
		if (_mode == null)
			startGuiMode();
		else if (_mode.equals("console"))
			startBatchMode();
		else
			startGuiMode();
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help

	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
