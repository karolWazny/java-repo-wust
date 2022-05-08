package pl.edu.pwr.java.lab8.model.mapper.soap;

import pl.pwr.java.lab8.soap.Person;

public class PersonMapper {
    public static Person map(pl.edu.pwr.java.lab8.model.entity.Person person){
        Person output = new Person();
        output.setId(person.getId());
        output.setFirstName(person.getFirstName());
        output.setLastName(person.getLastName());
        return output;
    }

    public static pl.edu.pwr.java.lab8.model.entity.Person map(Person person){
        pl.edu.pwr.java.lab8.model.entity.Person output = new pl.edu.pwr.java.lab8.model.entity.Person();
        output.setId(person.getId());
        output.setFirstName(person.getFirstName());
        output.setLastName(person.getLastName());
        return output;
    }
}
