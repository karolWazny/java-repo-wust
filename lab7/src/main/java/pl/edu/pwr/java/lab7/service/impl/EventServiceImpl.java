package pl.edu.pwr.java.lab7.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.edu.pwr.java.lab7.model.entity.Event;
import pl.edu.pwr.java.lab7.repository.EventRepository;
import pl.edu.pwr.java.lab7.service.EventService;

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
    public void createEvent(Event event) {
        eventRepository.save(event);
    }
}
