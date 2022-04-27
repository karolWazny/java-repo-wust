package pl.edu.pwr.java.lab8.service;

import pl.edu.pwr.java.lab8.model.entity.Payment;

import java.util.List;

public interface PaymentService {
    void createPayment(Payment payment);
    List<Payment> fetchPaymentsForPerson(Long personId, int page);
    List<Payment> fetchPaymentsForEvent(Long eventId, int page);
}
