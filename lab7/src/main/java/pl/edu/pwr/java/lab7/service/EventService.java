package pl.edu.pwr.java.lab7.service;

import pl.edu.pwr.java.lab7.model.entity.Event;

import java.util.List;

public interface EventService {
    List<Event> fetchEvents(int page);
    void createEvent(Event event);
}
