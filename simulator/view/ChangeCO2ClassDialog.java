package simulator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class ChangeCO2ClassDialog extends JDialog {
    
    private static final long serialVersionUID = 1L;

    private JSpinner ticksSpinner;

    private JButton cancelButton;
    private JButton okButton;

    ChangeCO2ClassDialog() {
        initGui();
    }

    private void initGui() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);

        // Description
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 3));
        mainPanel.add(topPanel);

        JLabel descLabel = new JLabel("Schedule an event to change the CO2 of a vehicle after a given number o simulation ticks from now");
        topPanel.add(descLabel);

        // Vehicles CO2 class and Ticks
        JPanel centerPanel = new JPanel();
        mainPanel.add(centerPanel);

        JLabel ticksLabel = new JLabel("Ticks: ");
        centerPanel.add(ticksLabel);
        ticksSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        centerPanel.add(ticksSpinner);

        // Cancel  and Ok Buttons
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

        pack();
        setName("Change CO2 class");

    }
}
