package pl.edu.pwr.java.lab8.gui;

import pl.edu.pwr.java.lab8.model.entity.Event;
import pl.edu.pwr.java.lab8.model.entity.Installment;

import javax.swing.*;
import java.sql.Date;
import java.util.List;

public class CreateInstallmentDialog {
    public static Installment show(List<Event> events){
        JComboBox<Event> eventBox = new JComboBox<>(events.toArray(new Event[0]));
        JTextField amountField = new JTextField();
        JTextField dateField = new JTextField();

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));

        myPanel.add(new JLabel("Event:"));
        myPanel.add(eventBox);
        myPanel.add(new JLabel("Amount:"));
        myPanel.add(amountField);
        myPanel.add(new JLabel("Date:"));
        myPanel.add(dateField);


        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter installment details", JOptionPane.OK_CANCEL_OPTION);
        Installment output = null;
        if (result == JOptionPane.OK_OPTION) {
            try{
                Event event = (Event) eventBox.getSelectedItem();
                Integer amount = Integer.parseInt(amountField.getText().trim());
                Date date = Date.valueOf(dateField.getText().trim());
                if(event != null)
                    output = new Installment(null, event, null, date, amount);
            } catch(NullPointerException | StringIndexOutOfBoundsException | NumberFormatException ignored){}
        }
        return output;
    }
}
