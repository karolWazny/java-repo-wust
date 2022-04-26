package pl.edu.pwr.java.lab7.service;

import pl.edu.pwr.java.lab7.model.entity.Person;

import java.util.List;

public interface PersonService {
    void createPerson(Person person);
    List<Person> fetchPeople(int page);
}
