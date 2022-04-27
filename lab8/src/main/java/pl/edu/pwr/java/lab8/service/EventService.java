package pl.edu.pwr.java.lab8.service;

import pl.edu.pwr.java.lab8.model.entity.Event;

import java.util.List;

public interface EventService {
    List<Event> fetchEvents(int page);
    void createEvent(Event event);
    List<Event> fetchFutureEvents();
    List<Event> fetchAllEvents();
}
