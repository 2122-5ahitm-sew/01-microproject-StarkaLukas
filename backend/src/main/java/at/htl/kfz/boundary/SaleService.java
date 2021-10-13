package at.htl.kfz.boundary;

import at.htl.kfz.entity.Car;
import at.htl.kfz.entity.Customer;
import at.htl.kfz.entity.Sale;
import at.htl.kfz.entity.Salesman;
import at.htl.kfz.repository.SaleRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public Sale getSale(@PathParam("id") Long id) {
        return saleRepository.findById(id);
    }

    @GET
    @Produces({
            MediaType.APPLICATION_JSON
    })
    public List<Sale> getSales() {
        return saleRepository.listAll();
    }

    @POST
    @Consumes({
            MediaType.APPLICATION_JSON
    })
    @Produces({
            MediaType.APPLICATION_JSON
    })
    @Transactional
    public Sale addSale(Sale sale) {
        saleRepository.persist(sale);

        return sale;
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
    public Sale updateSale(@PathParam("id") Long id, Sale sale) {
        Sale receivedSale = saleRepository.findById(id);

        if (receivedSale != null) {
            receivedSale.car = sale.car;
            receivedSale.salesman = sale.salesman;
            receivedSale.customer = sale.customer;
            receivedSale.contractDate = sale.contractDate;
            receivedSale.discount = sale.discount;
        }

        return receivedSale;
    }

    @DELETE
    @Path("{id}")
    @Consumes({
            MediaType.APPLICATION_JSON
    })
    @Produces({
            MediaType.APPLICATION_JSON
    })
    @Transactional
    public Sale deleteSale(Long id) {
        Sale receivedSale = saleRepository.findById(id);

        if (receivedSale != null) {
            saleRepository.delete(receivedSale);
        }

        return receivedSale;
    }
}
