package pl.edu.pwr.java.lab7;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.edu.pwr.java.lab7.gui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.jar.JarFile;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class Lab7Application extends MainWindow {

    public static void main(String[] args) {
        SpringApplication.run(Lab7Application.class, args);
    }

    //@Scheduled(cron = "0 30 1 * * ?")
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
    }

    private void checkPendingInstallments(){
        log.debug("checkPendingInstallments() call");
    }
}
