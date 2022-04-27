package pl.edu.pwr.java.lab8.service;

import pl.edu.pwr.java.lab8.model.entity.Installment;
import pl.edu.pwr.java.lab8.model.entity.Person;

import java.util.List;
import java.util.Map;

public interface ReminderService {
    void remind(Map<Person, List<Installment>> pending);
    void escalate(Map<Person, List<Installment>> overdue);
}
