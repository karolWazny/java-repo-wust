package com.pwr.java.lab3.lib;

import com.pwr.java.lab3.lib.DataSource;

import java.util.Locale;
import java.util.Objects;

public abstract class Question {
    protected DataSource dataSource;
    private Locale locale;
    private final String bundleName;

    public Question(String bundleName) {
        this.bundleName = bundleName;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource);
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public abstract void setParameters(String[] parameters);
    public abstract String getQuestion();
    public abstract void answer(String answer);
    public abstract String getAnswerComment();
    public abstract boolean wasAnswerCorrect();
}
