package lib.gui;

import lib.logic.ApplicationModel;
import lib.logic.RecordRenderer;

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

    private RecordRenderer recordRenderer = new DefaultRenderer();

    private ApplicationModel model;

    public BottomPanel(ApplicationModel model){
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.PAGE_AXIS));

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));

        JButton previousButt = new JButton("Previous");
        previousButt.addActionListener(action-> previousItem());
        JButton nextButt = new JButton("Next");
        nextButt.addActionListener(action -> nextItem());


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
        scrollPane.setMinimumSize(new Dimension(200, 100));
        scrollPane.setPreferredSize(new Dimension(200, 100));
        leftPane.add(scrollPane);

        rightPane.setMinimumSize(new Dimension(400, 500));
        rightPane.setPreferredSize(new Dimension(400, 500));
        rightPane.add(recordRenderer);

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
        int index = recordsView.getSelectedIndex();
        recordsView.setSelectedIndex(index - 1);
    }

    public void nextItem(){
        int index = recordsView.getSelectedIndex();
        recordsView.setSelectedIndex(index + 1);
    }
}
