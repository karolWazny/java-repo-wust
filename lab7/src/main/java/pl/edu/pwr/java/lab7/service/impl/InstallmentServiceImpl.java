package pl.edu.pwr.java.lab7.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pwr.java.lab7.model.entity.Installment;
import pl.edu.pwr.java.lab7.repository.InstallmentRepository;
import pl.edu.pwr.java.lab7.service.InstallmentService;

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
}
