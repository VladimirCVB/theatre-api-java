package fontys.sem3.service.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EvenimentTest {

    private Eveniment testData(){
        int id = 1;
        String name = "Test Event";
        String description = "test description";
        String date = "12.10.2020";
        String imgSrc = "none";

        List<Seat> seatsList = new ArrayList<>();
        Seat seat = new Seat(1, 12, "1A", true);

        Eveniment event = new Eveniment(id, name, description, seatsList, date, imgSrc, true);

        return event;
    }

    @Test
    void TestConstructor() {

        // Arrange - Setup the code to be used
        List<Seat> seatsList = new ArrayList<>();
        Seat seat = new Seat(1, 12, "1A", true);

        Eveniment eventOne = new Eveniment(1, "Test Event", "test description", seatsList, "12.10.2020", "none", true);

        // Act - Execute the method to be tested
        Eveniment eventTwo = testData();

        // Assert - Check if the method postconditions is as expected
        assertEquals(eventOne, eventTwo);
    }

    @Test
    void TestGetSetId() {

        // Arrange - Setup the code to be used
        Eveniment event = new Eveniment();

        // Act - Execute the method to be tested
        event.setId(1);

        // Assert - Check if the method postconditions is as expected
        assertEquals(1, event.getId());
    }

    @Test
    void TestGetSetName() {

        // Arrange - Setup the code to be used
        Eveniment event = testData();

        // Act - Execute the method to be tested
        event.setName("New Event Name");

        // Assert - Check if the method postconditions is as expected
        assertEquals("New Event Name", event.getName());
    }

    @Test
    void TestGetSetDescription() {

        // Arrange - Setup the code to be used
        Eveniment event = testData();

        // Act - Execute the method to be tested
        event.setDescription("New Event Description");

        // Assert - Check if the method postconditions is as expected
        assertEquals("New Event Description", event.getDescription());
    }

    @Test
    void TestGetSetDate() {

        // Arrange - Setup the code to be used
        Eveniment event = testData();

        // Act - Execute the method to be tested
        event.setDate("12.11.2020");

        // Assert - Check if the method postconditions is as expected
        assertEquals("12.11.2020", event.getDate());
    }

    @Test
    void TestGetSetImgSrc() {

        // Arrange - Setup the code to be used
        Eveniment event = testData();

        // Act - Execute the method to be tested
        event.setImgSrc("imgSrcUnknown");

        // Assert - Check if the method postconditions is as expected
        assertEquals("imgSrcUnknown", event.getImgSrc());
    }

    @Test
    void TestGetSetAccess() {

        // Arrange - Setup the code to be used
        Eveniment event = testData();

        // Act - Execute the method to be tested
        event.setAccess(false);

        // Assert - Check if the method postconditions is as expected
        assertEquals(false, event.getAccess());
    }

    @Test
    void TestGetSetPrice() {

        // Arrange - Setup the code to be used
        Eveniment event = testData();

        // Act - Execute the method to be tested
        event.setPrice(12);

        // Assert - Check if the method postconditions is as expected
        assertEquals(12, event.getPrice());
    }

    @Test
    void TestGetSetSeats() {

        // Arrange - Setup the code to be used
        Eveniment event = testData();

        String number = "3A";

        // Act - Execute the method to be tested
        event.setSeats(30);

        // Assert - Check if the method postconditions is as expected
        assertEquals(number, event.getSeats().get(20).getNumber());
    }
}