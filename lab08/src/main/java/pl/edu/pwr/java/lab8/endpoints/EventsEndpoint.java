package pl.edu.pwr.java.lab8.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.edu.pwr.java.lab8.model.entity.Event;
import pl.edu.pwr.java.lab8.model.mapper.soap.EventMapper;
import pl.edu.pwr.java.lab8.service.EventService;
import pl.pwr.java.lab8.soap.*;

import java.util.stream.Collectors;

@Endpoint
@RequiredArgsConstructor
public class EventsEndpoint {
    public static final String NAMESPACE_URI = "pl/pwr/java/lab8/soap";

    private final EventService eventService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "fetchEventsRequest")
    @ResponsePayload
    public FetchEventsResponse fetchEvents(@RequestPayload FetchEventsRequest request) {
        FetchEventsResponse response = new FetchEventsResponse();
        response.getEvents().addAll(eventService.fetchEvents(request.getPage())
                .stream()
                .map(EventMapper::map)
                .collect(Collectors.toList()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "fetchFutureEventsRequest")
    @ResponsePayload
    public FetchEventsResponse fetchEvents(@RequestPayload FetchFutureEventsRequest request) {
        FetchEventsResponse response = new FetchEventsResponse();
        response.getEvents().addAll(eventService.fetchFutureEvents()
                .stream()
                .map(EventMapper::map)
                .collect(Collectors.toList()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "fetchAllEventsRequest")
    @ResponsePayload
    public FetchEventsResponse fetchEvents(@RequestPayload FetchAllEventsRequest request) {
        FetchEventsResponse response = new FetchEventsResponse();
        response.getEvents().addAll(eventService.fetchAllEvents()
                .stream()
                .map(EventMapper::map)
                .collect(Collectors.toList()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createEventRequest")
    @ResponsePayload
    public CreateEventResponse fetchEvents(@RequestPayload CreateEventRequest request) {
        CreateEventResponse response = new CreateEventResponse();
        Event event = EventMapper.map(request.getEvent());
        event = eventService.createEvent(event);
        response.setEvent(EventMapper.map(event));
        return response;
    }
}
