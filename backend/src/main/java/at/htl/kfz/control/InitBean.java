package at.htl.kfz.control;

import at.htl.kfz.entity.*;
import at.htl.kfz.repository.CarRepository;
import at.htl.kfz.repository.CustomerRepository;
import at.htl.kfz.repository.SaleRepository;
import at.htl.kfz.repository.SalesmanRepository;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;

@ApplicationScoped
public class InitBean {

    private static final Logger LOG = Logger.getLogger(InitBean.class);

    @Inject
    SalesmanRepository salesmanRepository;

    @Inject
    CarRepository carRepository;

    @Inject
    CustomerRepository customerRepository;

    @Inject
    SaleRepository saleRepository;

    @Transactional
    void startup(@Observes StartupEvent event) {
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
        Customer customer2 = new Customer(
                "Jane",
                "Roe",
                LocalDate.of(1984, 7, 23),
                "Place",
                "Street 11",
                "AT-4040",
                7000
        );
        Customer customer3 = new Customer(
                "Alan",
                "Coolguy",
                LocalDate.of(1987, 5, 15),
                "Place",
                "Street 12",
                "AT-4040",
                3000
        );

        Car car1 = new Car(
                "E 240 Elegance",
                Brand.MERCEDES,
                "Black",
                177,
                9470.00

        );

        Car car2 = new Car(
                "Corsa City",
                Brand.OPEL,
                "Blue",
                54,
                7390.00
        );
        Car car3 = new Car(
                "Golf Comfortline",
                Brand.VW,
                "Grey",
                105,
                28380.00
        );

        salesmanRepository.persist(salesman);

        salesmanRepository.persist(new Salesman(
                "Elisabeth",
                "Saleswoman",
                LocalDate.of(2016, 3, 7), 2800));

        salesmanRepository.persist(new Salesman(
                "Sophie",
                "Marketing",
                LocalDate.of(2019, 5, 16), 1700));

        carRepository.persist(car1);

        carRepository.persist(car2);

        carRepository.persist(car3);

        customerRepository.persist(customer1);

        customerRepository.persist(customer2);

        customerRepository.persist(customer3);

        saleRepository.persist(new Sale(
                        car1,
                        customer1,
                        salesman,
                        LocalDate.of(2020, 10, 10),
                        0.0
                )
        );

        saleRepository.persist(new Sale(
                        car2,
                        customer2,
                        salesman,
                        LocalDate.of(2020, 10, 17),
                        0.02
                )
        );

        saleRepository.persist(new Sale(
                        car3,
                        customer3,
                        salesman,
                        LocalDate.of(2020, 11, 3),
                        0.05
                )
        );
    }
}
