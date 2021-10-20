package at.htl.kfz.boundary;

import at.htl.kfz.entity.Car;
import at.htl.kfz.entity.Customer;
import at.htl.kfz.entity.Sale;
import at.htl.kfz.entity.Salesman;
import at.htl.kfz.repository.SaleRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Path("api-sale")
public class SaleService {
    @Inject
    SaleRepository saleRepository;

    @GET
    @Path("/{id}")
    @Produces({
            MediaType.APPLICATION_JSON
    })
    public Response findById(@PathParam("id") Long id) {
        Sale foundSale = saleRepository.findById(id);

        if (foundSale != null) {
            return Response.ok(foundSale).build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .header("Reason", String.format("Sale with id %d was not found.", id))
                .build();
    }

    @GET
    @Produces({
            MediaType.APPLICATION_JSON
    })
    public Response listAll() {
        return Response.ok(saleRepository.listAll()).build();
    }

    @POST
    @Consumes({
            MediaType.APPLICATION_JSON
    })
    @Produces({
            MediaType.APPLICATION_JSON
    })
    @Transactional
    public Response create(@Context UriInfo uriInfo, Sale sale) {
        if (saleRepository.findById(sale.id) != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .header("Reason", String.format("Sale with id %d already exists.", sale.id))
                    .build();
        }

        saleRepository.persist(sale);

        URI uri = uriInfo.getAbsolutePathBuilder().path("" + sale.id).build();

        if (sale.id != 0) {
            return Response.created(uri).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({
            MediaType.APPLICATION_JSON
    })
    @Produces({
            MediaType.APPLICATION_JSON
    })
    @Transactional
    public Response update(@PathParam("id") Long id, Sale sale) {
        Sale receivedSale = saleRepository.findById(id);

        if (receivedSale != null) {
            receivedSale.car = sale.car;
            receivedSale.salesman = sale.salesman;
            receivedSale.customer = sale.customer;
            receivedSale.contractDate = sale.contractDate;
            receivedSale.discount = sale.discount;

            return Response.ok(receivedSale).build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .header("Reason", String.format("Sale with id %d was not found", sale.id))
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
    public Response delete(Long id) {
        Sale receivedSale = saleRepository.findById(id);

        if (receivedSale != null) {
            saleRepository.delete(receivedSale);
            Sale foundSaleAfterDeletion = saleRepository.findById(id);

            if (foundSaleAfterDeletion == null) {
                return Response.ok(receivedSale).build();
            }

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .header("Reason", String.format("Sale with id %d could not be deleted.", id))
                    .build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .header("Reason", String.format("Sale with id %d was not found.", id))
                .build();
    }
}
