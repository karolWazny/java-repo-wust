package pl.edu.pwr.java.lab8.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "people")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Identifiable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
