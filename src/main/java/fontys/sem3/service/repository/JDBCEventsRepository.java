package fontys.sem3.service.repository;

import fontys.sem3.service.model.*;

import javax.ws.rs.core.GenericEntity;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCEventsRepository extends JDBCRepository{

    private List<Eveniment> eventsList;

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

    private JDBCEventEarningsRepository eventEarningsRepository = new JDBCEventEarningsRepository();
    private JDBCSeatsRepository seatsRepository  = new JDBCSeatsRepository();

    public List<Eveniment> getEveniments(){

        try{
            statement = connect.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM events");

            eventsList = new ArrayList<>();

            while (resultSet.next()) {

                int eventId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String date = resultSet.getString(3);
                String description = resultSet.getString(4);
                String imgSrc = resultSet.getString(5);
                boolean access = resultSet.getBoolean(6);

                Eveniment event;
                event = new Eveniment(eventId, name, description, date, imgSrc, access);

                eventsList.add(event);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        for(Eveniment event : eventsList){
            event.setSeatsList(getSeats(event.getId()));
        }

        return eventsList;
    }

    public Eveniment getEveniment(int id) {

        try{
            prepStatement = connect.prepareStatement("SELECT * FROM events WHERE id = ?");
            prepStatement.setInt(1, id);
            resultSet = prepStatement.executeQuery();

            eventsList = new ArrayList<>();

            while (resultSet.next()){

                int eventId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String date = resultSet.getString(3);
                String description = resultSet.getString(4);
                String imgSrc = resultSet.getString(5);
                boolean access = resultSet.getBoolean(6);

                Eveniment event;
                event = new Eveniment(eventId, name, description, getSeats(eventId), date, imgSrc, access);

                return event;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public List<Seat> getSeats(int eventId){

        return seatsRepository.getSeats(eventId);

    }

    public boolean updateEveniment(int[] seatIds) {
        for(int i = 0; i < seatIds.length - 1; i++){
            try{
                prepStatement = connect.prepareStatement("UPDATE seats SET available = 0 WHERE id = ?");
                prepStatement.setInt(1, seatIds[i]);
                prepStatement.executeUpdate();
            }
            catch (SQLException e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public boolean addEveniment(Eveniment event){

        if(checkEvenimentData(event)){
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
                addSeats(event.getSeats());

                return true;

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    private boolean checkEvenimentData(Eveniment event){
        if(event.getName() != "" && !event.getName().isEmpty() && event.getDate() != "" && !event.getDate().isEmpty()
                && event.getDescription() != "" && !event.getDescription().isEmpty() && event.getImgSrc() != "" && !event.getImgSrc().isEmpty()){
            return true;
        }

        return false;
    }

    private int getLastEventId(){

        int eventId = 1;

        try{
            prepStatement = connect.prepareStatement("SELECT MAX(id) FROM events");
            resultSet = prepStatement.executeQuery();

            while (resultSet.next()) {

                eventId = resultSet.getInt(1);

                if(eventId == -1){
                    return 1;
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return eventId;
    }

    private boolean addSeats(List<Seat> seats){

       return seatsRepository.addSeats(seats, getLastEventId());
    }

    private boolean addEventEarnings(String event_date, String event_name){

        return eventEarningsRepository.addEventEarnings(event_date, event_name, getLastEventId());

    }

    public boolean deleteEveniment(int id) {
        String seatQuery = "DELETE FROM seats WHERE event_id = ?";
        deleteStatement(seatQuery, id);

        String eventsQuery = "DELETE FROM events WHERE id = ?";
        return deleteStatement(eventsQuery, id);
    }

    private boolean deleteStatement(String query, int id) {
        try{
            String sql = query;
            prepStatement = connect.prepareStatement(sql);
            prepStatement.setInt(1, id);

            prepStatement.executeUpdate();
            //delete seats

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean truncateTable(){
        try{
            String sql = "TRUNCATE TABLE events";
            prepStatement = connect.prepareStatement(sql);
            prepStatement.executeUpdate();

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
}
