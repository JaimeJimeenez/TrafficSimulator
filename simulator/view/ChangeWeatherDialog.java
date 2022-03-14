package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.misc.Pair;
import simulator.model.Road;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private int status;
	
	private JComboBox<String> boxRoad;
	private JComboBox<Weather> boxWeather;
	private JButton cancelButton;
	private JButton okButton;
	private JSpinner ticksSpinner;
	
	ChangeWeatherDialog() {
		initGui();
	}
	
	private void initGui() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		add(mainPanel);
		
		// Description
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 3));
		mainPanel.add(topPanel);
		
		JLabel descLabel = new JLabel("Schedule an event to change the weather of a road after a given number o simulation ticks from now.");
		topPanel.add(descLabel);
		
		// Center Panel
		JPanel centerPanel = new JPanel();
		mainPanel.add(centerPanel);
		
		//	Roads
		JLabel roadsLabel = new JLabel("Road:");
		centerPanel.add(roadsLabel);
		
		boxRoad = new JComboBox<String>();
		boxRoad.setPreferredSize(new Dimension(50, 25));
		centerPanel.add(boxRoad);
		
		//	Weather
		JLabel weatherLabel = new JLabel("Weather:");
		centerPanel.add(weatherLabel);
		
		boxWeather = new JComboBox<Weather>();
		boxWeather.setPreferredSize(new Dimension(50, 25));
		
		centerPanel.add(boxWeather);
		
		JLabel ticksPanel = new JLabel("Ticks:");
		centerPanel.add(ticksPanel);
		ticksSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
		centerPanel.add(ticksSpinner);
		
		// Cancel and OK Buttons
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 3));
		mainPanel.add(bottomPanel);
		
		cancelButton = new JButton("Cancel");
		bottomPanel.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				status = 0;
				setVisible(false);
			}
			
		});
		
		okButton = new JButton("Ok");
		bottomPanel.add(okButton);
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				status = 1;
				setVisible(false);
			}
		});
		
		setName("Change Road Weather");
		pack();
		setVisible(true);
	}

	int open(List<Road> roads) {
		
		boxRoad.removeAllItems();
		for (Road r : roads)
			boxRoad.addItem(r.toString());
		
		boxWeather.removeAllItems();
		boxWeather.addItem(Weather.CLOUDY);
		boxWeather.addItem(Weather.RAINY);
		boxWeather.addItem(Weather.STORM);
		boxWeather.addItem(Weather.SUNNY);
		boxWeather.addItem(Weather.WINDY);
		
		return status;
	}
	
	List<Pair<String, Weather>> getNewWeather() {
		List<Pair<String, Weather>> data = new ArrayList<>();
		String r = (String) boxRoad.getSelectedItem().toString();
		Weather w = (Weather) boxWeather.getSelectedItem();
		data.add(new Pair<String, Weather>(r, w));
		return data;
	}
	
	int getTime() { return (int) ticksSpinner.getValue(); }
}
