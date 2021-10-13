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
public class SaleTest {

    @Test
    @Order(100)
    void saleGetter() {
        Car car = new Car(
                "E 240 Elegance",
                Brand.MERCEDES,
                "Black",
                177,
                9470.00

        );
        Customer customer = new Customer(
                "John",
                "Doe",
                LocalDate.of(1982, 11, 11),
                "Place",
                "Street 10",
                "AT-4040",
                5000
        );
        Salesman salesman = new Salesman(
                "Michael",
                "Salesman",
                LocalDate.of(2013, 10, 20),
                2300
        );
        LocalDate contractDate = LocalDate.of(1982, 11, 11);
        double discount = 0.2;

        Sale sale = new Sale(car, customer, salesman, contractDate, discount);

        assertThat(sale).isNotNull();
        assertThat(sale.car).isEqualTo(car);
        assertThat(sale.customer).isEqualTo(customer);
        assertThat(sale.salesman).isEqualTo(salesman);
        assertThat(sale.contractDate).isEqualTo(contractDate);
        assertThat(sale.discount).isEqualTo(discount);
    }
}
