package at.htl.kfz.repository;

import at.htl.kfz.entity.Brand;
import at.htl.kfz.entity.Car;
import at.htl.kfz.entity.Customer;
import io.quarkus.test.junit.QuarkusTest;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerRepositoryTest {

    private static final Logger LOG = Logger.getLogger(CustomerRepositoryTest.class);

    @Inject
    CustomerRepository customerRepository;

    @Test
    @Order(100)
    void repoExists() {
        assertThat(customerRepository).isNotNull();
    }

    @Test
    @Order(110)
    void initRepo() {
        List<Customer> customerList = customerRepository.listAll();

        LOG.info(customerList.toString());


        assertThat(customerList.get(0).lastName).isEqualTo("Doe");
        assertThat(customerList.get(1).lastName).isEqualTo("Roe");
        assertThat(customerList.get(2).lastName).isEqualTo("Coolguy");
    }

    @Test
    @Order(120)
    @Transactional
    void saveCustomerOk() {
        Customer customer = new Customer(
                "Testus",
                "McTest",
                LocalDate.of(1990, 10, 12),
                "Place",
                "Street 24",
                "AT-4040",
                5000
        );

        LOG.info("Before saving: " + customerRepository.listAll());

        int sizeBeforeSaving = customerRepository.listAll().size();

        customerRepository.persist(customer);

        LOG.info("After saving: " + customerRepository.listAll());

        assertThat(customerRepository.listAll().size()).isEqualTo(sizeBeforeSaving + 1);
        assertThat(customerRepository.listAll()).contains(customer);
    }

    @Test
    @Order(140)
    @Transactional
    void findByIdOk() {
        Customer customer = new Customer(
                "Testus",
                "McTest",
                LocalDate.of(1990, 10, 12),
                "Place",
                "Street 24",
                "AT-4040",
                5000
        );

        customerRepository.persist(customer);

        LOG.info(customerRepository.listAll().toString());

        Customer foundCustomer = customerRepository.findById(customer.id);

        assertThat(foundCustomer).isEqualTo(customer);
    }

    @Test
    @Order(150)
    void findByIdFail() {
        Customer customer = new Customer(
                "Testus",
                "McTest",
                LocalDate.of(1990, 10, 12),
                "Place",
                "Street 24",
                "AT-4040",
                5000
        );

        LOG.info(customerRepository.listAll().toString());

        Customer foundCustomer = customerRepository.findById(customer.id);

        assertThat(foundCustomer).isNull();
    }

    @Test
    @Order(160)
    @Transactional
    void deleteCustomerOk() {
        Customer customer = new Customer(
                "Testus",
                "McTest",
                LocalDate.of(1990, 10, 12),
                "Place",
                "Street 24",
                "AT-4040",
                5000
        );

        customerRepository.persist(customer);

        LOG.info("Before deletion: " + customerRepository.listAll());

        Customer customerToDelete = customerRepository.findById(customer.id);
        int sizeBeforeDeletion = customerRepository.listAll().size();

        customerRepository.delete(customerToDelete);

        LOG.info("After deletion: " + customerRepository.findAll());

        assertThat(customerRepository.listAll()).doesNotContain(customerToDelete);
        assertThat(customerRepository.listAll().size()).isEqualTo(sizeBeforeDeletion - 1);
    }

    @Test
    @Order(170)
    @Transactional
    void deleteCustomerMultipleTimes() {
        Customer customer = new Customer(
                "Testus",
                "McTest",
                LocalDate.of(1990, 10, 12),
                "Place",
                "Street 24",
                "AT-4040",
                5000
        );

        customerRepository.persist(customer);

        LOG.info("Before deletion: " + customerRepository.listAll());

        Customer customerToDelete = customerRepository.findById(customer.id);
        int sizeBeforeDeletion = customerRepository.listAll().size();

        customerRepository.delete(customerToDelete);
        customerRepository.delete(customerToDelete);
        customerRepository.delete(customerToDelete);

        LOG.info("After deletion: " + customerRepository.listAll());

        assertThat(customerRepository.listAll()).doesNotContain(customerToDelete);
        assertThat(customerRepository.listAll().size()).isEqualTo(sizeBeforeDeletion - 1);
    }

    @Test
    @Order(180)
    @Transactional
    void deleteCustomerWithNotExistingId() {
        Customer customer = new Customer(
                "Testus",
                "McTest",
                LocalDate.of(1990, 10, 12),
                "Place",
                "Street 24",
                "AT-4040",
                5000
        );

        LOG.info("Before deletion: " + customerRepository.listAll());

        int sizeBeforeDeletion = customerRepository.listAll().size();

        customerRepository.delete(customer);

        int sizeAfterDeletion = customerRepository.listAll().size();

        LOG.info("After deletion: " + customerRepository.listAll());

        assertThat(sizeBeforeDeletion).isEqualTo(sizeAfterDeletion);
    }
}
