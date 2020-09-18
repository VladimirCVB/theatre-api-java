package fontys.sem3.service.resources;


import fontys.sem3.service.model.Eveniment;
import fontys.sem3.service.model.Seat;
import fontys.sem3.service.model.User;
import fontys.sem3.service.repository.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/users")
public class UserResources {
    @Context
    private UriInfo uriInfo;
    // this has to be static because the service is stateless:
    private static final FakeDataStore fakeDataStore = new FakeDataStore();

    @GET //GET at http://localhost:XXXX/students?
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<User> users = fakeDataStore.getUsers();

        GenericEntity<List<User>> entity = new GenericEntity<>(users) {  };
        return Response.ok(entity).build();
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
