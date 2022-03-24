package app;

import gui.AdminDivisionsPanel;
import gui.CountriesOnContinentPanel;
import gui.QuestionPanel;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.prefs.Preferences;

public class MainWindow extends JFrame {
    private final static String bundleName = "resources.MainWindowBundle";
    private final static Map<String, Locale> locales = getLocales();

    private QuestionPanel currentQuestionPanel;
    private List<QuestionPanel> questionPanels = acquireQuestionPanels();
    private String currentLanguage;
    private Locale currentLocale;

    private JButton languageButton;
    private JButton questionButton;

    private JPanel topPanel = new JPanel();

    private int currentQuestionIndex = 0;

    private static List<QuestionPanel> acquireQuestionPanels(){
        List<QuestionPanel> questionPanels = new ArrayList<>(2);
        questionPanels.add(0, new CountriesOnContinentPanel());
        questionPanels.add(1, new AdminDivisionsPanel());
        return questionPanels;
    }
    private static Map<String, Locale> getLocales(){
        Map<String, Locale> output = new HashMap<>();
        output.put("polski", new Locale("pl", "PL"));
        output.put("english", new Locale("en", "EN"));
        return output;
    }

    public MainWindow() {
        super();

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        currentQuestionPanel = questionPanels.get(currentQuestionIndex);

        topPanel.add(currentQuestionPanel);
        add(topPanel);
        add(createBottomPanel());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        setMinimumSize(new Dimension(680, 160));

        loadLanguage();
    }

    private void loadLanguage(){
        Preferences prefs = Preferences.userNodeForPackage(MainWindow.class);
        currentLanguage = prefs.get("language", "english");
        currentLocale = locales.get(currentLanguage);
        updateControlsLanguage();
    }

    private void setLanguage(String language){
        currentLanguage = language;
        currentLocale = locales.get(language);
        Preferences prefs = Preferences.userNodeForPackage(MainWindow.class);
        prefs.get("language", "english");
        prefs.put("language", language);
        updateControlsLanguage();
    }

    private void updateControlsLanguage(){
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, currentLocale);

        setTitle(bundle.getString("title"));
        languageButton.setText(bundle.getString("languageButton"));
        questionButton.setText(bundle.getString("questionButton"));

        try {
            currentQuestionPanel.setLocale(currentLocale);
        } catch (Exception ignored) {

        }
        repaint();
        invalidate();
    }

    private JPanel createBottomPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        languageButton = new JButton("dupa");
        languageButton.addActionListener(action->switchLanguage());
        panel.add(languageButton);
        questionButton = new JButton("piwo");
        questionButton.addActionListener(action -> changeQuestion());
        panel.add(questionButton);
        return panel;
    }

    private void changeQuestion(){
        currentQuestionIndex = (currentQuestionIndex + 1) % 2;
        topPanel.remove(0);
        currentQuestionPanel = questionPanels.get(currentQuestionIndex);
        topPanel.add(currentQuestionPanel);
        topPanel.invalidate();
        updateControlsLanguage();
    }

    private void switchLanguage(){
        if(currentLanguage.equals("english"))
            setLanguage("polski");
        else
            setLanguage("english");
    }

    public static void main(String[] args){
        new MainWindow();
    }
}
