package app;

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
    private List<QuestionPanel> questionPanels = new ArrayList<>();
    private String currentLanguage;
    private Locale currentLocale;

    private JButton languageButton;
    private JButton questionButton;

    private static Map<String, Locale> getLocales(){
        Map<String, Locale> output = new HashMap<>();
        output.put("polski", new Locale("pl", "PL"));
        output.put("english", new Locale("en", "EN"));
        return output;
    }

    public MainWindow() {
        super();

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        currentQuestionPanel = new CountriesOnContinentPanel();

        add(currentQuestionPanel);
        add(createBottomPanel());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        setMinimumSize(new Dimension(600, 160));

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

        invalidate();
    }

    private JPanel createBottomPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        languageButton = new JButton("dupa");
        languageButton.addActionListener(action->switchLanguage());
        panel.add(languageButton);
        questionButton = new JButton("piwo");
        panel.add(questionButton);
        return panel;
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
