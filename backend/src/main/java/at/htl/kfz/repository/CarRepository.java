package at.htl.kfz.repository;

import at.htl.kfz.entity.Car;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CarRepository implements PanacheRepository<Car> {

    public Car findById(Long id) {
        return find("id", id).firstResult();
    }
}
