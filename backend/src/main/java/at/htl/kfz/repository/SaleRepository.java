package at.htl.kfz.repository;

import at.htl.kfz.entity.Sale;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SaleRepository implements PanacheRepository<Sale> {

    public Sale findById(Long id) {
        return find("id", id).firstResult();
    }
}
