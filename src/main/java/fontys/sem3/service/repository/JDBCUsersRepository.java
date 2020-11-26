package fontys.sem3.service.repository;

import fontys.sem3.service.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUsersRepository extends JDBCRepository{

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
                userAccount = new UserAccount(userId, email, password, name);

                return userAccount;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public int loginUser(String email, String password) {

        int userId = -1;

        try{
            prepStatement = connect.prepareStatement("SELECT id FROM users WHERE email = ? AND password = ?");
            prepStatement.setString(1, email);
            prepStatement.setString(2, password);
            resultSet = prepStatement.executeQuery();

            while (resultSet.next()){

                userId = resultSet.getInt("id");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return userId;
        }

        return userId;
    }

    public String getUserRole(String id) {
        try{
            prepStatement = connect.prepareStatement("SELECT role FROM users WHERE id = ?");
            prepStatement.setString(1, id);
            resultSet = prepStatement.executeQuery();

            while (resultSet.next()){

                String role = resultSet.getString(1);

                return role;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public boolean updateUser(int accountId, String passwordValue){
        try{
            prepStatement = connect.prepareStatement("UPDATE users SET password = ? WHERE id = ?");
            prepStatement.setInt(1, accountId);
            prepStatement.setString(2, passwordValue);
            prepStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteUser(int id){
        try{
            prepStatement = connect.prepareStatement("DELETE FROM users WHERE id = ?");
            prepStatement.setInt(1, id);

            prepStatement.executeUpdate();

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean addUserAccount(UserAccount userAccount){
        try{
            String sql = "INSERT INTO users ( name, email, password, role ) VALUES (?, ?, ?, ?)";
            prepStatement = connect.prepareStatement(sql);
            prepStatement.setString(1, userAccount.getName());
            prepStatement.setString(2, userAccount.getEmail());
            prepStatement.setString(3, userAccount.getPassword());
            prepStatement.setString(4, userAccount.getRole().name());

            prepStatement.executeUpdate();

            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean truncateTable(){
        try{
            String sql = "TRUNCATE TABLE users";
            prepStatement = connect.prepareStatement(sql);
            prepStatement.executeUpdate();

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
}
