package fontys.sem3.service.resources;


import fontys.sem3.service.model.Country;
import fontys.sem3.service.model.Student;
import fontys.sem3.service.repository.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/seats")
public class SeatResources {
    @Context
    private UriInfo uriInfo;
    // this has to be static because the service is stateless:
    private static final FakeDataStore fakeDataStore = new FakeDataStore();


}
