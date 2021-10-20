package at.htl.kfz.boundary;

import at.htl.kfz.entity.Customer;
import at.htl.kfz.repository.CustomerRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("api-customer")
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    @GET
    @Path("/{id}")
    @Produces({
            MediaType.APPLICATION_JSON
    })
    public Response findById(@PathParam("id") Long id) {
        Customer foundCustomer = customerRepository.findById(id);

        if (foundCustomer != null) {
            return Response.ok(foundCustomer).build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .header("Reason", String.format("Customer with id %d was not found.", id))
                .build();
    }

    @GET
    @Produces({
            MediaType.APPLICATION_JSON
    })
    public Response listAll() {
        return Response.ok(customerRepository.listAll()).build();
    }

    @POST
    @Consumes({
            MediaType.APPLICATION_JSON
    })
    @Produces({
            MediaType.APPLICATION_JSON
    })
    @Transactional
    public Response create(@Context UriInfo uriInfo, Customer customer) {
        if (customerRepository.findById(customer.id) != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .header("Reason", String.format("Customer with id %d already exists.", customer.id))
                    .build();
        }

        customerRepository.persist(customer);

        URI uri = uriInfo.getAbsolutePathBuilder().path("" + customer.id).build();

        if (customer.id != 0) {
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
    public Response update(@PathParam("id") Long id, Customer customer) {
        Customer receivedCustomer = customerRepository.findById(id);

        if (receivedCustomer != null) {
            receivedCustomer.budget = customer.budget;
            receivedCustomer.dateOfBirth = customer.dateOfBirth;
            receivedCustomer.firstName = customer.firstName;
            receivedCustomer.lastName = customer.lastName;
            receivedCustomer.place = customer.place;
            receivedCustomer.street = customer.street;
            receivedCustomer.zip = customer.zip;

            return Response.ok(receivedCustomer).build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .header("Reason", String.format("Customer with id %d was not found", customer.id))
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
        Customer receivedCustomer = customerRepository.findById(id);

        if (receivedCustomer != null) {
            customerRepository.delete(receivedCustomer);
            Customer foundCustomerAfterDeletion = customerRepository.findById(id);

            if (foundCustomerAfterDeletion == null) {
                return Response.ok(receivedCustomer).build();
            }

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .header("Reason", String.format("Customer with id %d could not be deleted.", id))
                    .build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .header("Reason", String.format("Customer with id %d was not found.", id))
                .build();
    }

}
