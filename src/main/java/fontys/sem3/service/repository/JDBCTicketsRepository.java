package fontys.sem3.service.repository;

import fontys.sem3.service.model.*;

import java.sql.*;
import java.util.List;

public class JDBCTicketsRepository extends JDBCRepository{

    private Connection connect;

    {
        try {
            connect = this.getConnection();
        } catch (TheaterDatabaseException e) {
            e.printStackTrace();
        }
    }

    private static ResultSet resultSet = null;
    private static PreparedStatement prepStatement = null;

    public Ticket getTicket(int id) {

        JDBCSeatsRepository seatsRepository = new JDBCSeatsRepository();

        try{
            prepStatement = connect.prepareStatement("SELECT * FROM tickets WHERE user_id = ?");
            prepStatement.setInt(1, id);
            resultSet = prepStatement.executeQuery();

            while (resultSet.next()){

                int ticketId = resultSet.getInt(1);
                String dateOfPurchase = resultSet.getString(2);
                List<Seat> seats = seatsRepository.getSeatsByTicket(ticketId);

                Ticket ticket;
                ticket = new Ticket(dateOfPurchase, seats);
                ticket.getPrice();

                return ticket;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        return null;
    }
}
