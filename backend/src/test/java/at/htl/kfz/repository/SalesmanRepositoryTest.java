package at.htl.kfz.repository;

import at.htl.kfz.entity.Salesman;
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
public class SalesmanRepositoryTest {

    private static final Logger LOG = Logger.getLogger(SalesmanRepositoryTest.class);

    @Inject
    SalesmanRepository salesmanRepository;

    @Test
    @Order(100)
    void repoExists() {
        assertThat(salesmanRepository).isNotNull();
    }

    @Test
    @Order(110)
    void initRepo() {
        List<Salesman> salesmanList = salesmanRepository.listAll();

        LOG.info(salesmanList.toString());

        assertThat(salesmanList.get(0).lastName).isEqualTo("Salesman");
        assertThat(salesmanList.get(1).lastName).isEqualTo("Saleswoman");
        assertThat(salesmanList.get(2).lastName).isEqualTo("Marketing");
    }

    @Test
    @Order(120)
    @Transactional
    void saveSalesmanOk() {
        Salesman salesman = new Salesman(
                "Testus",
                "McTest",
                LocalDate.of(1990, 12, 10),
                2700
        );

        LOG.info("Before saving: " + salesmanRepository.listAll());

        int sizeBeforeSaving = salesmanRepository.listAll().size();

        salesmanRepository.persist(salesman);

        LOG.info("After saving: " + salesmanRepository.listAll());

        assertThat(salesmanRepository.listAll().size()).isEqualTo(sizeBeforeSaving + 1);
        assertThat(salesmanRepository.listAll()).contains(salesman);
    }

    @Test
    @Order(140)
    @Transactional
    void findByIdOk() {
        Salesman salesman = new Salesman(
                "Testus",
                "McTest",
                LocalDate.of(1990, 12, 10),
                2700
        );

        salesmanRepository.persist(salesman);

        LOG.info(salesmanRepository.listAll().toString());

        Salesman foundSalesman = salesmanRepository.findById(salesman.id);

        assertThat(foundSalesman).isEqualTo(salesman);
    }

    @Test
    @Order(150)
    void findByIdFail() {
        Salesman salesman = new Salesman(
                "Testus",
                "McTest",
                LocalDate.of(1990, 12, 10),
                2700
        );

        LOG.info(salesmanRepository.listAll().toString());

        Salesman foundSalesman = salesmanRepository.findById(salesman.id);

        assertThat(foundSalesman).isNull();
    }

    @Test
    @Order(160)
    @Transactional
    void deleteSalesmanOk() {
        Salesman salesman = new Salesman(
                "Testus",
                "McTest",
                LocalDate.of(1990, 12, 10),
                2700
        );

        salesmanRepository.persist(salesman);

        LOG.info("Before deletion: " + salesmanRepository.listAll());

        Salesman salesmanToDelete = salesmanRepository.findById(salesman.id);
        int sizeBeforeDeletion = salesmanRepository.listAll().size();

        salesmanRepository.delete(salesmanToDelete);

        LOG.info("After deletion: " + salesmanRepository.listAll());

        assertThat(salesmanRepository.listAll()).doesNotContain(salesmanToDelete);
        assertThat(salesmanRepository.listAll().size()).isEqualTo(sizeBeforeDeletion - 1);
    }

    @Test
    @Order(170)
    @Transactional
    void deleteSalesmanMultipleTimes() {
        Salesman salesman = new Salesman(
                "Testus",
                "McTest",
                LocalDate.of(1990, 12, 10),
                2700
        );

        salesmanRepository.persist(salesman);

        LOG.info("Before deletion: " + salesmanRepository.listAll());

        Salesman salesmanToDelete = salesmanRepository.findById(salesman.id);
        int sizeBeforeDeletion = salesmanRepository.listAll().size();

        salesmanRepository.delete(salesmanToDelete);
        salesmanRepository.delete(salesmanToDelete);
        salesmanRepository.delete(salesmanToDelete);

        LOG.info("After deletion: " + salesmanRepository.listAll());

        assertThat(salesmanRepository.listAll()).doesNotContain(salesmanToDelete);
        assertThat(salesmanRepository.listAll().size()).isEqualTo(sizeBeforeDeletion - 1);
    }

    @Test
    @Order(180)
    @Transactional
    void deleteSalesmanWithNotExistingId() {
        Salesman salesman = new Salesman(
                "Testus",
                "McTest",
                LocalDate.of(1990, 12, 10),
                2700
        );

        LOG.info("Before deletion: " + salesmanRepository.listAll());

        int sizeBeforeDeletion = salesmanRepository.listAll().size();

        salesmanRepository.delete(salesman);

        int sizeAfterDeletion = salesmanRepository.listAll().size();

        LOG.info("After deletion: " + salesmanRepository.listAll());

        assertThat(sizeBeforeDeletion).isEqualTo(sizeAfterDeletion);
    }
}
