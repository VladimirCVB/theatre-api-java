package fontys.sem3.service.repository;

import java.sql.*;

public class JDBCEventEarningsRepository extends JDBCRepository {

    private static Statement statement = null;
    private static ResultSet resultSet = null;
    private static PreparedStatement prepStatement = null;

    private Connection connect;

    {
        try {
            connect = this.getConnection();
        } catch (TheaterDatabaseException e) {
            e.printStackTrace();
        }
    }

    public boolean addEventEarnings(String event_date, String event_name, int lastEventId){

        if(lastEventId == -1){
            return false;
        }

        int event_id = lastEventId;

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
}
