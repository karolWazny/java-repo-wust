package pl.edu.pwr.java.lab7.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.java.lab7.model.entity.Installment;

@Service
public interface PaymentReminderService {
    void remindAboutInstallment(Installment installment);
}
