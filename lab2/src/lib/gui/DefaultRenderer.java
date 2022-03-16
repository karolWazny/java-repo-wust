package lib.gui;

import lib.logic.Record;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DefaultRenderer extends RecordRenderer {

    private Record record;

    private JTextField name = uneditableTextField();
    private JTextField lastName = uneditableTextField();
    private JTextField email = uneditableTextField();
    private JTextField birthDate = uneditableTextField();
    private ImagePanel imagePanel = defaultImageRenderer();
    private Dimension dimension = new Dimension(100, 100);

    public DefaultRenderer(){
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.PAGE_AXIS));
        text.add(new JLabel("First name"));
        text.add(name);
        text.add(new JLabel("Last name"));
        text.add(lastName);
        text.add(new JLabel("E-mail address"));
        text.add(email);
        text.add(new JLabel("Birth date"));
        text.add(birthDate);
        text.add(new JPanel());
        add(text);
        add(imagePanel);
        //setMinimumSize(new Dimension(400, 300));
        //setPreferredSize(new Dimension(400, 300));
    }

    private static ImagePanel defaultImageRenderer(){
        OriginalRatioImagePanel panel = new OriginalRatioImagePanel();
        panel.setHeight(210);

        /*DefaultImagePanel panel = new DefaultImagePanel();
        panel.setDimension(new Dimension(130, 210));*/

        return panel;
    }

    private static JTextField uneditableTextField(){
        JTextField output = new JTextField();
        output.setEditable(false);
        output.setMaximumSize(new Dimension(3000, 30));
        return output;
    }

    @Override
    public void setRecord(Record record) {
        this.record = record;
        updateControls();
        revalidate();
    }

    private void updateControls(){
        name.setText(record.getFirstName());
        lastName.setText(record.getLastName());
        email.setText(record.getEmail());
        birthDate.setText(record.getBirthDate().format(formatter()));

        imagePanel.setImage(record.getImage());
        setDimension(new Dimension(imagePanel.getWidth() + 200, imagePanel.getHeight()));
    }

    private void setDimension(Dimension dimension){
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);
        setSize(dimension);
    }

    private DateTimeFormatter formatter(){
        return DateTimeFormatter.ofPattern("dd-LLL-yyyy", Locale.US);
    }
}
