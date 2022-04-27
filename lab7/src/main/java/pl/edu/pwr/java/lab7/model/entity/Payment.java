package pl.edu.pwr.java.lab7.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "payments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_paid")
    private Date datePaid;

    @Column
    private Integer amount;

    @JoinColumn(name = "person_id")
    @ManyToOne
    private Person person;

    @JoinColumn(name = "event_id")
    @ManyToOne
    private Event event;

    @Column(name = "installment_number")
    private Integer installmentNumber;
}
