package fontys.sem3.service.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeatTest {

    private Seat testData(){

        int id = 1;
        String number = "1A";
        int price = 12;

        Seat seat = new Seat(id, price, number, true);

        return seat;
    }

    @Test
    void TestGetSetId() {
        // Arrange - Setup the code to be used
        Seat seat = testData();

        // Act - Execute the method to be tested
        seat.setId(2);

        // Assert - Check if the method postconditions is as expected
        assertEquals(2, seat.getId());
    }

    @Test
    void TestGetSetNumber() {
        // Arrange - Setup the code to be used
        Seat seat = testData();

        // Act - Execute the method to be tested
        seat.setNumber("2A");

        // Assert - Check if the method postconditions is as expected
        assertEquals("2A", seat.getNumber());
    }

    @Test
    void TestGetSetPrice() {
        // Arrange - Setup the code to be used
        Seat seat = testData();

        // Act - Execute the method to be tested
        seat.setPrice(10);

        // Assert - Check if the method postconditions is as expected
        assertEquals(10, seat.getPrice());
    }

    @Test
    void TestGetSetAvailable() {
        // Arrange - Setup the code to be used
        Seat seat = testData();

        // Act - Execute the method to be tested
        seat.setAvailable(false);

        // Assert - Check if the method postconditions is as expected
        assertEquals(false, seat.getAvailable());
    }
}