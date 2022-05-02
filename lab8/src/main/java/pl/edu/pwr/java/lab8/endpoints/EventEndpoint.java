package pl.edu.pwr.java.lab8.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import pl.edu.pwr.java.lab8.repository.EventRepository;

@Endpoint
@RequiredArgsConstructor
public class EventEndpoint {
    private final EventRepository eventRepository;


}
