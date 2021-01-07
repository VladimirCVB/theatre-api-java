package fontys.sem3.service.repository;

import fontys.sem3.service.model.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public List<Ticket> getTicket(int id) {

        JDBCSeatsRepository seatsRepository = new JDBCSeatsRepository();
        List<Ticket> ticketsList = new ArrayList<>();

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

                ticketsList.add(ticket);
            }

            return ticketsList;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public int addTicket(String userId){
        try{
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String currentDate = formatter.format(date);

            String sql = "INSERT INTO tickets ( dateOfPurchase, price, user_id ) VALUES (?, ?, ?)";
            prepStatement = connect.prepareStatement(sql);
            prepStatement.setString(1, currentDate);
            prepStatement.setString(2, "0");
            prepStatement.setString(3, userId);

            prepStatement.executeUpdate();

            int ticketId = getLastTicketId();
            return ticketId;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return -1;
    }

    private int getLastTicketId(){

        int ticketId = 1;

        try{
            prepStatement = connect.prepareStatement("SELECT MAX(id) FROM tickets");
            resultSet = prepStatement.executeQuery();

            while (resultSet.next()) {

                ticketId = resultSet.getInt(1);

                if(ticketId == -1){
                    return 1;
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return ticketId;
    }
}
