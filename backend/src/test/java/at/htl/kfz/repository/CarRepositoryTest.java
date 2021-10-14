package at.htl.kfz.repository;

import at.htl.kfz.entity.Brand;
import at.htl.kfz.entity.Car;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarRepositoryTest {

    private static final Logger LOG = Logger.getLogger(CarRepositoryTest.class.getSimpleName());

    @Inject
    CarRepository carRepository;


    @Test
    @Order(100)
    void repoExists() {
        assertThat(carRepository).isNotNull();
    }

//    @Test
//    @Order(110)
//    void initRepo() {
//        List<Car> carList = carRepository.listAll();
//
//        LOG.info(carList.toString());
//
//        assertThat(carList.get(0).brand).isEqualTo(Brand.MERCEDES);
//        assertThat(carList.get(0).model).isEqualTo("E 240 Elegance");
//
//        assertThat(carList.get(1).brand).isEqualTo(Brand.OPEL);
//        assertThat(carList.get(1).model).isEqualTo("Corsa City");
//
//        assertThat(carList.get(2).brand).isEqualTo(Brand.VW);
//        assertThat(carList.get(2).model).isEqualTo("Golf Comfortline");
//    }

    @Test
    @Order(120)
    @Transactional
    void saveCarOk() {
        Car car = new Car(
                "Sportage Active 2,0",
                Brand.KIA,
                "White",
                136,
                8990.00
        );

        LOG.info("Before saving: " + carRepository.listAll());

        int sizeBeforeSaving = carRepository.listAll().size();

        carRepository.persist(car);

        LOG.info("After saving: " + carRepository.listAll());

        assertThat(carRepository.listAll().size()).isEqualTo(sizeBeforeSaving + 1);
        assertThat(carRepository.listAll()).contains(car);
    }

    @Test
    @Order(140)
    @Transactional
    void findByIdOk() {
        Car car = new Car(
                "Sportage Active 2,0",
                Brand.KIA,
                "White",
                136,
                8990.00
        );

        carRepository.persist(car);

        LOG.info(carRepository.listAll().toString());

        Car foundCar = carRepository.findById(car.id);

        assertThat(foundCar).isEqualTo(car);
    }

    @Test
    @Order(150)
    void findByIdFail() {
        Car car = new Car(
                "Sportage Active 2,0",
                Brand.KIA,
                "White",
                136,
                8990.00
        );

        LOG.info(carRepository.listAll().toString());

        Car foundCar = carRepository.findById(car.id);

        assertThat(foundCar).isNull();
    }

    @Test
    @Order(160)
    @Transactional
    void deleteCarOk() {
        Car car = new Car(
                "Sportage Active 2,0",
                Brand.KIA,
                "White",
                136,
                8990.00
        );

        carRepository.persist(car);

        LOG.info("Before deletion: " + carRepository.listAll());

        Car carToDelete = carRepository.findById(car.id);
        int sizeBeforeDeletion = carRepository.listAll().size();

        carRepository.delete(carToDelete);

        LOG.info("After deletion: " + carRepository.listAll());

        assertThat(carRepository.listAll()).doesNotContain(carToDelete);
        assertThat(carRepository.listAll().size()).isEqualTo(sizeBeforeDeletion - 1);
    }

    @Test
    @Order(170)
    @Transactional
    void deleteCarMultipleTimes() {
        Car car = new Car(
                "Sportage Active 2,0",
                Brand.KIA,
                "White",
                136,
                8990.00
        );

        carRepository.persist(car);

        LOG.info("Before deletion: " + carRepository.listAll());

        Car carToDelete = carRepository.findById(car.id);
        int sizeBeforeDeletion = carRepository.listAll().size();

        carRepository.delete(carToDelete);
        carRepository.delete(carToDelete);
        carRepository.delete(carToDelete);

        LOG.info("After deletion: " + carRepository.findAll());

        assertThat(carRepository.listAll()).doesNotContain(carToDelete);
        assertThat(carRepository.listAll().size()).isEqualTo(sizeBeforeDeletion - 1);
    }

    @Test
    @Order(180)
    @Transactional
    void deleteCarWithCarNotExistingId() {
        Car car = new Car(
                "Sportage Active 2,0",
                Brand.KIA,
                "White",
                136,
                8990.00
        );

        LOG.info("Before deletion: " + carRepository.listAll());

        int sizeBeforeDeletion = carRepository.listAll().size();

        carRepository.delete(car);

        int sizeAfterDeletion = carRepository.listAll().size();

        LOG.info("After deletion: " + carRepository.listAll());

        assertThat(sizeBeforeDeletion).isEqualTo(sizeAfterDeletion);
    }
}
