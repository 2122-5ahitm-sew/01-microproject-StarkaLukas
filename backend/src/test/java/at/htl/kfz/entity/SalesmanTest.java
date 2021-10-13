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
public class SalesmanTest {
    @Test
    @Order(100)
    void salesmanGetter() {
        String firstName = "John";
        String lastName = "Doe";
        LocalDate hireDate = LocalDate.of(1982, 11, 11);
        double salary = 5000;

        Salesman salesman = new Salesman(firstName, lastName, hireDate, salary);

        assertThat(salesman).isNotNull();
        assertThat(salesman.firstName).isEqualTo(firstName);
        assertThat(salesman.lastName).isEqualTo(lastName);
        assertThat(salesman.hireDate).isEqualTo(hireDate);
        assertThat(salesman.salary).isEqualTo(salary);
    }

    @Test
    @Order(110)
    void salesmanToString() {
        Long id = null;
        String firstName = "John";
        String lastName = "Doe";
        LocalDate hireDate = LocalDate.of(1982, 11, 11);
        double salary = 5000;

        Salesman salesman = new Salesman(firstName, lastName, hireDate, salary);

        String expected = String.format("%d: %s %s was hired on %s and has a salary of %.2f",
                id,
                firstName,
                lastName,
                hireDate,
                salary);

        assertThat(salesman).isNotNull();
        assertThat(salesman.toString()).isEqualTo(expected);
    }
}
