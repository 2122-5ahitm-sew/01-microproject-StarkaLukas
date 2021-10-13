package at.htl.kfz.entity;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerTest {

    @Test
    @Order(100)
    void customerGetter() {
        String firstName = "John";
        String lastName = "Doe";
        LocalDate dateOfBirth = LocalDate.of(1982, 11, 11);
        String place = "Place";
        String street = "Street 10";
        String zip = "AT-4040";
        double budget = 5000;

        Customer customer = new Customer(firstName, lastName, dateOfBirth, place, street, zip, budget);

        assertThat(customer).isNotNull();
        assertThat(customer.firstName).isEqualTo(firstName);
        assertThat(customer.lastName).isEqualTo(lastName);
        assertThat(customer.dateOfBirth).isEqualTo(dateOfBirth);
        assertThat(customer.place).isEqualTo(place);
        assertThat(customer.street).isEqualTo(street);
        assertThat(customer.zip).isEqualTo(zip);
        assertThat(customer.budget).isEqualTo(budget);
    }

    @Test
    @Order(110)
    void customerToString() {
        Long id = null;
        String firstName = "John";
        String lastName = "Doe";
        LocalDate dateOfBirth = LocalDate.of(1982, 11, 11);
        String place = "Place";
        String street = "Street 10";
        String zip = "AT-4040";
        double budget = 5000;

        Customer customer = new Customer(firstName, lastName, dateOfBirth, place, street, zip, budget);

        String expected = String.format("%d: %s %s born on %s lives in %s %s %s and has a budget of %.2f",
                id,
                firstName,
                lastName,
                dateOfBirth.toString(),
                street,
                zip,
                place,
                budget);

        assertThat(customer).isNotNull();
        assertThat(customer.toString()).isEqualTo(expected);
    }
}
