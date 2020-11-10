package fontys.sem3.service.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User testData(){
        String name = "John Doe";
        int age = 18;

        User user = new User(name, age);

        return user;
    }

    @Test
    void TestGetName() {
        // Arrange - Setup the code to be used
        User user = testData();

        // Act - Execute the method to be tested
        user.getName();

        // Assert - Check if the method postconditions is as expected
        assertEquals("John Doe", user.getName());
    }

    @Test
    void getAge() {
        // Arrange - Setup the code to be used
        User user = testData();

        // Act - Execute the method to be tested
        user.getAge();

        // Assert - Check if the method postconditions is as expected
        assertEquals(18, user.getAge());
    }
}