package pl.edu.pwr.java.lab7.gui;

import pl.edu.pwr.java.lab7.model.entity.Installment;

import javax.swing.*;
import java.sql.Date;

public class CreateInstallmentDialog {
    public static Installment show(){
        JTextField nameField = new JTextField();
        JTextField placeField = new JTextField();
        JTextField dateField = new JTextField();

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));

        myPanel.add(new JLabel("Name:"));
        myPanel.add(nameField);
        myPanel.add(new JLabel("Place:"));
        myPanel.add(placeField);
        myPanel.add(new JLabel("Date:"));
        myPanel.add(dateField);


        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter event details", JOptionPane.OK_CANCEL_OPTION);
        Installment output = null;
        if (result == JOptionPane.OK_OPTION) {
            try{
                String name = nameField.getText().trim();
                String place = placeField.getText().trim();
                Date date = Date.valueOf(dateField.getText().trim());
                /*if(!name.equals("") && !place.equals(""))
                    output = new Event(null, name, place, date);*/
            } catch(NullPointerException | StringIndexOutOfBoundsException ignored){}
        }
        return output;
    }
}
