package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
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
	
	private boolean status;
	
	private final Weather[] dataWeather = { Weather.CLOUDY, Weather.RAINY, Weather.STORM, Weather.SUNNY, Weather.WINDY };
	private JComboBox<String> boxRoad;
	private JComboBox<Weather> boxWeather;
	private JButton cancelButton;
	private JButton okButton;
	private JSpinner ticksSpinner;
	
	ChangeWeatherDialog(Frame frame) {
		super(frame, true);
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
		
		boxWeather = new JComboBox<Weather>(dataWeather);
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
				status = false;
				setVisible(false);
			}
			
		});
		
		okButton = new JButton("Ok");
		bottomPanel.add(okButton);
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (boxRoad.getSelectedItem() != null && boxWeather.getSelectedItem() != null)
						status = true;
				setVisible(false);
				
			}
		});
		
		setName("Change Road Weather");
		pack();
		setLocationRelativeTo(null);
	}

	boolean open(List<Road> roads) {
		
		boxRoad.removeAllItems();
		for (Road r : roads)
			boxRoad.addItem(r.toString());
		
		setVisible(true);
		
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
