package app;

import javax.swing.*;

public class BottomPanel extends JPanel {
    private JPanel leftPane;
    private JPanel rightPane;

    public BottomPanel(){
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        leftPane = new JPanel();
        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.PAGE_AXIS));

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));

        JButton previousButt = new JButton("Previous");
        JButton nextButt = new JButton("Next");

        buttons.add(previousButt);
        buttons.add(nextButt);

        leftPane.add(buttons);

        add(leftPane);
    }
}
