package pl.edu.pwr.java.lab7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.java.lab7.model.entity.Installment;

public interface InstallmentRepository extends JpaRepository<Installment, Long> {
}
