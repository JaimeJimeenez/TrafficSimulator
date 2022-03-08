package simulator.view;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class ChangeWeatherDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private JSpinner ticksSpinner;

    private JButton cancelButton;
    private JButton okButton;

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

        JLabel descLabel = new JLabel("Schedule an event to change the weather of a road after a given number o simulation ticks from now");
        topPanel.add(descLabel);
        
        // Roads Weather and Ticks
        JPanel centerPanel = new JPanel();
        mainPanel.add(centerPanel);

        JLabel roadsLabel = new JLabel("Road");
        centerPanel.add(roadsLabel);

        JLabel weatherLabel = new JLabel("Weather:");
        centerPanel.add(weatherLabel);

        JLabel ticksPanel = new JLabel("Ticks:");
        centerPanel.add(ticksPanel);
        ticksSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        centerPanel.add(ticksSpinner);

        // Cancel and Ok Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 3));
        mainPanel.add(bottomPanel);

        cancelButton = new JButton("Cancel");
        bottomPanel.add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        okButton = new JButton("Ok");
        bottomPanel.add(okButton);
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
        setName("Change Road Weather");
        pack();
        setVisible(true);
    }
}