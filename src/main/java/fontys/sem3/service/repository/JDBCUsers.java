package fontys.sem3.service.repository;

import fontys.sem3.service.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUsers extends JDBCRepository{

    private Connection connect;

    {
        try {
            connect = this.getConnection();
        } catch (TheaterDatabaseException e) {
            e.printStackTrace();
        }
    }

    private static Statement statement = null;
    private static ResultSet resultSet = null;
    private static PreparedStatement prepStatement = null;

    public UserAccount getUserAccount(int id) {
        try{
            prepStatement = connect.prepareStatement("SELECT * FROM users WHERE id = ?");
            prepStatement.setInt(1, id);
            resultSet = prepStatement.executeQuery();

            while (resultSet.next()){

                int userId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                String password = resultSet.getString(4);
                String role = resultSet.getString(5);

                UserAccount userAccount;
                User user = new User(name, 18);

                return userAccount = new UserAccount(userId, email, password, user);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        return null;
    }
}
