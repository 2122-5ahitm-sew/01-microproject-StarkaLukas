package at.htl.kfz.repository;

import at.htl.kfz.entity.Salesman;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SalesmanRepository implements PanacheRepository<Salesman> {

    public Salesman findById(Long id) {
        return find("id", id).firstResult();
    }
}
