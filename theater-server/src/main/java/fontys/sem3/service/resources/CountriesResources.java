package fontys.sem3.service.resources;


import fontys.sem3.service.model.Country;
import fontys.sem3.service.model.Student;
import fontys.sem3.service.repository.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/countries")
public class CountriesResources {

    @Context
    private UriInfo uriInfo;
    // this has to be static because the service is stateless:
    private static final FakeDataStore fakeDataStore = new FakeDataStore();

    @GET //GET at http://localhost:XXXX/students/3
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountry(@PathParam("code") String stNr) {
        // StudentsRepository studentsRepository = RepositoryFactory.getStudentsRepository();
        Country country = fakeDataStore.getCountry(stNr);//studentsRepository.get(stNr);
        if (country == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid student number.").build();
        } else {
            return Response.ok(country).build();
        }
    }

    @GET //GET at http://localhost:XXXX/students?
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCountries() {
        List<Country> countries = fakeDataStore.getCountries();

        GenericEntity<List<Country>> entity = new GenericEntity<>(countries) {  };
        return Response.ok(entity).build();
    }


    @DELETE //DELETE at http://localhost:XXXX/students/3
    @Path("/delete/{code}")
    public Response deleteCountry(@PathParam("code") String code) {
        fakeDataStore.deleteCountry(code);
        // Idempotent method. Always return the same response (even if the resource has already been deleted before).
        return Response.noContent().build();
    }

    @POST //POST at http://localhost:XXXX/students/
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCountry(Country country) {
        if (!fakeDataStore.addCountry(country)){
            String entity =  "Country with country code " + country.getCode() + " already exists.";
            return Response.status(Response.Status.CONFLICT).entity(entity).build();
        } else {
            String url = uriInfo.getAbsolutePath() + "/" + country.getCode(); // url of the created student
            URI uri = URI.create(url);
            return Response.created(uri).build();
        }
    }

    @PUT //PUT at http://localhost:XXXX/students/
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update/{code}")
    public Response updateCountry(Country country) {
        // Idempotent method. Always update (even if the resource has already been updated before).
        if (fakeDataStore.updateCountry(country)) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Please provide a valid country code.").build();
        }
    }
}

