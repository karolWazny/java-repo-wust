package lib;

import lib.models.Territory;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonStructure;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DataSourceImpl implements DataSource{
    private final static String api = "https://api.teleport.org/api/";

    @Override
    public List<Territory> getContinents() {
        JsonStructure jsonStructure = getJsonFromEndpoint(api + "continents/");
        return jsonStructure.getValue("/_links/continent:items")
                .asJsonArray()
                .stream()
                .map(item-> new Territory(item.asJsonObject().getString("name"),
                        codeFromHref(item.asJsonObject().getString("href"))))
                .collect(Collectors.toList());
    }

    @Override
    public List<Territory> getCountries(Territory continent) {
        String endpoint = "countries/";
        if(continent != null){
            endpoint = "continents/" + continent.getGeonameCode() + "/" + endpoint;
        }
        return jsonToCountries(
                getJsonFromEndpoint(api + endpoint)
        );
    }

    @Override
    public List<Territory> getCountries(){
        return getCountries(null);
    }

    private List<Territory> jsonToCountries(JsonStructure json){
        return json.getValue("/_links/country:items")
                .asJsonArray()
                .stream()
                .map(item-> new Territory(item.asJsonObject().getString("name"),
                        codeFromHref(item.asJsonObject().getString("href"))))
                .collect(Collectors.toList());
    }

    private String codeFromHref(String href){
        String[] elements = href.split("/");
        return elements[elements.length - 1];
    }

    @Override
    public String[] getAdminDivisions(String country) {
        return new String[0];
    }

    private String getStringFromEndpoint(String endpoint){
        try {
            HttpRequest request;
            request = HttpRequest.newBuilder()
                    .uri(new URI(endpoint))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = null;
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException | URISyntaxException ignored) {
            //e.printStackTrace();
        }
        throw new RuntimeException("Problem accessing remote API endpoints...");
    }
    private JsonStructure getJsonFromEndpoint(String endpoint){
        String jsonString = getStringFromEndpoint(endpoint);
        JsonReaderFactory readerFactory = Json.createReaderFactory(Collections.emptyMap());
        JsonReader jsonReader = readerFactory.createReader(new ByteArrayInputStream(jsonString.getBytes()));
        return jsonReader.read();
    }

    public static void main(String[] args) throws Exception {
        new DataSourceImpl().getContinents()
                .forEach(continent->{
                    System.out.println("CONTINENT: " + continent);
                    new DataSourceImpl().getCountries(continent)
                            .forEach(System.out::println);
                });
    }
}
