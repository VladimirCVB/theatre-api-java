package fontys.sem3.service.resources;


import fontys.sem3.service.model.Eveniment;
import fontys.sem3.service.model.Seat;
import fontys.sem3.service.repository.*;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@Path("/events")
public class EvenimentResources {
    @Context
    private UriInfo uriInfo;
    // this has to be static because the service is stateless:
    private static final JDBCEventsRepository JDBC_EVENTS_REPOSITORY = new JDBCEventsRepository();

    @GET //GET at http://localhost:XXXX/theater/events/1
    @Path("/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvenimentPath(@PathParam("id") int id) throws SQLException {
        Eveniment eveniment = JDBC_EVENTS_REPOSITORY.getEveniment(id);
        if (eveniment == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid id for the eveniment.").build();
        } else {
            return Response.ok(eveniment).header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @GET //GET at http://localhost:XXXX/theater/events
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEveniments() {
        List<Eveniment> eveniments = JDBC_EVENTS_REPOSITORY.getEveniments();

        GenericEntity<List<Eveniment>> entity = new GenericEntity<>(eveniments) {  };
        return Response.ok(entity).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET //GET at http://localhost:XXXX/theater/events/2/seats
    @Path("/{id}/seats")
    @RolesAllowed({"Regular"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvenimentSeats(@PathParam("id") int id) {

        List<Seat> seats = JDBC_EVENTS_REPOSITORY.getSeats(id);
        if (seats == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid id for the eveniment.").build();
        } else {
            //return Response.ok(seats).build();
            return Response.ok(seats).header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @PUT //PUT at http://localhost:XXXX/theater/events/2
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateEveniment(@PathParam("id") int id) {
        // Idempotent method. Always update (even if the resource has already been updated before).
        if (JDBC_EVENTS_REPOSITORY.updateEveniment(id)) {
            return Response.noContent().header("Access-Control-Allow-Origin", "*").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Please provide a valid event id.").header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @DELETE //DELETE at http://localhost:XXXX/theater/events/1
    @Path("/{id}")
    public Response deleteEveniment(@PathParam("id") int id) {
         JDBC_EVENTS_REPOSITORY.deleteEveniment(id);
        // Idempotent method. Always return the same response (even if the resource has already been deleted before).
        return Response.noContent().header("Access-Control-Allow-Origin", "*").build();
    }

    @POST //POST at http://localhost:XXXX/theater/events
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEveniment(Eveniment eveniment) {
        if (!JDBC_EVENTS_REPOSITORY.addEveniment(eveniment)){
            String entity =  "Eveniment with id " + eveniment.getId() + " already exists.";
            return Response.status(Response.Status.CONFLICT).header("Access-Control-Allow-Origin", "*").entity(entity).build();
        } else {
            String url = uriInfo.getAbsolutePath() + "/" + eveniment.getId(); // url of the created student
            URI uri = URI.create(url);
            return Response.created(uri).header("Access-Control-Allow-Origin", "*").build();
        }
    }


}
