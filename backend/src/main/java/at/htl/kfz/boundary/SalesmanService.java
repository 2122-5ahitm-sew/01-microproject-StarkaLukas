package at.htl.kfz.boundary;

import at.htl.kfz.entity.Salesman;
import at.htl.kfz.repository.SalesmanRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("api-salesman")
public class SalesmanService {

    @Inject
    SalesmanRepository salesmanRepository;

    @GET
    @Path("/{id}")
    @Produces({
            MediaType.APPLICATION_JSON
    })
    public Salesman getSalesman(@PathParam("id") Long id) {
        return salesmanRepository.findById(id);
    }

    @GET
    @Produces({
            MediaType.APPLICATION_JSON
    })
    public List<Salesman> getSalesmen() {
        return salesmanRepository.listAll();
    }

    @POST
    @Consumes({
            MediaType.APPLICATION_JSON
    })
    @Produces({
            MediaType.APPLICATION_JSON
    })
    @Transactional
    public Salesman addSalesman(Salesman salesman) {
        salesmanRepository.persist(salesman);
        return salesman;
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
    public Salesman updateSalesman(@PathParam("id") Long id, Salesman salesman) {
        Salesman receivedSalesman = salesmanRepository.findById(id);

        if (receivedSalesman != null) {
            receivedSalesman.firstName = salesman.firstName;
            receivedSalesman.lastName = salesman.lastName;
            receivedSalesman.salary = salesman.salary;
            receivedSalesman.hireDate = salesman.hireDate;
        }

        return receivedSalesman;
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
    public Salesman deleteSalesman(@PathParam("id") Long id) {
        Salesman receivedSalesman = salesmanRepository.findById(id);
        salesmanRepository.deleteById(id);
        return receivedSalesman;
    }
}
