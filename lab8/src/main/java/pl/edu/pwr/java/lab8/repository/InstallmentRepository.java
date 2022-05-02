package pl.edu.pwr.java.lab8.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.pwr.java.lab8.model.entity.Installment;

import java.sql.Date;
import java.util.List;

public interface InstallmentRepository extends JpaRepository<Installment, Long> {
    Integer countAllByEventId(Long eventId);
    List<Installment> findAllByEventId(Long eventId, Pageable pageable);
    @Query("select i from #{#entityName} i where not exists " +
            "(select p from Payment p " +
            "where p.installmentNumber = i.installmentNumber " +
            "and p.event.id = i.event.id " +
            "and p.person.id = ?1)")
    List<Installment> findUnpaidByPersonId(Long personId);
    @Query("select i from #{#entityName} i where not exists " +
            "(select p from Payment p " +
            "where p.installmentNumber = i.installmentNumber " +
            "and p.event.id = i.event.id " +
            "and p.person.id = ?1)" +
            "and i.dueDate between ?2 and ?3")
    List<Installment> findPendingByPersonIdDueDateBetween(Long personId, Date from, Date to);
}
