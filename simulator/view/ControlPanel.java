package simulator.view;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.factories.SetContClassEventBuilder;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	
	private RoadMap map;
	
	private Controller ctrl;
	private ChangeCO2ClassDialog co2ClassDialog;
	private ChangeWeatherDialog weatherDialog;
	
	private JButton fileButton;
	private JFileChooser fileChooser;
	private JButton contClassButton;
	private JButton weatherButton;
	private JButton runButton;
	private JButton stopButton;
	private JSpinner ticksSpinner;
	private JButton exitButton;
	
	private boolean _stopped;
	
	ControlPanel(Controller ctrl) {
		this.ctrl = ctrl;
		_stopped = false;
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		JToolBar mainToolBar = new JToolBar();
		add(mainToolBar);
		
		//FileChooser and FileButton
		fileChooser = new JFileChooser("Load File");
		fileChooser.setCurrentDirectory(new File("resources/examples"));
		
		fileButton = createButton("resources/icons/open.png", "Load a file to the simulation");
		fileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int v = fileChooser.showOpenDialog(null);
					if (v == JFileChooser.APPROVE_OPTION) {
						File newFile = fileChooser.getSelectedFile();
						ctrl.reset();
						ctrl.loadEvents(new FileInputStream(newFile));
					}
				} catch (FileNotFoundException exception) {
					JOptionPane.showMessageDialog(getParent(), exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mainToolBar.add(fileButton);
		
		
		//ContClass Button
		contClassButton = createButton("resources/icons/co2class.png", "Change the contamination class of one vehicle");
		contClassButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setCO2Vehicle();
			}
			
		});
		mainToolBar.add(contClassButton);
		
		//Weather Button
		weatherButton = createButton("resources/icons/weather.png", "Change the weather of a road");
		weatherButton.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				setNewWeather();
			}
			
		});
		mainToolBar.add(weatherButton);
		
		//Run Button
		runButton = createButton("resources/icons/run.png", "Run the simulation");
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enableToolBar(false);
				_stopped = false;
				run_sim((int) ticksSpinner.getValue());
			}
		});
		mainToolBar.add(runButton);
		
		//Stop button
		stopButton = createButton("resources/icons/stop.png", "Stop the simulation");
		stopButton.setEnabled(false);
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enableToolBar(true);
				stop();
			}
		});
		mainToolBar.add(stopButton);
		
		// Ticks Label
		JLabel ticksLabel = new JLabel("Ticks:");
		mainToolBar.add(ticksLabel);
		ticksSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
		mainToolBar.add(ticksSpinner);
		
		JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
		separator.setPreferredSize(new Dimension(100, 42));
		mainToolBar.add(separator);
		mainToolBar.add(Box.createHorizontalGlue());
		
		//Exit Button
		exitButton = createButton("resources/icons/exit.png", "Exit the simulation");
		exitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int exit = JOptionPane.showConfirmDialog(null, "Exit?", "Exit Simulation", JOptionPane.YES_NO_OPTION);
				if (exit == 0)
					System.exit(0);
				
			}
		});
		mainToolBar.add(exitButton);
	}
	
	private JButton createButton(String image, String info) {
		JButton newButton = new JButton(new ImageIcon(image));
		newButton.setHorizontalAlignment(JLabel.LEFT);
		newButton.setToolTipText(info);
		
		return newButton;
	}
	
	private void enableToolBar(boolean _stopped) {
		fileButton.setEnabled(_stopped);
		contClassButton.setEnabled(_stopped);
		weatherButton.setEnabled(_stopped);
		runButton.setEnabled(_stopped);
		stopButton.setEnabled(!_stopped);
		exitButton.setEnabled(_stopped);
	}
	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				ctrl.run(1);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(getParent(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() { run_sim(n - 1); }
			});
		} else {
			enableToolBar(true);
			_stopped = true;
		}
	}
	
	private void stop() { _stopped = true; }
	
	private void setCO2Vehicle() {
		if (co2ClassDialog == null)
			co2ClassDialog = new ChangeCO2ClassDialog();
		else
			co2ClassDialog.setVisible(true);
		if (co2ClassDialog.open(map.getVehicles()) == 1)
			ctrl.addEvent(new SetContClassEvent(co2ClassDialog.getTime(), co2ClassDialog.getNewCO2Vehicle()));
	}
	
	private void setNewWeather() {
		if (weatherDialog == null)
			weatherDialog = new ChangeWeatherDialog();
		else
			weatherDialog.setVisible(true);
		if (weatherDialog.open(map.getRoads()) == 1)
			ctrl.addEvent(new SetWeatherEvent(weatherDialog.getTime(), weatherDialog.getNewWeather()));
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.map = map;
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		ticksSpinner.setValue(time);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.map = map;
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
}
