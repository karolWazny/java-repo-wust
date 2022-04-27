package pl.edu.pwr.java.lab7.service;

import pl.edu.pwr.java.lab7.model.entity.Installment;

import java.util.List;

public interface InstallmentService {
    void createInstallment(Installment installment);
    List<Installment> fetchUnpaidInstallmentsForPerson(Long personId);
    List<Installment> fetchInstallmentsForEvent(Long eventId, int page);
    List<Installment> fetchPendingInstallmentsForPerson(Long personId);
    List<Installment> fetchOverdueInstallmentsForPerson(Long personId);
}
