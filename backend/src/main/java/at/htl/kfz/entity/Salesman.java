package at.htl.kfz.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Salesman extends PanacheEntity {
    public String firstName;
    public String lastName;
    @JsonbDateFormat("dd-MM-yyyy")
    public LocalDate hireDate;
    public double salary;

    public Salesman(String firstName, String lastName, LocalDate hireDate, double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.hireDate = hireDate;
        this.salary = salary;
    }

    public Salesman() {
    }

    @Override
    public String toString() {
        return String.format("%d: %s %s was hired on %s and has a salary of %.2f",
                this.id,
                this.firstName,
                this.lastName,
                this.hireDate,
                this.salary);
    }
}
