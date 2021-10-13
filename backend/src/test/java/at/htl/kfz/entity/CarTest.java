package at.htl.kfz.entity;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarTest {

    @Test
    @Order(100)
    void carFields() {
        String model = "E 240 Elegance";
        Brand brand = Brand.MERCEDES;
        String color = "Black";
        int hp = 177;
        double price = 9470.00;

        Car car = new Car(model, brand, color, hp, price);

        assertThat(car).isNotNull();
        assertThat(car.model).isEqualTo(model);
        assertThat(car.brand).isEqualTo(brand);
        assertThat(car.color).isEqualTo(color);
        assertThat(car.hp).isEqualTo(hp);
        assertThat(car.price).isEqualTo(price);
    }

    @Test
    @Order(110)
    void carToString() {
        Long id = null;
        String model = "E 240 Elegance";
        Brand brand = Brand.MERCEDES;
        String color = "Black";
        int hp = 177;
        double price = 9470.00;

        String expected = String.format("%d: The %s %s %s with %d hp costs %f",
                id,
                color,
                brand,
                model,
                hp,
                price);

        Car car = new Car(model, brand, color, hp, price);

        assertThat(car).isNotNull();
        assertThat(car.toString()).isEqualTo(expected);
    }
}
