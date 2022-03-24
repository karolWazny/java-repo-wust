package gui;

import lib.questions.CountriesOnContinentQuestion;

import javax.swing.*;

public class CountriesOnContinentPanel extends QuestionPanel{
    public CountriesOnContinentPanel(){
        super(new CountriesOnContinentQuestion());
    }

    @Override
    protected JPanel getQuestionContentPanel() {
        JPanel panel = new JPanel();
        panel.add(new JButton("For nudes click here!"));
        return panel;
    }
}
