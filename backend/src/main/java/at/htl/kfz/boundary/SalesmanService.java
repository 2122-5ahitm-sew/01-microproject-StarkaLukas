package at.htl.kfz.boundary;

import at.htl.kfz.entity.Car;
import at.htl.kfz.entity.Salesman;
import at.htl.kfz.repository.SalesmanRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
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
    public Response findById(@PathParam("id") Long id) {
        Salesman foundSalesman = salesmanRepository.findById(id);

        if (foundSalesman != null) {
            return Response.ok(foundSalesman).build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .header("Reason", String.format("Salesman with id %d was not found.", id))
                .build();
    }

    @GET
    @Produces({
            MediaType.APPLICATION_JSON
    })
    public Response listAll() {
        return Response.ok(salesmanRepository.listAll()).build();
    }

    @POST
    @Consumes({
            MediaType.APPLICATION_JSON
    })
    @Produces({
            MediaType.APPLICATION_JSON
    })
    @Transactional
    public Response create(@Context UriInfo uriInfo, Salesman salesman) {
        if (salesmanRepository.findById(salesman.id) != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .header("Reason", String.format("Salesman with id %d already exists.", salesman.id))
                    .build();
        }

        salesmanRepository.persist(salesman);

        URI uri = uriInfo.getAbsolutePathBuilder().path("" + salesman.id).build();

        if (salesman.id != 0) {
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
    public Response update(@PathParam("id") Long id, Salesman salesman) {
        Salesman receivedSalesman = salesmanRepository.findById(id);

        if (receivedSalesman != null) {
            receivedSalesman.firstName = salesman.firstName;
            receivedSalesman.lastName = salesman.lastName;
            receivedSalesman.salary = salesman.salary;
            receivedSalesman.hireDate = salesman.hireDate;

            return Response.ok(receivedSalesman).build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .header("Reason", String.format("Salesman with id %d was not found", salesman.id))
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
        Salesman receivedSalesman = salesmanRepository.findById(id);

        if (receivedSalesman != null) {
        salesmanRepository.deleteById(id);
        Salesman foundSalesmanAfterDeletion = salesmanRepository.findById(id);

        if (foundSalesmanAfterDeletion == null) {
            return Response.ok(receivedSalesman).build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .header("Reason", String.format("Salesman with id %d could not be deleted.", id))
                .build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
            .header("Reason", String.format("Salesman with id %d was not found.", id))
            .build();
    }
}
