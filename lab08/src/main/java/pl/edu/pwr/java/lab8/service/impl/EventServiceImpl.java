package pl.edu.pwr.java.lab8.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.edu.pwr.java.lab8.model.entity.Event;
import pl.edu.pwr.java.lab8.repository.EventRepository;
import pl.edu.pwr.java.lab8.service.EventService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public List<Event> fetchEvents(int page) {
        return eventRepository.findAll(PageRequest.of(page, 12, Sort.by("date").descending())).getContent();
    }

    @Override
    public Event createEvent(Event event) {
        event.setId(null);
        return eventRepository.save(event);
    }

    @Override
    public List<Event> fetchFutureEvents() {
        return eventRepository.findAllByDateGreaterThan(Date.valueOf(LocalDate.now()));
    }

    @Override
    public List<Event> fetchAllEvents() {
        return eventRepository.findAll();
    }
}
