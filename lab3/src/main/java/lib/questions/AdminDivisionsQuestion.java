package lib.questions;

import lib.Question;

import java.util.Locale;

public class AdminDivisionsQuestion extends Question {
    private final static String bundleName = "AdminDivisionsBundle";

    public AdminDivisionsQuestion() {
        super(bundleName);
    }

    @Override
    public void setParameters(String[] parameters) {

    }

    @Override
    public String getQuestion() {
        return resourceBundle.getString("greetings");
    }

    @Override
    public void answer(String answer) {

    }

    @Override
    public String getAnswerComment() {
        return null;
    }

    @Override
    public boolean wasAnswerCorrect() {
        return false;
    }

    public static void main(String[] args){
        Question question = new AdminDivisionsQuestion();
        System.out.println(question.getQuestion());
        question.setLocale(new Locale("en", "EN"));
        System.out.println(question.getQuestion());
    }
}
