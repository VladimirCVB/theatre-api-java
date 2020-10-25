package fontys.sem3.service.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCRepository {

    private static String url = "jdbc:mysql://localhost:3306/theater-manager";
    private static String user = "root", pass = "";

    public Connection getConnection() throws TheaterDatabaseException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            throw new TheaterDatabaseException("There has been a problem" ,e);
        } catch (SQLException throwables) {
            throw new TheaterDatabaseException("There has been a problem" , throwables);
        }
    }
}
