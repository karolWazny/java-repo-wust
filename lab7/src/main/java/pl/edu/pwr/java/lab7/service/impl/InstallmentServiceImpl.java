package pl.edu.pwr.java.lab7.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.edu.pwr.java.lab7.model.entity.Installment;
import pl.edu.pwr.java.lab7.repository.InstallmentRepository;
import pl.edu.pwr.java.lab7.service.InstallmentService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstallmentServiceImpl implements InstallmentService {
    private final InstallmentRepository installmentRepository;

    @Override
    public void createInstallment(Installment installment) {
        Integer installmentNumber = installmentRepository.countAllByEventId(installment.getEvent().getId()) + 1;
        log.info("number of installments for this event: " + (installmentNumber - 1));
        installment.setInstallmentNumber(installmentNumber);
        installmentRepository.save(installment);
    }

    @Override
    public List<Installment> fetchUnpaidInstallmentsForPerson(Long personId) {
        log.debug("using my query");
        return installmentRepository.findUnpaidByPersonId(personId);
    }

    @Override
    public List<Installment> fetchInstallmentsForEvent(Long eventId, int page) {
        return installmentRepository.findAllByEventId(eventId, PageRequest.of(page, 12));
    }

    @Override
    public List<Installment> fetchPendingInstallmentsForPerson(Long personId) {
        return installmentRepository.findPendingByPersonIdDueDateBetween(personId, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(15)));
    }

    @Override
    public List<Installment> fetchOverdueInstallmentsForPerson(Long personId) {
        return installmentRepository.findPendingByPersonIdDueDateBetween(personId, Date.valueOf(LocalDate.of(1970, 1, 1)), Date.valueOf(LocalDate.now().plusDays(1)));
    }
}
