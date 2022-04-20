package pl.edu.pwr.java.lab7.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "installments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Installment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "installment_number")
    private Integer installmentNumber;

    @Column(name = "due_date")
    private Date dueDate;

    @Column
    private Integer amount;
}
