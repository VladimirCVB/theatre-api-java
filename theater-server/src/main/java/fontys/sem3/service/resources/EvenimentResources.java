package fontys.sem3.service.resources;


import fontys.sem3.service.model.Eveniment;
import fontys.sem3.service.model.Seat;
import fontys.sem3.service.repository.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/events")
public class EvenimentResources {
    @Context
    private UriInfo uriInfo;
    // this has to be static because the service is stateless:
    private static final FakeDataStore fakeDataStore = new FakeDataStore();

    @GET //GET at http://localhost:XXXX/students/3
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvenimentPath(@PathParam("id") int id) {
        // StudentsRepository studentsRepository = RepositoryFactory.getStudentsRepository();
        Eveniment eveniment = fakeDataStore.getEveniment(id);//studentsRepository.get(stNr);
        if (eveniment == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid id for the eveniment.").build();
        } else {
            return Response.ok(eveniment).build();
        }
    }

    @GET //GET at http://localhost:XXXX/students?
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEveniments() {
        List<Eveniment> eveniments = fakeDataStore.getEveniments();

        GenericEntity<List<Eveniment>> entity = new GenericEntity<>(eveniments) {  };
        return Response.ok(entity).build();
    }


    @DELETE //DELETE at http://localhost:XXXX/students/3
    @Path("/delete/{id}")
    public Response deleteEveniment(@PathParam("id") int id) {
        fakeDataStore.deleteEveniment(id);
        // Idempotent method. Always return the same response (even if the resource has already been deleted before).
        return Response.noContent().build();
    }

    @POST //POST at http://localhost:XXXX/students/
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEveniment(Eveniment eveniment) {
        if (!fakeDataStore.addEveniment(eveniment)){
            String entity =  "Eveniment with id " + eveniment.getId() + " already exists.";
            return Response.status(Response.Status.CONFLICT).entity(entity).build();
        } else {
            String url = uriInfo.getAbsolutePath() + "/" + eveniment.getId(); // url of the created student
            URI uri = URI.create(url);
            return Response.created(uri).build();
        }
    }

    @PUT //PUT at http://localhost:XXXX/students/
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update/{id}")
    public Response updateEveniment(Eveniment eveniment) {
        // Idempotent method. Always update (even if the resource has already been updated before).
        if (fakeDataStore.updateEveniment(eveniment)) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Please provide a valid event id.").build();
        }
    }

    @GET //GET at http://localhost:XXXX/students/3
    @Path("/{id}/seats")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvenimentSeats(@PathParam("id") int id) {
        // StudentsRepository studentsRepository = RepositoryFactory.getStudentsRepository();
        List<Seat> seats = fakeDataStore.getEveniment(id).getSeats();//studentsRepository.get(stNr);
        if (seats == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid id for the eveniment.").build();
        } else {
            return Response.ok(seats).build();
        }
    }
}
