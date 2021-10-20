package at.htl.kfz.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Sale extends PanacheEntity {

    @ManyToOne
    @Cascade(CascadeType.ALL)
    public Car car;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    public Customer customer;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    public Salesman salesman;

    @JsonbProperty("contract_date")
    @JsonbDateFormat("dd-MM-yyyy")
    public LocalDate contractDate;

    public double discount;

    public Sale(Car car, Customer customer, Salesman salesman, LocalDate contractDate, double discount) {
        this.car = car;
        this.customer = customer;
        this.salesman = salesman;
        this.contractDate = contractDate;
        this.discount = discount;
    }

    public Sale() {
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", car=" + car +
                ", customer=" + customer +
                ", salesman=" + salesman +
                ", contractDate=" + contractDate +
                ", discount=" + discount +
                '}';
    }
}
