package pl.edu.pwr.java.lab8.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.edu.pwr.java.lab8.model.entity.Person;
import pl.edu.pwr.java.lab8.repository.PersonRepository;
import pl.edu.pwr.java.lab8.service.PersonService;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Override
    public void createPerson(Person person) {
        person.setId(null);
        personRepository.save(person);
    }

    @Override
    public List<Person> fetchPeople(int page) {
        return personRepository.findAll(PageRequest.of(page, 12, Sort.by("lastName").ascending())).getContent();
    }

    @Override
    public List<Person> fetchAllPeople() {
        return personRepository.findAll();
    }

}
