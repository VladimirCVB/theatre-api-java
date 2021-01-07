package fontys.sem3.service.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountTest {

    private UserAccount testData(){
        int id = 1;
        String name = "John Doe";
        String email = "email@example.com";
        String password = "password";

        UserAccount userAcc = new UserAccount(id, email, password, name);

        return userAcc;
    }

    @Test
    void TestGetSetId() {
        // Arrange - Setup the code to be used
        UserAccount userAccount = testData();

        // Act - Execute the method to be tested
        userAccount.setId(2);

        // Assert - Check if the method postconditions is as expected
        assertEquals(2, userAccount.getId());
    }

    @Test
    void TestGetSetRole() {
        // Arrange - Setup the code to be used
        UserAccount userAccount = testData();

        // Act - Execute the method to be tested
        userAccount.setRole(UserRole.administrator);

        // Assert - Check if the method postconditions is as expected
        assertEquals(UserRole.administrator, userAccount.getRole());
    }

    @Test
    void TestGetSetTickets() {
        // Arrange - Setup the code to be used
        UserAccount userAccount = testData();

        // Act - Execute the method to be tested
        List<Ticket> tickets = new ArrayList<>();

        userAccount.setTicket(tickets);

        // Assert - Check if the method postconditions is as expected
        assertEquals(tickets, userAccount.getTickets());
    }
}