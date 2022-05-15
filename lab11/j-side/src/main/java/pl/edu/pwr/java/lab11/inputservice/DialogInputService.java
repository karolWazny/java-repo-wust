package pl.edu.pwr.java.lab11.inputservice;

import pl.edu.pwr.java.lab11.Order;

import javax.swing.*;
import java.util.Arrays;

public class DialogInputService extends UserInputService {
    @Override
    public Data getInput() {
        JTextField valuesField = new JTextField();
        JComboBox<Order> orderBox = new JComboBox<>(Order.values());

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));

        myPanel.add(new JLabel("Values:"));
        myPanel.add(valuesField);
        myPanel.add(new JLabel("Order:"));
        myPanel.add(orderBox);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter password", JOptionPane.OK_CANCEL_OPTION);
        Data output = null;
        if (result == JOptionPane.OK_OPTION) {
            try{
                output = new Data();
                output.values = Arrays.stream(valuesField.getText().split(","))
                        .map((str) -> Double.parseDouble(str.trim())).toArray(Double[]::new);
                output.order = (Order) orderBox.getSelectedItem();
            } catch(NullPointerException | StringIndexOutOfBoundsException ignored){}
        }
        return output;
    }
}
