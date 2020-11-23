package fontys.sem3.service.resources;


import fontys.sem3.service.model.UserAccount;
import fontys.sem3.service.repository.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.security.Key;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Logger;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("/users")
public class UserResources {
    @Context
    private UriInfo uriInfo;
    // this has to be static because the service is stateless:
    private static final JDBCUsersRepository JDBC_USERS = new JDBCUsersRepository();


    //private static Logger logger;

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

    //JWT
    @POST
    @Path("/login")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON)
    public Response authenticateUser(@FormParam("email") String email,
                                     @FormParam("password") String password) {
        try {

            //logger.info("#### login/password : " + "john@example.com" + "/" + password);

            // Authenticate the user using the credentials provided
            int userId = JDBC_USERS.loginUser("john@example.com", password);

            if (userId == -1) {
                Response response = Response.status(Response.Status.UNAUTHORIZED).entity("Invalid username and/or password. " + email + " " + password + userId).build();
                return response;
            }

            // Issue a token for the user
            UserAccount userAccount = JDBC_USERS.getUserAccount(userId);
            String token = issueToken(userAccount.getName());

            // Return the token on the response
            return Response.ok(token).header(AUTHORIZATION, "Bearer " + token).build();

        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }

    private String issueToken(String name) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String jwtToken = Jwts.builder()
                .setSubject(name)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        //logger.info("#### generating token for a key : " + jwtToken + " - " + key);
        return jwtToken;
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
