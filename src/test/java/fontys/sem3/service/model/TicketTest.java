package fontys.sem3.service.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    private Ticket testData(){

        int id = 1;
        String date = "12.10.2022";

        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat(12, "2A");
        seats.add(seat);

        Ticket ticket = new Ticket(date, seats);

        return ticket;
    }

    @Test
    void TestGetSetId() {
        // Arrange - Setup the code to be used
        Ticket ticket = testData();

        // Act - Execute the method to be tested
        ticket.setId(2);

        // Assert - Check if the method postconditions is as expected
        assertEquals(2, ticket.getId());
    }

    @Test
    void TestGetPrice() {
        // Arrange - Setup the code to be used
        Ticket ticket = testData();

        // Act - Execute the method to be tested
        ticket.getPrice();

        // Assert - Check if the method postconditions is as expected
        assertEquals(12, ticket.getPrice());
    }

    @Test
    void TestGetSetSeats() {
        // Arrange - Setup the code to be used
        Ticket ticket = testData();

        // Act - Execute the method to be tested
        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat(10, "7B");
        seats.add(seat);

        ticket.setSeats(seats);

        // Assert - Check if the method postconditions is as expected
        assertEquals(seats, ticket.getSeats());
    }
}