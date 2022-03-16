package lib.gui;

import lib.logic.ApplicationModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BottomPanel extends JPanel {
    private JPanel leftPane = new JPanel();
    private JPanel rightPane = new JPanel();
    private JList<String> recordsView = new JList<>();

    private ApplicationModel model;

    public BottomPanel(ApplicationModel model){
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.PAGE_AXIS));

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));

        JButton previousButt = new JButton("Previous");
        JButton nextButt = new JButton("Next");

        buttons.add(previousButt);
        buttons.add(nextButt);

        leftPane.add(buttons);

        recordsView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recordsView.setLayoutOrientation(JList.VERTICAL);

        recordsView.addListSelectionListener((ListSelectionEvent event) -> {
            if(!event.getValueIsAdjusting())
                System.out.println("Dupa: " + event);
        });

        JScrollPane scrollPane = new JScrollPane(recordsView);
        scrollPane.setMinimumSize(new Dimension(100, 100));
        leftPane.add(scrollPane);

        rightPane.setMinimumSize(new Dimension(800, 500));

        add(leftPane);
        add(rightPane);
    }

    public void setRecords(List<String> records){
        DefaultListModel<String> newModel = new DefaultListModel<>();
        newModel.addAll(records);
        recordsView.setModel(newModel);
        recordsView.setSelectedIndex(0);
    }

    public void setRecords(String[] records){
        setRecords(Arrays.stream(records)
                .collect(Collectors.toList()));
    }

    public void previousItem(){

    }

    public void nextItem(){

    }
}
