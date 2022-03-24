package lib.questions;

import lib.Question;
import lib.models.Territory;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;

public class CountriesOnContinentQuestion extends Question {
    private final static String bundleName = "resources.CountriesOnContinentBundle";
    private Territory continent = new Territory("South America", null);

    public CountriesOnContinentQuestion(){
        super(bundleName);
    }

    @Override
    public void setParameters(Object[] parameters) {

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
        return null;
    }

    @Override
    public boolean wasAnswerCorrect() {
        return dataSource.getCountries(continent).size() == Integer.parseInt(answer);
    }

    public static void main(String[] args){
        Question question = new CountriesOnContinentQuestion();
        System.out.println(question.getQuestion());
        question.setLocale(new Locale("en", "EN"));
        System.out.println(question.getQuestion());
    }
}
