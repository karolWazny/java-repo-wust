package com.pwr.java.lab3.lib.questions;

import com.pwr.java.lab3.lib.Question;

import java.util.Locale;

public class CountriesOnContinentQuestion extends Question {
    public CountriesOnContinentQuestion(){
        Locale locale = new Locale("pl", "PL");
        setLocale(locale);
    }

    @Override
    public void setParameters(String[] parameters) {

    }

    @Override
    public String getQuestion() {
        return null;
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
}
