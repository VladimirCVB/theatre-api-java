package fontys.sem3.service.resources;


import fontys.sem3.service.model.Country;
import fontys.sem3.service.model.Student;
import fontys.sem3.service.repository.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/students")
public class StudentsResources {

    @Context
    private UriInfo uriInfo;
    // this has to be static because the service is stateless:
    private static final FakeDataStore fakeDataStore = new FakeDataStore();

    @GET //GET at http://localhost:XXXX/students/hello
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello() {
        String msg = " Hello, your resources works!";
        return Response.ok(msg).build();
    }

    @GET //GET at http://localhost:XXXX/students/3
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentPath(@PathParam("id") int stNr) {
        // StudentsRepository studentsRepository = RepositoryFactory.getStudentsRepository();
        Student student = fakeDataStore.getStudent(stNr);//studentsRepository.get(stNr);
        if (student == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid student number.").build();
        } else {
            return Response.ok(student).build();
        }
    }

    @GET //GET at http://localhost:XXXX/students?
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStudents(@QueryParam("country") String countryCode) {
        List<Student> students;
        //If query parameter is missing return all students. Otherwise filter students by given countryCode
        if (uriInfo.getQueryParameters().containsKey("country")){
            Country country = fakeDataStore.getCountry(countryCode);
            if (country == null){ // if country code invalid, return BAD_REQUEST
                return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid country code.").build();
            } else {
                students = fakeDataStore.getStudents(country);
            }
        } else {
            students = fakeDataStore.getStudents();
        }
        GenericEntity<List<Student>> entity = new GenericEntity<>(students) {  };
        return Response.ok(entity).build();
    }


    @DELETE //DELETE at http://localhost:XXXX/students/3
    @Path("/delete/{id}")
    public Response deleteStudent(@PathParam("id") int stNr) {
        fakeDataStore.deleteStudent(stNr);
        // Idempotent method. Always return the same response (even if the resource has already been deleted before).
        return Response.noContent().build();
    }

    @POST //POST at http://localhost:XXXX/students/
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createStudent(Student student) {
        if (!fakeDataStore.add(student)){
            String entity =  "Student with student number " + student.getStudentNumber() + " already exists.";
            return Response.status(Response.Status.CONFLICT).entity(entity).build();
        } else {
            String url = uriInfo.getAbsolutePath() + "/" + student.getStudentNumber(); // url of the created student
            URI uri = URI.create(url);
            return Response.created(uri).build();
        }
    }

    @PUT //PUT at http://localhost:XXXX/students/
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response updateStudent(Student student) {
        // Idempotent method. Always update (even if the resource has already been updated before).
        if (fakeDataStore.update(student)) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Please provide a valid student number.").build();
        }
    }


    @PUT //PUT at http://localhost:XXXX/students/{id}
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Path("{id}")
    public Response updateStudent(@PathParam("id") int stNr,  @FormParam("name") String name, @FormParam("country") String countryCode) {
        Student student = fakeDataStore.getStudent(stNr);
        if (student == null){
            return Response.status(Response.Status.NOT_FOUND).entity("Please provide a valid student number.").build();
        }

        Country country = fakeDataStore.getCountry(countryCode);
        if (country == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid country code.").build();
        }

        student.setName(name);
        student.setCountry(country);
        return Response.noContent().build();
    }

}

