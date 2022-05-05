package lib;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public abstract class Question {
    protected DataSource dataSource;
    private Locale locale;
    private final String bundleName;
    protected ResourceBundle resourceBundle;
    protected String answer;

    public Question(String bundleName) {
        this.bundleName = bundleName;
        setLocale(defaultLocale());
    }

    protected Locale defaultLocale(){
        return new Locale("pl", "PL");
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource);
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        resourceBundle = ResourceBundle.getBundle(bundleName, locale);
    }

    public abstract void setParameters(Object[] parameters);

    public abstract String getQuestion();
    public void answer(String answer){
        this.answer = answer;
    }
    public abstract String getAnswerComment();
    public abstract boolean answerWasCorrect();
}
