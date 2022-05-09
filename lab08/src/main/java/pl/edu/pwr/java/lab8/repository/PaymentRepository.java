package pl.edu.pwr.java.lab8.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.java.lab8.model.entity.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByPersonId(Long personId, Pageable pageable);
    List<Payment> findAllByEventId(Long eventId, Pageable pageable);
}
