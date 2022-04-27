package pl.edu.pwr.java.lab7;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.edu.pwr.java.lab7.gui.MainWindow;
import pl.edu.pwr.java.lab7.model.entity.Installment;
import pl.edu.pwr.java.lab7.model.entity.Person;
import pl.edu.pwr.java.lab7.repository.InstallmentRepository;
import pl.edu.pwr.java.lab7.repository.PersonRepository;
import pl.edu.pwr.java.lab7.service.InstallmentService;

import java.util.List;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class Lab7Application extends MainWindow {
    public static void main(String[] args) {
        SpringApplication.run(Lab7Application.class, args);
    }

    @Scheduled(cron = "${cron.expression}")
    @Scheduled(initialDelay = 1000 * 5, fixedDelay=Long.MAX_VALUE)
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
        for(Person person : people){
            List<Installment> overdueInstallments = installmentService.fetchOverdueInstallmentsForPerson(person.getId());
        }
    }

    private void checkPendingInstallments(){
        log.debug("checkPendingInstallments() call");
        List<Person> people = personService.fetchAllPeople();
        for(Person person : people){
            List<Installment> pendingInstallments = installmentService.fetchPendingInstallmentsForPerson(person.getId());
        }
    }
}
