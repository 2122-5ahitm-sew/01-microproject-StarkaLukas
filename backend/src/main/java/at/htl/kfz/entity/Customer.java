package at.htl.kfz.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Customer extends PanacheEntity {
    public String firstName;
    public String lastName;
    @JsonbDateFormat("dd-MM-yyyy")
    public LocalDate dateOfBirth;
    public String place;
    public String street;
    public String zip;
    public double budget;

    public Customer(String firstName, String lastName, LocalDate dateOfBirth, String place, String street, String zip, double budget) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.place = place;
        this.street = street;
        this.zip = zip;
        this.budget = budget;
    }

    public Customer() {
    }

    @Override
    public String toString() {
        return String.format("%d: %s %s born on %s lives in %s %s %s and has a budget of %.2f",
                this.id,
                this.firstName,
                this.lastName,
                this.dateOfBirth.toString(),
                this.street,
                this.zip,
                this.place,
                this.budget);
    }
}
