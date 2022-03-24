package lib.questions;

import lib.Question;
import lib.models.Territory;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;

public class CountriesOnContinentQuestion extends Question {
    private final static String bundleName = "resources.CountriesOnContinentBundle";
    private Territory continent;
    private Integer correctAnswer;

    public CountriesOnContinentQuestion(){
        super(bundleName);
    }

    @Override
    public void setParameters(Object[] parameters) {
        continent = (Territory) parameters[0];
        correctAnswer = dataSource.getCountries(continent).size();
    }

    @Override
    public String getQuestion() {
        String questionTemplate = resourceBundle.getString("question");
        return MessageFormat.format(questionTemplate, continent);
    }

    public List<Territory> possibleContinents(){
        return dataSource.getContinents();
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
        Question question = new CountriesOnContinentQuestion();
        System.out.println(question.getQuestion());
        question.setLocale(new Locale("en", "EN"));
        System.out.println(question.getQuestion());
    }
}
