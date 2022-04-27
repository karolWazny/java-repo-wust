package pl.edu.pwr.java.lab8.model.entity;

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
public class Installment implements Identifiable {
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

    @Override
    public String toString() {
        return event.getName() + " " + installmentNumber + ", " + amount;
    }
}
