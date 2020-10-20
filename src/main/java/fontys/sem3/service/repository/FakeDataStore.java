package fontys.sem3.service.repository;

import fontys.sem3.service.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FakeDataStore {

    private List<Seat> seatsList;
    private List<Eveniment> eventsList;

    private static Connection connect = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;
    private static PreparedStatement prepStatement = null;
    private static String url = "jdbc:mysql://localhost:3306/theater-manager";
    private static String user = "root", pass = "";

    public FakeDataStore() {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Eveniment> getEveniments(){

        try{
            statement = connect.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM events");

            Eveniment event;
            eventsList = new ArrayList<>();

            while (resultSet.next()){

                int eventId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String date = resultSet.getString(3);
                String description = resultSet.getString(4);
                String imgSrc = resultSet.getString(5);
                boolean access = resultSet.getBoolean(6);

                event = new Eveniment(eventId, name, description, getSeats(eventId), date, imgSrc, access);

                eventsList.add(event);

            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        return eventsList;
    }

    public Eveniment getEveniment(int id) {

        try{
            prepStatement = connect.prepareStatement("SELECT * FROM events WHERE id = ?");
            prepStatement.setInt(1, id);
            resultSet = prepStatement.executeQuery();

            Eveniment event;
            eventsList = new ArrayList<>();

            while (resultSet.next()){

                int eventId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String date = resultSet.getString(3);
                String description = resultSet.getString(4);
                String imgSrc = resultSet.getString(5);
                boolean access = resultSet.getBoolean(6);

                event = new Eveniment(eventId, name, description, getSeats(eventId), date, imgSrc, access);

                eventsList.add(event);

            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        return eventsList.get(0);

    }

    public List<Seat> getSeats(int eventId){
        try{
            prepStatement = connect.prepareStatement("SELECT * FROM seats WHERE event_id = ?");
            prepStatement.setInt(1, eventId);

            resultSet = prepStatement.executeQuery();
            seatsList = new ArrayList<>();

            while (resultSet.next()) {

                int seatId = resultSet.getInt(1);
                String number = resultSet.getString(2);
                int price = resultSet.getInt(3);
                boolean available = resultSet.getBoolean(4);

                Seat seat = new Seat(seatId, price, number, available);

                seatsList.add(seat);
            }
        }  catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return seatsList;
    }

    public boolean updateEveniment(int eventId) {

        try{
            prepStatement = connect.prepareStatement("UPDATE seats SET available = 0 WHERE event_id = ?");
            prepStatement.setInt(1, eventId);
            prepStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean addEveniment(Eveniment event){
        try{
            String sql = "INSERT INTO events ( name, date, description, imgSrc, access ) VALUES (?, ?, ?, ?, ?)";
            prepStatement = connect.prepareStatement(sql);
            prepStatement.setString(1, event.getName());
            prepStatement.setString(2, event.getDate());
            prepStatement.setString(3, event.getDescription());
            prepStatement.setString(4, event.getImgSrc());
            prepStatement.setBoolean(5, event.getAccess());

            prepStatement.executeUpdate();

            addEventEarnings(event.getDate(), event.getName());

            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    private int getLastEventId(){
        try{
            prepStatement = connect.prepareStatement("SELECT MAX(id) FROM events");
            resultSet = prepStatement.executeQuery();

            while (resultSet.next()) {

                return resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return -1;
    }

    private boolean addEventEarnings(String event_date, String event_name){

        if(getLastEventId() == -1){
            return false;
        }

        int event_id = getLastEventId();

        try{
            String sql = "INSERT INTO events_earnings ( sold_seats, earnings, event_id, event_date, event_name ) VALUES (0, 0, ?, ?, ?)";
            prepStatement = connect.prepareStatement(sql);
            prepStatement.setInt(1, event_id);
            prepStatement.setString(2, event_date);
            prepStatement.setString(3, event_name);

            prepStatement.executeUpdate();
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean deleteEveniment(int id) {
        try{
            String sql = "DELETE FROM events WHERE id = ?";
            prepStatement = connect.prepareStatement(sql);
            prepStatement.setInt(1, id);

            prepStatement.executeUpdate();

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

/*

    public List<UserAccount> getUsers() {return this.users;}

    public UserAccount getUserAccount(int id){
        for (UserAccount userAccount : users) {
            if (userAccount.getId() == id)
                return userAccount;
        }
        return null;
    }*/
}
