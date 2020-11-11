package fontys.sem3.service.resources;


import fontys.sem3.service.model.UserAccount;
import fontys.sem3.service.repository.*;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.annotation.security.DenyAll;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.net.URI;

@Path("/users")
public class UserResources {
    @Context
    private UriInfo uriInfo;
    // this has to be static because the service is stateless:
    private static final JDBCUsers JDBC_USERS = new JDBCUsers();

    /*@GET //GET at http://localhost:XXXX/theater/users
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<UserAccount> users = fakeDataStore.getUsers();

        GenericEntity<List<UserAccount>> entity = new GenericEntity<>(users) {  };
        return Response.ok(entity).build();
    }*/

    @GET //GET at http://localhost:XXXX/theater/users/3
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int id) {
        //getting user from the users list
        UserAccount userAccount = JDBC_USERS.getUserAccount(id);
        if (userAccount == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid id for the eveniment.").build();
        } else {
            return Response.ok().header("Access-Control-Allow-Origin", "*").entity(new UserAccount()).build();
        }
    }
}
