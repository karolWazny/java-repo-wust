package pl.edu.pwr.java.lab7.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.java.lab7.model.entity.Installment;

import java.util.List;

public interface InstallmentRepository extends JpaRepository<Installment, Long> {
    Integer countAllByEventId(Long eventId);
    List<Installment> findAllByEventId(Long eventId, Pageable pageable);
}
