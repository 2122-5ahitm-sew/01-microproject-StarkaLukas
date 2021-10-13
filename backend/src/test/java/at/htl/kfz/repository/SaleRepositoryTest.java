package at.htl.kfz.repository;

import at.htl.kfz.entity.*;
import io.quarkus.test.junit.QuarkusTest;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SaleRepositoryTest {

    private static final Logger LOG = Logger.getLogger(SaleRepositoryTest.class);

    @Inject
    SaleRepository saleRepository;

    @Test
    @Order(100)
    void repoExists() {
        assertThat(saleRepository).isNotNull();
    }

    @Test
    @Order(110)
    void initRepo() {
        LOG.info(saleRepository.listAll().toString());

        assertThat(saleRepository.listAll().size()).isEqualTo(3);
    }

    @Test
    @Order(120)
    @Transactional
    void saveSaleOk() {
        Salesman salesman = new Salesman(
                "Michael",
                "Salesman",
                LocalDate.of(2013, 10, 20), 2300
        );

        Customer customer1 = new Customer(
                "John",
                "Doe",
                LocalDate.of(1982, 11, 11),
                "Place",
                "Street 10",
                "AT-4040",
                5000
        );

        Car car1 = new Car(
                "E 240 Elegance",
                Brand.MERCEDES,
                "Black",
                177,
                9470.00

        );

        Sale sale = new Sale(
                car1,
                customer1,
                salesman,
                LocalDate.of(2020, 10, 10),
                0.0
        );


        LOG.info("Before saving: " + saleRepository.listAll());

        int sizeBeforeSaving = saleRepository.listAll().size();

        saleRepository.persist(sale);

        LOG.info("After saving: " + saleRepository.listAll());

        assertThat(saleRepository.listAll().size()).isEqualTo(sizeBeforeSaving + 1);
        assertThat(saleRepository.listAll().get(sizeBeforeSaving).id).isEqualTo(sale.id);
        assertThat(saleRepository.listAll().get(sizeBeforeSaving).car).isEqualTo(sale.car);
        assertThat(saleRepository.listAll().get(sizeBeforeSaving).customer).isEqualTo(sale.customer);
        assertThat(saleRepository.listAll().get(sizeBeforeSaving).salesman).isEqualTo(sale.salesman);
        assertThat(saleRepository.listAll().get(sizeBeforeSaving).contractDate).isEqualTo(sale.contractDate);
        assertThat(saleRepository.listAll().get(sizeBeforeSaving).discount).isEqualTo(sale.discount);
    }

    @Test
    @Order(140)
    @Transactional
    void findByIdOk() {
        Salesman salesman = new Salesman(
                "Michael",
                "Salesman",
                LocalDate.of(2013, 10, 20), 2300
        );

        Customer customer1 = new Customer(
                "John",
                "Doe",
                LocalDate.of(1982, 11, 11),
                "Place",
                "Street 10",
                "AT-4040",
                5000
        );

        Car car1 = new Car(
                "E 240 Elegance",
                Brand.MERCEDES,
                "Black",
                177,
                9470.00

        );

        Sale sale = new Sale(
                car1,
                customer1,
                salesman,
                LocalDate.of(2020, 10, 10),
                0.0
        );

        int sizeBeforeSaving = saleRepository.listAll().size();

        saleRepository.persist(sale);

        LOG.info(saleRepository.listAll().toString());

        Sale foundSale = saleRepository.findById(sale.id);

        assertThat(saleRepository.listAll().size()).isEqualTo(sizeBeforeSaving + 1);
        assertThat(foundSale).isEqualTo(sale);
    }

    @Test
    @Order(150)
    void findByIdFail() {
        Salesman salesman = new Salesman(
                "Michael",
                "Salesman",
                LocalDate.of(2013, 10, 20), 2300
        );

        Customer customer1 = new Customer(
                "John",
                "Doe",
                LocalDate.of(1982, 11, 11),
                "Place",
                "Street 10",
                "AT-4040",
                5000
        );

        Car car1 = new Car(
                "E 240 Elegance",
                Brand.MERCEDES,
                "Black",
                177,
                9470.00

        );

        Sale sale = new Sale(
                car1,
                customer1,
                salesman,
                LocalDate.of(2020, 10, 10),
                0.0
        );

        LOG.info(saleRepository.listAll().toString());

        Sale foundSale = saleRepository.findById(sale.id);

        assertThat(foundSale).isNull();
    }

    @Test
    @Order(160)
    @Transactional
    void deleteSaleOk() {
        Salesman salesman = new Salesman(
                "Michael",
                "Salesman",
                LocalDate.of(2013, 10, 20), 2300
        );

        Customer customer1 = new Customer(
                "John",
                "Doe",
                LocalDate.of(1982, 11, 11),
                "Place",
                "Street 10",
                "AT-4040",
                5000
        );

        Car car1 = new Car(
                "E 240 Elegance",
                Brand.MERCEDES,
                "Black",
                177,
                9470.00

        );

        Sale sale = new Sale(
                car1,
                customer1,
                salesman,
                LocalDate.of(2020, 10, 10),
                0.0
        );

        saleRepository.persist(sale);

        LOG.info("Before deletion: " + saleRepository.listAll());

        Sale saleToDelete = saleRepository.findById(sale.id);
        int sizeBeforeDeletion = saleRepository.listAll().size();

        saleRepository.delete(saleToDelete);

        LOG.info("After deletion: " + saleRepository.listAll());

        assertThat(saleRepository.listAll()).doesNotContain(saleToDelete);
        assertThat(saleRepository.listAll().size()).isEqualTo(sizeBeforeDeletion - 1);
    }

    @Test
    @Order(170)
    @Transactional
    void deleteSaleMultipleTimes() {
        Salesman salesman = new Salesman(
                "Michael",
                "Salesman",
                LocalDate.of(2013, 10, 20), 2300
        );

        Customer customer1 = new Customer(
                "John",
                "Doe",
                LocalDate.of(1982, 11, 11),
                "Place",
                "Street 10",
                "AT-4040",
                5000
        );

        Car car1 = new Car(
                "E 240 Elegance",
                Brand.MERCEDES,
                "Black",
                177,
                9470.00

        );

        Sale sale = new Sale(
                car1,
                customer1,
                salesman,
                LocalDate.of(2020, 10, 10),
                0.0
        );

        saleRepository.persist(sale);

        LOG.info("Before deletion: " + saleRepository.listAll());

        Sale saleToDelete = saleRepository.findById(sale.id);
        int sizeBeforeDeletion = saleRepository.listAll().size();

        saleRepository.delete(saleToDelete);
        saleRepository.delete(saleToDelete);
        saleRepository.delete(saleToDelete);

        LOG.info("After deletion: " + saleRepository.listAll());

        assertThat(saleRepository.listAll()).doesNotContain(saleToDelete);
        assertThat(saleRepository.listAll().size()).isEqualTo(sizeBeforeDeletion - 1);
    }

    @Test
    @Order(180)
    @Transactional
    void deleteSaleWithNotExistingId() {
        Salesman salesman = new Salesman(
                "Michael",
                "Salesman",
                LocalDate.of(2013, 10, 20), 2300
        );

        Customer customer1 = new Customer(
                "John",
                "Doe",
                LocalDate.of(1982, 11, 11),
                "Place",
                "Street 10",
                "AT-4040",
                5000
        );

        Car car1 = new Car(
                "E 240 Elegance",
                Brand.MERCEDES,
                "Black",
                177,
                9470.00

        );

        Sale sale = new Sale(
                car1,
                customer1,
                salesman,
                LocalDate.of(2020, 10, 10),
                0.0
        );

        LOG.info("Before deletion: " + saleRepository.listAll());

        int sizeBeforeDeletion = saleRepository.listAll().size();

        saleRepository.delete(sale);

        int sizeAfterDeletion = saleRepository.listAll().size();

        LOG.info("After deletion: " + saleRepository.listAll());

        assertThat(sizeBeforeDeletion).isEqualTo(sizeAfterDeletion);
    }
}
