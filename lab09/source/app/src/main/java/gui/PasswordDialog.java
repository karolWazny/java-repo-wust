package gui;

import javax.swing.*;

public class PasswordDialog {
    public static char[] show(){
        JPasswordField passwordField = new JPasswordField();

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));

        myPanel.add(new JLabel("Password:"));
        myPanel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter password", JOptionPane.OK_CANCEL_OPTION);
        return passwordField.getPassword();
    }
}
