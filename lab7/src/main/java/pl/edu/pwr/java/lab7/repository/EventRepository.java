package pl.edu.pwr.java.lab7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.java.lab7.model.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
