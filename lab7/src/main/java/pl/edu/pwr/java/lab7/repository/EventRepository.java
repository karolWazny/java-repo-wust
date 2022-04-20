package pl.edu.pwr.java.lab7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.java.lab7.model.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
