package fontys.sem3.service.resources;


import fontys.sem3.service.model.UserAccount;
import fontys.sem3.service.repository.*;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.sql.SQLException;

@Path("/users")
public class UserResources {
    @Context
    private UriInfo uriInfo;
    // this has to be static because the service is stateless:
    private static final JDBCUsersRepository JDBC_USERS = new JDBCUsersRepository();

    @GET //POST at http://localhost:XXXX/theater/users
    @RolesAllowed({"administrator"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUserAccounts() {
        //getting user from the users list
        UserAccount userAccount = JDBC_USERS.getUserAccount(1);
        if (userAccount == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid id for the eveniment.").build();
        } else {
            URI uri = URI.create("http://localhost:9090/theater/users/1");
            return Response.ok(123).header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @GET //GET at http://localhost:XXXX/theater/events/1
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserAccount(@PathParam("id") int id) throws SQLException {
        UserAccount userAccount = JDBC_USERS.getUserAccount(id);
        if (userAccount == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid id for the specified account.").build();
        } else {
            return Response.ok(userAccount).header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @PUT //PUT at http://localhost:XXXX/theater/events/2
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateAccount(@PathParam("id") int id, String passwordValue) {
        // Idempotent method. Always update (even if the resource has already been updated before).
        if (JDBC_USERS.updateUser(id, passwordValue)) {
            return Response.noContent().header("Access-Control-Allow-Origin", "*").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Please provide a valid user account id.").header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @DELETE //DELETE at http://localhost:XXXX/theater/events/1
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        JDBC_USERS.deleteUser(id);
        // Idempotent method. Always return the same response (even if the resource has already been deleted before).
        return Response.noContent().header("Access-Control-Allow-Origin", "*").build();
    }

    @POST //POST at http://localhost:XXXX/theater/events
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(UserAccount userAccount) {
        if (!JDBC_USERS.addUserAccount(userAccount)){
            String entity =  "User with the same email already exists.";
            return Response.status(Response.Status.CONFLICT).header("Access-Control-Allow-Origin", "*").entity(entity).build();
        } else {
            String url = "http://localhost:9090/theater/users/" + userAccount.getId(); // url of the created student
            URI uri = URI.create(url);
            return Response.created(uri).build();
        }
    }

}
