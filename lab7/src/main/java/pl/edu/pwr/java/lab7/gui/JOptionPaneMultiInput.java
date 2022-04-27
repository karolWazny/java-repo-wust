package pl.edu.pwr.java.lab7.gui;

import pl.edu.pwr.java.lab7.model.entity.Person;

public class JOptionPaneMultiInput {
    public static void main(String[] args) {
        Person person = CreatePersonDialogShower.show();
        System.out.println(person.getFirstName());
        System.out.println(person.getLastName());
    }
}