package pl.edu.pwr.java.lab8.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pwr.java.lab8.model.entity.Installment;
import pl.edu.pwr.java.lab8.model.entity.Person;
import pl.edu.pwr.java.lab8.service.ReminderService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReminderServiceImpl implements ReminderService {
    @Override
    public void remind(Map<Person, List<Installment>> pending) {
        if(!pending.isEmpty())
            writeToFileWithPrefix("reminded_", pending);
    }

    @Override
    public void escalate(Map<Person, List<Installment>> overdue) {
        if(!overdue.isEmpty())
            writeToFileWithPrefix("escalated_", overdue);
    }

    private void writeToFileWithPrefix(String prefix, Map<Person, List<Installment>> elements){
        LocalDateTime now = LocalDateTime.now();
        String filename = prefix + now.format(dateTimeFormatter()) + ".txt";
        writeToFile(filename, elements);
    }

    private void writeToFile(String filename, Map<Person, List<Installment>> elements){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            for (Map.Entry<Person, List<Installment>> personListEntry : elements.entrySet()) {
                writer.write("" + personListEntry.getKey() + "\n");
                for (Installment installment:
                        personListEntry.getValue()) {
                    writer.write("" + installment + "\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            log.info(e.toString());
        }
    }

    private DateTimeFormatter dateTimeFormatter(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm");
    }
}
