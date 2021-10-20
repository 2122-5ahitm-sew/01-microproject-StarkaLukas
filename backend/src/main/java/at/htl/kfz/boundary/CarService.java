package at.htl.kfz.boundary;

import at.htl.kfz.entity.Car;
import at.htl.kfz.repository.CarRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
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
    public Response findById(@PathParam("id") Long id) {
        Car foundCar = carRepository.findById(id);

        if (foundCar != null) {
            return Response.ok(foundCar).build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .header("Reason", String.format("Car with id %d was not found.", id))
                .build();
    }

    @GET
    @Produces({
            MediaType.APPLICATION_JSON
    })
    public Response listAll() {
        return Response.ok(carRepository.listAll()).build();
    }

    @POST
    @Consumes({
            MediaType.APPLICATION_JSON
    })
    @Produces({
            MediaType.APPLICATION_JSON
    })
    @Transactional
    public Response create(@Context UriInfo uriInfo, Car car) {
        if (carRepository.findById(car.id) != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .header("Reason", String.format("Car with id %d already exists.", car.id))
                    .build();
        }

        carRepository.persist(car);

        URI uri = uriInfo.getAbsolutePathBuilder().path("" + car.id).build();

        if (car.id != 0) {
            return Response.created(uri).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
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
    public Response update(@PathParam("id") Long id, Car car) {
        Car receivedCar = carRepository.findById(id);

        if (receivedCar != null) {
            receivedCar.brand = car.brand;
            receivedCar.color = car.color;
            receivedCar.hp = car.hp;
            receivedCar.model = car.model;
            receivedCar.price = car.price;

            return Response.ok(receivedCar).build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .header("Reason", String.format("Car with id %d was not found", car.id))
                .build();
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
    public Response delete(@PathParam("id") Long id) {
        Car receivedCar = carRepository.findById(id);

        if (receivedCar != null) {
            carRepository.delete(receivedCar);
            Car foundCarAfterDeletion = carRepository.findById(id);

            if (foundCarAfterDeletion == null) {
                return Response.ok(carRepository).build();
            }

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .header("Reason", String.format("Car with id %d could not be deleted.", id))
                    .build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .header("Reason", String.format("Car with id %d was not found.", id))
                .build();
    }
}
