package fontys.sem3.service.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountTest {

    private UserAccount testData(){
        int id = 1;
        User user = new User("John Doe", 18);
        String email = "email@example.com";
        String password = "password";

        UserAccount userAcc = new UserAccount(id, email, password, user);

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
        Ticket ticket = new Ticket();

        userAccount.setTicket(ticket);

        // Assert - Check if the method postconditions is as expected
        assertEquals(ticket, userAccount.getTickets().get(0));
    }
}