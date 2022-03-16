package lib.gui;

import lib.logic.Record;

import javax.swing.*;

public abstract class RecordRenderer extends JPanel {
    public abstract void setRecord(Record record);
}
