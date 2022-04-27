package pl.edu.pwr.java.lab8.gui;

import pl.edu.pwr.java.lab8.model.entity.Person;

import javax.swing.*;
import java.util.Locale;

public class CreatePersonDialogShower {
    public static Person show(){
        JTextField firstNameField = new JTextField(5);
        JTextField lastNameField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));

        myPanel.add(new JLabel("First name:"));
        myPanel.add(firstNameField);
        myPanel.add(new JLabel("Last name:"));
        myPanel.add(lastNameField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter first and last name", JOptionPane.OK_CANCEL_OPTION);
        Person output = null;
        if (result == JOptionPane.OK_OPTION) {
            try{
                String firstName = firstNameField.getText().trim();
                firstName = firstName.substring(0, 1).toUpperCase(Locale.ROOT) + firstName.substring(1).toLowerCase(Locale.ROOT);
                String lastName = lastNameField.getText().trim();
                lastName = lastName.substring(0, 1).toUpperCase(Locale.ROOT) + lastName.substring(1).toLowerCase(Locale.ROOT);
                if(!firstName.equals("") && !lastName.equals(""))
                    output = new Person(null, firstName, lastName);
            } catch(NullPointerException | StringIndexOutOfBoundsException ignored){}
        }
        return output;
    }
}
