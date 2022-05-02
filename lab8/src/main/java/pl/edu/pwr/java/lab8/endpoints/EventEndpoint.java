package pl.edu.pwr.java.lab8.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.edu.pwr.java.lab8.service.EventService;
import pl.pwr.java.lab8.soap.events.Event;
import pl.pwr.java.lab8.soap.events.FetchEventsRequest;
import pl.pwr.java.lab8.soap.events.FetchEventsResponse;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

@Endpoint
@RequiredArgsConstructor
public class EventEndpoint {
    public final static String NAMESPACE_URI = "pl/pwr/java/lab8/soap/events";

    private final EventService eventService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "fetchEventsRequest")
    @ResponsePayload
    public FetchEventsResponse fetchEvents(@RequestPayload FetchEventsRequest request) {
        FetchEventsResponse response = new FetchEventsResponse();
        response.getEvents().addAll(eventService.fetchEvents(request.getPage())
                .stream()
                .map(event -> {
                    Event output = new Event();
                    output.setId(event.getId());
                    GregorianCalendar c = new GregorianCalendar();
                    c.setTime(event.getDate());
                    try {
                        XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                        output.setDate(xmlDate);
                    } catch (DatatypeConfigurationException e) {
                        e.printStackTrace();
                    }
                    output.setName(event.getName());
                    output.setPlace(event.getPlace());
                    return output;
                })
                .collect(Collectors.toList()));
        return response;
    }
}
