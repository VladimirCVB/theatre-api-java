package fontys.sem3.service.resources;

import fontys.sem3.service.model.UserAccount;
import fontys.sem3.service.repository.JDBCEventsRepository;
import fontys.sem3.service.repository.JDBCUsersRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserResourcesTest {

    private static UserAccount testData(){
        int id = 1;
        String name = "John Doe";
        String email = "johndoe@example.com";
        String password = "testPassword";

        UserAccount userAccount = new UserAccount(id, name, email, password);

        return userAccount;
    }

    @BeforeAll
    static void setUp() {
        UserResources userRes = new UserResources();

        UserAccount userAccount = testData();

        userRes.createAccount(userAccount);
    }

    @AfterAll
    static void tearDown() {
        JDBCUsersRepository usersRepository = new JDBCUsersRepository();
        usersRepository.truncateTable();
    }

    @Test
    void testGetUserAccount() throws SQLException {
        // Arrange - Setup the code to be used
        UserResources userRes = new UserResources();

        // Act - Execute the method to be tested
        Response response = userRes.getUserAccount(1);

        // Assert - Check if the method postconditions is as expected
        assertEquals(200, response.getStatus());
    }

    @Test
    void testGetUserAccounts() {
        // Arrange - Setup the code to be used
        UserResources userRes = new UserResources();

        // Act - Execute the method to be tested
        Response response = userRes.getUserAccounts();

        // Assert - Check if the method postconditions is as expected
        assertEquals(200, response.getStatus());
    }

    @Test
    void testUpdateAccount() {
        // Arrange - Setup the code to be used
        UserResources userRes = new UserResources();

        // Act - Execute the method to be tested
        Response response = userRes.updateAccount(1, "newPassword123");

        // Assert - Check if the method postconditions is as expected
        assertEquals(204, response.getStatus());
    }

    @Test
    void testDeleteUser() {
        // Arrange - Setup the code to be used
        UserResources userRes = new UserResources();

        // Act - Execute the method to be tested
        Response response = userRes.deleteUser(1);

        // Assert - Check if the method postconditions is as expected
        assertEquals(204, response.getStatus());
    }

    @Test
    void testCreateAccount() {
        // Arrange - Setup the code to be used
        UserResources userRes = new UserResources();

        // Act - Execute the method to be tested
        UserAccount userAccount = testData();
        Response response = userRes.createAccount(userAccount);

        // Assert - Check if the method postconditions is as expected
        assertEquals(201, response.getStatus());
    }
}