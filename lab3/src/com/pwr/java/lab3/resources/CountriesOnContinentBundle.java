package com.pwr.java.lab3.resources;

import java.util.ListResourceBundle;

public class CountriesOnContinentBundle extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"greetings", "hello there C"}
        };
    }
}
