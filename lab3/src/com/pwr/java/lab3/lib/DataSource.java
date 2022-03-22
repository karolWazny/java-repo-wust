package com.pwr.java.lab3.lib;

public interface DataSource {
    String[] getContinents();
    String[] getCountries(String continent);
    String[] getAdminDivisions(String country);
}
