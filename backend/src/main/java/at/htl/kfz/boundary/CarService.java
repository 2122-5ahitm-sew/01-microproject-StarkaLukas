package at.htl.kfz.boundary;

import at.htl.kfz.entity.Car;
import at.htl.kfz.repository.CarRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api-car")
public class CarService {

    @Inject
    CarRepository carRepository;

    @GET
    @Path("/{id}")
    @Produces({
            MediaType.APPLICATION_JSON
    })
    public Car getCar(@PathParam("id") Long id) {
        return carRepository.findById(id);
    }

    @GET
    @Produces({
            MediaType.APPLICATION_JSON
    })
    public List<Car> getCars() {
        return carRepository.listAll();
    }

    @POST
    @Consumes({
            MediaType.APPLICATION_JSON
    })
    @Produces({
            MediaType.APPLICATION_JSON
    })
    @Transactional
    public Car addCarObject(Car car) {
        carRepository.persist(car);
        return car;
    }

    @PUT
    @Path("/{id}")
    @Consumes({
            MediaType.APPLICATION_JSON
    })
    @Produces({
            MediaType.APPLICATION_JSON
    })
    @Transactional
    public Car updateCar(@PathParam("id") Long id, Car car) {
        Car receivedCar = carRepository.findById(id);

        if (receivedCar != null) {
            receivedCar.brand = car.brand;
            receivedCar.color = car.color;
            receivedCar.hp = car.hp;
            receivedCar.model = car.model;
            receivedCar.price = car.price;
        }

        return receivedCar;
    }


    @DELETE
    @Path("/{id}")
    @Consumes({
            MediaType.APPLICATION_JSON
    })
    @Produces({
            MediaType.APPLICATION_JSON
    })
    @Transactional
    public Car deleteCar(@PathParam("id") Long id) {
        Car receivedCar = carRepository.findById(id);

        if (receivedCar != null) {
            carRepository.delete(receivedCar);
        }

        return receivedCar;
    }
}
