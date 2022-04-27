package pl.edu.pwr.java.lab8;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.edu.pwr.java.lab8.gui.MainWindow;
import pl.edu.pwr.java.lab8.model.entity.Installment;
import pl.edu.pwr.java.lab8.model.entity.Person;
import pl.edu.pwr.java.lab8.service.ReminderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class Lab8Application extends MainWindow {
    private ReminderService reminderService;

    public static void main(String[] args) {
        SpringApplication.run(Lab8Application.class, args);
    }

    @Scheduled(cron = "${cron.expression}")
    @Scheduled(initialDelay = 1000 * 2, fixedDelay=Long.MAX_VALUE)
    private void checkPendingAndOverdue(){
        log.info("Checking pending installments...");
        checkPendingInstallments();
        log.info("Checked pending installments.");
        log.info("Checking overdue installments...");
        checkOverdueInstallments();
        log.info("Checked overdue installments.");
    }

    private void checkOverdueInstallments(){
        log.debug("checkOverdueInstallments() call");
        List<Person> people = personService.fetchAllPeople();
        Map<Person, List<Installment>> allOverdue = new HashMap<>();
        for(Person person : people){
            List<Installment> overdueInstallments = installmentService.fetchOverdueInstallmentsForPerson(person.getId());
            if(overdueInstallments.size() > 0)
                allOverdue.put(person, overdueInstallments);
        }
        reminderService.escalate(allOverdue);
    }

    private void checkPendingInstallments(){
        log.debug("checkPendingInstallments() call");
        List<Person> people = personService.fetchAllPeople();
        Map<Person, List<Installment>> allPending = new HashMap<>();
        for(Person person : people){
            List<Installment> pendingInstallments = installmentService.fetchPendingInstallmentsForPerson(person.getId());
            if(pendingInstallments.size() > 0)
                allPending.put(person, pendingInstallments);
        }
        reminderService.remind(allPending);
    }

    @Autowired
    public void setReminderService(ReminderService reminderService) {
        this.reminderService = reminderService;
    }
}
