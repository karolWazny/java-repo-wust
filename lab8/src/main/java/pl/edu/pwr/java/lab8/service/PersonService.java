package pl.edu.pwr.java.lab8.service;

import pl.edu.pwr.java.lab8.model.entity.Person;

import java.util.List;

public interface PersonService {
    void createPerson(Person person);
    List<Person> fetchPeople(int page);
    List<Person> fetchAllPeople();
}
