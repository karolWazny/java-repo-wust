package com.pwr.java.lab3.lib;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DataSourceImpl implements DataSource{
    @Override
    public String[] getContinents() {
        return new String[0];
    }

    @Override
    public String[] getCountries(String continent) {
        return new String[0];
    }

    @Override
    public String[] getAdminDivisions(String country) {
        return new String[0];
    }

    public static void main(String[] args) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.teleport.org/api/continents/"))
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
