package pl.edu.pwr.java.lab8.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import pl.edu.pwr.java.lab8.service.PersonService;

@Endpoint
@RequiredArgsConstructor
public class PeopleEndpoint {
    public final static String NAMESPACE_URI = "pl/pwr/java/lab8/soap/people";

    private final PersonService personService;
}
