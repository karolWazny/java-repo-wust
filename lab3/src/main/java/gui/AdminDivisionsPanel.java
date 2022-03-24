package gui;

import lib.DataSource;
import lib.DataSourceImpl;
import lib.models.Territory;
import lib.questions.AdminDivisionsQuestion;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class AdminDivisionsPanel extends QuestionPanel{
    private static final String bundleName = "resources.AdminDivisionsBundle";

    private JLabel questionContent;
    private JComboBox<Territory> continentsBox;

    public AdminDivisionsPanel() {
        super(new AdminDivisionsQuestion());
        DataSource dataSource = new DataSourceImpl();
        question.setDataSource(dataSource);
        DefaultComboBoxModel<Territory> model = new DefaultComboBoxModel<>();
        model.addAll(dataSource.getCountries());
        continentsBox.setModel(model);
        continentsBox.setSelectedIndex(0);
    }

    @Override
    protected void updateQuestionContentLanguage() {
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName,
                question.getLocale());
        questionContent.setText(bundle.getString("question"));
        invalidate();
    }

    @Override
    protected JPanel getQuestionContentPanel() {
        JPanel panel = new JPanel();
        questionContent = new JLabel("Lorem ipsum");
        panel.add(questionContent);
        continentsBox = new JComboBox<>();
        continentsBox.setMaximumSize(new Dimension(290, 30));
        panel.add(continentsBox);
        panel.add(new JLabel("?"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        return panel;
    }

    @Override
    protected void getParametersAndAnswerTheQuestion() {
        question.setParameters(new Object[]{continentsBox.getSelectedItem()});
        question.answer(answerBox.getText());
    }
}
