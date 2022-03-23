package lib;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonStructure;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;

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
        String jsonString = response.body();
        JsonReaderFactory readerFactory = Json.createReaderFactory(Collections.emptyMap());
        try (JsonReader jsonReader = readerFactory.createReader(new ByteArrayInputStream(jsonString.getBytes()))) {
            JsonStructure jsonStructure = jsonReader.read();
            System.out.println(jsonStructure.getValue("/_links/continent:items/0"));
        }
        System.out.println(jsonString);
    }
}
