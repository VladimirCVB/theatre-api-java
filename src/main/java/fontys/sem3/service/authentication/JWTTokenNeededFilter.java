package fontys.sem3.service.authentication;

import com.mysql.cj.core.util.Base64Decoder;
import fontys.sem3.service.repository.JDBCUsersRepository;
import fontys.sem3.service.resources.UserResources;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;


@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext){

        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null) {
            //logger.severe("#### invalid authorizationHeader : " + authorizationHeader);
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer=".length()).trim();

        //AUTHORIZATION

        /* Get information about the service method which is being called.
        This information includes the annotated/permitted roles. */
        Method method = resourceInfo.getResourceMethod();
        // if access is allowed for all -> do not check anything further : access is approved for all
        if (method.isAnnotationPresent(PermitAll.class)) { return; }
        // if access is denied for all: deny access
        if (method.isAnnotationPresent(DenyAll.class)) {
            Response response = Response.status(Response.Status.FORBIDDEN).build();
            requestContext.abortWith(response);
            return;
        }

        try {
            // Validate the token
            Key key = UserResources.tokenKey;
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            String userId = body.getSubject();

            if (method.isAnnotationPresent(RolesAllowed.class)) {
                // get allowed roles for this method
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

                /*isUserAllowed : implement this method to check if this user has any of the roles in the rolesSet*/
                /*if not isUserAllowed abort the requestContext with FORBIDDEN response*/

                //if (!isUserAllowed(username, password, rolesSet))
                JDBCUsersRepository user = new JDBCUsersRepository();
                String userRole = user.getUserRole(userId);
                System.out.println(userRole + " " + userId);
                for(String role : rolesSet){
                    if (!role.equals(userRole)) {
                        Response response = Response.status(Response.Status.FORBIDDEN).build();
                        requestContext.abortWith(response);
                    }
                    return;
                }
            }
            //logger.info("#### valid token : " + token);

        } catch (Exception e) {
            //logger.severe("#### invalid token : " + token);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
