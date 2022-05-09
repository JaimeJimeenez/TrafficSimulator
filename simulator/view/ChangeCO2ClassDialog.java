package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.misc.Pair;
import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private boolean status;
	
	private JComboBox<String> boxVehicles;
	private JComboBox<Integer> boxCO2;
	private JSpinner ticksSpinner;
	private JButton cancelButton;
	private JButton okButton;

	ChangeCO2ClassDialog(Frame frame) {
		super(frame, true);
		initGui();
	}
	
	private void initGui()  {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		add(mainPanel);
		
		//Description;
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 3));
		mainPanel.add(topPanel);
		
		JLabel descLabel = new JLabel("Schedule an event to change the CO2 class of a vehicle after a given number o simulation ticks from now.");
		topPanel.add(descLabel);
		
		// Vehicles
		JPanel centerPanel = new JPanel();
		mainPanel.add(centerPanel);
		
		JLabel vehiclesLabel = new JLabel("Vehicle:");
		centerPanel.add(vehiclesLabel);
		
		boxVehicles = new JComboBox<String>();
		boxVehicles.setPreferredSize(new Dimension(50, 25));
		centerPanel.add(boxVehicles);
		
		// CO2 
		JLabel co2Label = new JLabel("CO2 Class:");
		centerPanel.add(co2Label);
		
		boxCO2 = new JComboBox<Integer>();
		boxCO2.setPreferredSize(new Dimension(50, 25));
		for (int i = 0; i < 10; i++)
			boxCO2.addItem(i);
		centerPanel.add(boxCO2);
		
		// Ticks
		JLabel ticksLabel = new JLabel("Ticks:");
		centerPanel.add(ticksLabel);
		ticksSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
		centerPanel.add(ticksSpinner);
		
		
		//Cancel and Ok Buttons
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
				if (boxVehicles.getSelectedItem() != null && boxCO2.getSelectedItem() != null)
					status = true;
				setVisible(false);
			}
		});
		
		pack();
		setName("Change CO2 Class");
		setLocationRelativeTo(null);
	}
	
	boolean open(List<Vehicle> vehicles) {
		
		boxVehicles.removeAllItems();
		for (Vehicle v : vehicles)
			boxVehicles.addItem(v.toString());
		
		setVisible(true);
		return status;
	}
	
	List<Pair<String, Integer>> getNewCO2Vehicle() {
		List<Pair<String, Integer>> data = new ArrayList<>();
		String v = (String) boxVehicles.getSelectedItem().toString();
		Integer co2 = (Integer) boxCO2.getSelectedItem();
		data.add(new Pair<String, Integer>(v, co2));
		return data;
	}
	
	int getTime() { return (int) ticksSpinner.getValue(); }
	
}
