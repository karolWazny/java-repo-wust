package pl.edu.pwr.java.lab7.gui;

import pl.edu.pwr.java.lab7.model.entity.Event;
import pl.edu.pwr.java.lab7.model.entity.Payment;
import pl.edu.pwr.java.lab7.model.entity.Person;

import javax.swing.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class CreatePaymentDialog {
    public static Payment show(List<Person> people, List<Event> events){
        JTextField amountField = new JTextField(5);
        JTextField installmentNumberField = new JTextField(5);
        JComboBox<Event> eventBox = new JComboBox<>(events.toArray(new Event[0]));
        JComboBox<Person> personBox = new JComboBox<>(people.toArray(new Person[0]));

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));

        myPanel.add(new JLabel("Amount:"));
        myPanel.add(amountField);
        myPanel.add(new JLabel("Installment number:"));
        myPanel.add(installmentNumberField);
        myPanel.add(new JLabel("Event:"));
        myPanel.add(eventBox);
        myPanel.add(new JLabel("Person paying:"));
        myPanel.add(personBox);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter payment details", JOptionPane.OK_CANCEL_OPTION);
        Payment output = null;
        if (result == JOptionPane.OK_OPTION) {
            try{
                Integer amount = Integer.parseInt(amountField.getText().trim());
                Integer installmentNumber = Integer.parseInt(installmentNumberField.getText().trim());
                output = new Payment( null, Date.valueOf(LocalDate.now()), amount, (Person)personBox.getSelectedItem(), (Event)eventBox.getSelectedItem(), installmentNumber);
            } catch(NullPointerException | StringIndexOutOfBoundsException ignored){}
        }
        return output;
    }
}
