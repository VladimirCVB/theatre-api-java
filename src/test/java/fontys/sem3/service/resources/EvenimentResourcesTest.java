package fontys.sem3.service.resources;

import fontys.sem3.service.model.Eveniment;
import fontys.sem3.service.model.Seat;
import fontys.sem3.service.repository.JDBCEventsRepository;
import org.junit.jupiter.api.*;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EvenimentResourcesTest {

    @BeforeAll
    static void setUp() {
        EvenimentResources eventRes = new EvenimentResources();

        int id = 1;
        String name = "Test Event";
        String date = "12.10.2020";
        String description = "test description";
        String imgSrc = "none";

        Eveniment event = new Eveniment(id, name, description, date, imgSrc, true);
        event.setSeats(10);

        // Act - Execute the method to be tested
        eventRes.createEveniment(event);
    }

    @AfterAll
    static void tearDown() {
        JDBCEventsRepository eventsRepo = new JDBCEventsRepository();
        eventsRepo.truncateTable();
    }

    @Test
    void TestCreateEveniment() throws SQLException {

        // Arrange - Setup the code to be used
        EvenimentResources eventRes = new EvenimentResources();

        int id = 1;
        String name = "Test Event";
        String date = "12.10.2020";
        String description = "test description";
        String imgSrc = "none";

        Eveniment event = new Eveniment(id, name, description, date, imgSrc, true);
        event.setSeats(10);

        // Act - Execute the method to be tested
        Response response = eventRes.createEveniment(event);

        // Assert - Check if the method postconditions is as expected
        assertEquals(201, response.getStatus());
    }

    @Test
    void TestCreateEvenimentNoName() throws SQLException {

        // Arrange - Setup the code to be used
        EvenimentResources eventRes = new EvenimentResources();

        int id = 1;
        String name = "";
        String date = "12.10.2020";
        String description = "test description";
        String imgSrc = "none";

        Eveniment event = new Eveniment(id, name, description, date, imgSrc, true);
        event.setSeats(10);

        // Act - Execute the method to be tested
        Response response = eventRes.createEveniment(event);

        // Assert - Check if the method postconditions is as expected
        assertEquals(409, response.getStatus());
    }

    @Test
    void TestCreateEvenimentNoDate() throws SQLException {

        // Arrange - Setup the code to be used
        EvenimentResources eventRes = new EvenimentResources();

        int id = 1;
        String name = "Test Title";
        String date = "";
        String description = "test description";
        String imgSrc = "none";

        Eveniment event = new Eveniment(id, name, description, date, imgSrc, true);
        event.setSeats(10);

        // Act - Execute the method to be tested
        Response response = eventRes.createEveniment(event);

        // Assert - Check if the method postconditions is as expected
        assertEquals(409, response.getStatus());
    }

    @Test
    void TestCreateEvenimentNoDescription() throws SQLException {

        // Arrange - Setup the code to be used
        EvenimentResources eventRes = new EvenimentResources();

        int id = 1;
        String name = "Test Title";
        String date = "12.10.2020";
        String description = "";
        String imgSrc = "none";

        Eveniment event = new Eveniment(id, name, description, date, imgSrc, true);
        event.setSeats(10);

        // Act - Execute the method to be tested
        Response response = eventRes.createEveniment(event);

        // Assert - Check if the method postconditions is as expected
        assertEquals(409, response.getStatus());
    }

    @Test
    void TestCreateEvenimentNoImgSrc() throws SQLException {

        // Arrange - Setup the code to be used
        EvenimentResources eventRes = new EvenimentResources();

        int id = 1;
        String name = "Test Title";
        String date = "12.10.2020";
        String description = "test description";
        String imgSrc = "";

        Eveniment event = new Eveniment(id, name, description, date, imgSrc, true);
        event.setSeats(10);

        // Act - Execute the method to be tested
        Response response = eventRes.createEveniment(event);

        // Assert - Check if the method postconditions is as expected
        assertEquals(409, response.getStatus());
    }

    @Test
    void TestGetEvenimentPath() throws SQLException {

        // Arrange - Setup the code to be used
        EvenimentResources eventRes = new EvenimentResources();

        // Act - Execute the method to be tested
        Response event = eventRes.getEvenimentPath(1);

        // Assert - Check if the method postconditions is as expected
        assertEquals(200, event.getStatus());
    }

    @Test
    void TestGetAllEveniments() {

        // Arrange - Setup the code to be used
        EvenimentResources eventRes = new EvenimentResources();

        // Act - Execute the method to be tested
        Response event = eventRes.getAllEveniments();

        // Assert - Check if the method postconditions is as expected
        assertEquals(200, event.getStatus());
    }

    @Test
    void TestUpdateEveniment() {

        // Arrange - Setup the code to be used
        EvenimentResources eventRes = new EvenimentResources();

        // Act - Execute the method to be tested
        int[] seatIds = { 0, 1, 2};
        Response event = eventRes.updateEveniment(2, seatIds);

        // Assert - Check if the method postconditions is as expected
        assertEquals(204, event.getStatus());
    }

    @Test
    void TestDeleteEveniment() {

        // Arrange - Setup the code to be used
        EvenimentResources eventRes = new EvenimentResources();

        // Act - Execute the method to be tested
        Response event = eventRes.deleteEveniment(1);

        // Assert - Check if the method postconditions is as expected
        assertEquals(204, event.getStatus());
    }
}
