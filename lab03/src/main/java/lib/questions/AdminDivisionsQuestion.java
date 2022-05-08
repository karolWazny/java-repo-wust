package lib.questions;

import lib.Question;
import lib.models.Territory;

import java.text.MessageFormat;
import java.util.Locale;

public class AdminDivisionsQuestion extends Question {
    private final static String bundleName = "AdminDivisionsBundle";
    private Territory country;
    private Integer correctAnswer;

    public AdminDivisionsQuestion() {
        super(bundleName);
    }

    @Override
    public void setParameters(Object[] parameters) {
        country = (Territory) parameters[0];
        correctAnswer = dataSource.getAdminDivisions(country)
                .size();
    }

    @Override
    public String getQuestion() {
        return resourceBundle.getString("greetings");
    }

    @Override
    public String getAnswerComment() {
        if(answer == null || answer.trim().equals(""))
            return "";
        if(answerWasCorrect()){
            return resourceBundle.getString("correct");
        } else {
            return MessageFormat.format(
                    resourceBundle.getString("incorrect"),
                    correctAnswer);
        }
    }

    @Override
    public boolean answerWasCorrect() {
        try{
            return correctAnswer.equals(Integer.parseInt(answer));
        } catch (NumberFormatException e){
            return false;
        }
    }

    public static void main(String[] args){
        Question question = new AdminDivisionsQuestion();
        System.out.println(question.getQuestion());
        question.setLocale(new Locale("en", "EN"));
        System.out.println(question.getQuestion());
    }
}
