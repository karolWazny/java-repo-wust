package com.lib.gui;

import com.lib.logic.ApplicationModel;
import com.lib.logic.Record;
import com.lib.logic.observer.DefaultEvent;
import com.lib.logic.observer.Listener;
import com.lib.logic.observer.Observable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class BottomPanel extends JPanel implements Observable {
    private final JList<String> recordsView = new JList<>();
    private final List<Listener> listeners = new LinkedList<>();

    private final RecordRenderer recordRenderer = new DefaultRenderer();

    private final ApplicationModel model;

    public BottomPanel(ApplicationModel model){
        this.model = model;
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        JPanel leftPane = new JPanel();
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
                updateRecordView();
        });

        JScrollPane scrollPane = new JScrollPane(recordsView);
        scrollPane.setMinimumSize(new Dimension(300, 100));
        scrollPane.setMaximumSize(new Dimension(380, 1000));
        scrollPane.setPreferredSize(new Dimension(270, 100));
        leftPane.add(scrollPane);

        /*rightPane.setMinimumSize(new Dimension(400, 500));
        rightPane.setPreferredSize(new Dimension(400, 500));*/
        JPanel rightPane = new JPanel();
        rightPane.add(recordRenderer);

        add(leftPane);
        add(rightPane);

        /*setPreferredSize(new Dimension(900, 500));
        setMinimumSize(new Dimension(900, 500));*/
    }

    private void updateRecordView(){
        int index = recordsView.getSelectedIndex();
        ListModel<String> model = recordsView.getModel();
        Record record = this.model.getRecord(model.getElementAt(index));
        recordRenderer.setRecord(record);

        setSize(new Dimension(300 + recordRenderer.getWidth(), -1));
        revalidate();
        for (Listener listener:
             listeners) {
            listener.onEventHappened(new DefaultEvent(this));
        }
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
        try{
            recordsView.setSelectedIndex(index - 1);
        } catch (IndexOutOfBoundsException ignored){}
    }

    public void nextItem(){
        int index = recordsView.getSelectedIndex();
        recordsView.setSelectedIndex(index + 1);
    }

    @Override
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }
}
