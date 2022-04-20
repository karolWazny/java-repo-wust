package pl.edu.pwr.java.lab7.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.java.lab7.model.entity.Installment;

@Service
public interface OverdueService {
    void escalateOverdueInstallment(Installment installment);
}
