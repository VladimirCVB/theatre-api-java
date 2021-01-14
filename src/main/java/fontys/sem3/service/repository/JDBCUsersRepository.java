package fontys.sem3.service.repository;

import fontys.sem3.service.model.*;

import java.sql.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        JDBCTicketsRepository ticketsRepository = new JDBCTicketsRepository();

        try{
            prepStatement = connect.prepareStatement("SELECT * FROM users WHERE id = ?");
            prepStatement.setInt(1, id);
            resultSet = prepStatement.executeQuery();

            while (resultSet.next()){

                int userId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                String password = "";
                String role = resultSet.getString(5);

                UserAccount userAccount;
                userAccount = new UserAccount(userId, email, password, name);
                userAccount.setRole(UserRole.valueOf(role));

                List<Ticket> ticket = ticketsRepository.getTicket(userId);
                userAccount.setTicket(ticket);

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

    public boolean addUserAccount(String name, String email, String password){
        if(!checkEmail(email) && checkUserDetails(name, email, password)){
            try{
                String sql = "INSERT INTO users ( name, email, password, role ) VALUES (?, ?, ?, ?)";
                prepStatement = connect.prepareStatement(sql);
                prepStatement.setString(1, name);
                prepStatement.setString(2, email);
                prepStatement.setString(3, password);
                prepStatement.setString(4, "regular");

                prepStatement.executeUpdate();

                return true;

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    private boolean checkEmail(String email){
        try{
            prepStatement = connect.prepareStatement("SELECT * FROM users WHERE email = ?");
            prepStatement.setString(1, email);
            resultSet = prepStatement.executeQuery();
            String selectedEmail = "";

            while (resultSet.next()){
                selectedEmail = resultSet.getString(1);
            }

            if(selectedEmail != ""){ return true; }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    private boolean checkUserDetails(String name, String email, String password){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);

        if(name != null && !name.isEmpty() && email != null && !email.isEmpty() && password != null && !password.isEmpty()){
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
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
