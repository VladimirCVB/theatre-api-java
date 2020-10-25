package fontys.sem3.service.resources;


import fontys.sem3.service.repository.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/users")
public class UserResources {
    @Context
    private UriInfo uriInfo;
    // this has to be static because the service is stateless:
    private static final JDBCEventsRepository JDBC_EVENTS_REPOSITORY = new JDBCEventsRepository();

    /*@GET //GET at http://localhost:XXXX/theater/users
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<UserAccount> users = fakeDataStore.getUsers();

        GenericEntity<List<UserAccount>> entity = new GenericEntity<>(users) {  };
        return Response.ok(entity).build();
    }

    @GET //GET at http://localhost:XXXX/theater/users/3
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int id) {
        //getting user from the users list
        UserAccount userAccount = fakeDataStore.getUserAccount(id);
        if (userAccount == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid id for the eveniment.").build();
        } else {
            return Response.ok(userAccount).build();
        }
    }*/
}
