package fontys.sem3.service.repository;

import fontys.sem3.service.model.Seat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSeatsRepository extends JDBCRepository {

    private static Statement statement = null;
    private static ResultSet resultSet = null;
    private static PreparedStatement prepStatement = null;
    private List<Seat> seatsList;
    private Connection connect;

    {
        try {
            connect = this.getConnection();
        } catch (TheaterDatabaseException e) {
            e.printStackTrace();
        }
    }

    public List<Seat> getSeats(int eventId) {

        String query = "SELECT * FROM seats WHERE event_id = ?";
        List<Seat> seats = getSeatsAction(eventId, query);

        return seats;
    }

    public List<Seat> getSeatsByTicket(int ticketId) {

        String query = "SELECT * FROM seats WHERE ticket_id = ?";
        List<Seat> seats = getSeatsAction(ticketId, query);

        return seats;
    }

    private List<Seat> getSeatsAction(int id, String query) {
        try {
            prepStatement = connect.prepareStatement(query);
            prepStatement.setInt(1, id);

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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return seatsList;
    }

    public boolean addSeats(List<Seat> seats, int lastEventId) {

        if (lastEventId == -1) {
            return false;
        }

        int event_id = lastEventId;

        for (Seat seat : seats) {
            try {
                String sql = "INSERT INTO seats ( number, price, available, event_id ) VALUES (?, ?, ?, ?)";
                prepStatement = connect.prepareStatement(sql);

                prepStatement.setString(1, seat.getNumber());
                prepStatement.setDouble(2, seat.getPrice());
                prepStatement.setBoolean(3, seat.getAvailable());
                prepStatement.setInt(4, event_id);

                prepStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    public boolean updateSeatTicket(int[] seatIds, int ticketId) {
        for(int i = 0; i < seatIds.length - 1; i++){
            try{
                prepStatement = connect.prepareStatement("UPDATE seats SET ticket_id = ? WHERE id = ?");
                prepStatement.setInt(1, ticketId);
                prepStatement.setInt(2, seatIds[i]);
                prepStatement.executeUpdate();
            }
            catch (SQLException e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
