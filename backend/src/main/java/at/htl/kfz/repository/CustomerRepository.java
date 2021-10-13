package at.htl.kfz.repository;

import at.htl.kfz.entity.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {

    public Customer findById(Long id) {
        return find("id", id).firstResult();
    }
}
