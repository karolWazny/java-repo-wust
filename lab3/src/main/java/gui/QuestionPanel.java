package gui;

import lib.Question;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class QuestionPanel extends JPanel {
    private final static String bundleName = "resources.QuestionPanelBundle";
    protected Question question;

    private JPanel firstPanel;
    private JPanel secondPanel;
    private JPanel thirdPanel;

    private JLabel answerLabel;
    private JButton okButton;
    protected JTextField answerBox;

    private JLabel responseLabel;

    protected QuestionPanel(Question question){
        this.question = question;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        firstPanel = getQuestionContentPanel();
        add(firstPanel);
        secondPanel = createAnswerPanel();
        add(secondPanel);
        thirdPanel = createResponsePanel();
        add(thirdPanel);
    }

    public void setLocale(Locale l){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName, l);
        answerLabel.setText(resourceBundle.getString("answerLabel"));
        okButton.setText(resourceBundle.getString("confirmButton"));
        question.setLocale(l);
        responseLabel.setText(question.getAnswerComment());
        updateQuestionContentLanguage();
        invalidate();
    }

    protected abstract void updateQuestionContentLanguage();
    protected abstract JPanel getQuestionContentPanel();
    private JPanel createAnswerPanel(){
        JPanel panel = new JPanel();
        answerLabel = new JLabel("placeholder");
        panel.add(answerLabel);
        answerBox = new JTextField();
        answerBox.setMinimumSize(new Dimension(100, 30));
        answerBox.setSize(new Dimension(100, 30));
        panel.add(answerBox);
        okButton = new JButton("placeholder");
        okButton.addActionListener(action -> answerTheQuestion());
        panel.add(okButton);
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setMinimumSize(new Dimension(600, 30));
        panel.setSize(new Dimension(600, 30));
        panel.setMaximumSize(new Dimension(600, 30));
        return panel;
    }

    private void answerTheQuestion(){
        getParametersAndAnswerTheQuestion();
        responseLabel.setText(question.getAnswerComment());
    }

    protected abstract void getParametersAndAnswerTheQuestion();

    private JPanel createResponsePanel(){
        JPanel panel = new JPanel();
        responseLabel = new JLabel("");
        panel.add(responseLabel);
        return panel;
    }
}
