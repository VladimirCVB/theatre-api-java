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
    private static String url = "jdbc:mysql://localhost:3306/theater-manager";
    private static String user = "root", pass = "";

    public FakeDataStore() {

    }

    private List<Eveniment> getEventAny(String query){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, pass);
            statement = connect.createStatement();
            resultSet = statement.executeQuery(query);

            Eveniment event;

            while (resultSet.next()){


                int eventId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String date = resultSet.getString(3);
                String description = resultSet.getString(4);
                String imgSrc = resultSet.getString(5);
                boolean access = resultSet.getBoolean(6);

                event = new Eveniment(eventId, name, description, getSeats(eventId), date, imgSrc, access);
                eventsList = new ArrayList<>();
                eventsList.add(event);

                return eventsList;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Eveniment getEveniment(int id) {

        String query = "select * from events where id = " + Integer.toString(id);
        eventsList = getEventAny(query);

        return eventsList.get(0);

    }

    public List<Eveniment> getEveniments() {

        String query = "select * from events";
        eventsList = getEventAny(query);

        return eventsList;

    }

    public List<Seat> getSeats(int eventId){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, pass);
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from seats where event_id = " + eventId);
            seatsList = new ArrayList<>();

            while (resultSet.next()) {

                int seatId = resultSet.getInt(1);
                String number = resultSet.getString(2);
                int price = resultSet.getInt(3);
                boolean available = resultSet.getBoolean(4);

                Seat seat = new Seat(seatId, price, number, available);

                seatsList.add(seat);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return seatsList;
    }

    public boolean updateEveniment(Eveniment eveniment) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, pass);
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery("select * from seats where event_id = 1");

            while (resultSet.next()) {

                resultSet.updateInt("available", 0);
                resultSet.updateRow();
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return true;

        /*Eveniment old = this.getEveniment(eveniment.getId());
        if (old == null) {
            return false;
        }
        old.setSeats(eveniment.getSeats());
        return true;*/
    }

    /*
    public boolean addEveniment(Eveniment eveniment){
        if (this.getEveniment(eveniment.getId()) != null){
            return false;
        }
        evenimentsList.add(eveniment);
        return true;
    }

    public boolean deleteEveniment(int id) {
        Eveniment eveniment = getEveniment(id);
        if (eveniment == null){
            return false;
        }

        return evenimentsList.remove(eveniment);
    }

    public boolean updateEveniment(Eveniment eveniment) {
        Eveniment old = this.getEveniment(eveniment.getId());
        if (old == null) {
            return false;
        }
        old.setSeats(eveniment.getSeats());
        return true;
    }

    public List<UserAccount> getUsers() {return this.users;}

    public UserAccount getUserAccount(int id){
        for (UserAccount userAccount : users) {
            if (userAccount.getId() == id)
                return userAccount;
        }
        return null;
    }*/
}
