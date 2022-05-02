package pl.edu.pwr.java.lab8.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.edu.pwr.java.lab8.model.entity.Payment;
import pl.edu.pwr.java.lab8.repository.PaymentRepository;
import pl.edu.pwr.java.lab8.service.PaymentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public void createPayment(Payment payment) {
        payment.setId(null);
        paymentRepository.save(payment);
    }

    @Override
    public List<Payment> fetchPaymentsForPerson(Long personId, int page) {
        return paymentRepository.findAllByPersonId(personId, PageRequest.of(page, 12));
    }

    @Override
    public List<Payment> fetchPaymentsForEvent(Long eventId, int page) {
        return paymentRepository.findAllByEventId(eventId, PageRequest.of(page, 12));
    }
}
