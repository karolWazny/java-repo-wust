package com.pwr.java.lab3.lib;

import java.util.Objects;

public abstract class Question {
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource);
    }

    public abstract void setParameters(String[] parameters);
    public abstract String getQuestion();
    public abstract void answer(String answer);
    public abstract String getAnswerComment();
    public abstract boolean wasAnswerCorrect();
}
