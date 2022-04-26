package pl.edu.pwr.java.lab7.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pwr.java.lab7.model.entity.Person;
import pl.edu.pwr.java.lab7.repository.PersonRepository;
import pl.edu.pwr.java.lab7.service.PersonService;

@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Override
    public void createPerson(Person person) {
        person.setId(null);
        personRepository.save(person);
    }

}
