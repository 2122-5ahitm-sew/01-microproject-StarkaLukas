package at.htl.kfz.boundary;

import at.htl.kfz.entity.Customer;
import at.htl.kfz.repository.CustomerRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("api-customer")
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    @GET
    @Path("/{id}")
    @Produces({
            MediaType.APPLICATION_JSON
    })
    public Customer getCustomer(@PathParam("id") Long id) {
        return customerRepository.findById(id);
    }

    @GET
    @Produces({
            MediaType.APPLICATION_JSON
    })
    public List<Customer> getCustomers() {
        return customerRepository.listAll();
    }

    @POST
    @Consumes({
            MediaType.APPLICATION_JSON
    })
    @Produces({
            MediaType.APPLICATION_JSON
    })
    @Transactional
    public Customer addCustomer(Customer customer) {
        customerRepository.persist(customer);
        return customer;
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
    public Customer updateCustomer(@PathParam("id") Long id, Customer customer) {
        Customer receivedCustomer = customerRepository.findById(id);

        if (receivedCustomer != null) {
            receivedCustomer.budget = customer.budget;
            receivedCustomer.dateOfBirth = customer.dateOfBirth;
            receivedCustomer.firstName = customer.firstName;
            receivedCustomer.lastName = customer.lastName;
            receivedCustomer.place = customer.place;
            receivedCustomer.street = customer.street;
            receivedCustomer.zip = customer.zip;
        }

        return receivedCustomer;
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
    public Customer deleteCustomer(@PathParam("id") Long id) {
        Customer receivedCustomer = customerRepository.findById(id);

        if (receivedCustomer != null) {
            customerRepository.delete(receivedCustomer);
        }

        return receivedCustomer;
    }

}
