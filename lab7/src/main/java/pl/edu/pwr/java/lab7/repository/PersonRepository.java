package pl.edu.pwr.java.lab7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.java.lab7.model.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
