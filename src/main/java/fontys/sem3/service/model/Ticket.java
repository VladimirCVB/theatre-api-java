package fontys.sem3.service.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement
public class Ticket {

    private int id;
    private String dateOfPurchase;
    private double price;
    private List<Seat> seats = new ArrayList<Seat>();;

    public Ticket(String dateOfPurchase, List<Seat> seats) {

        this.dateOfPurchase = dateOfPurchase;
        this.seats = seats;
        this.getPrice();
    }

    public Ticket(){}

    public int getId() {
        return this.id;
    }
    public void setId(int id){ this.id = id; }

    public double getPrice() {
        int totalPrice = 0;

        for(int i = 0; i < seats.size(); i++){
            totalPrice += seats.get(i).getPrice();
        }

        price = totalPrice;

        return price;
    }

    public List<Seat> getSeats(){
        return this.seats;
    }
    public void setSeats(List<Seat> seats){ this.seats = seats; }

    public String getDateOfPurchase(){
        return this.dateOfPurchase;
    }
    public void setDateOfPurchase(String dateOfPurchase){ this.dateOfPurchase = dateOfPurchase; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "price='" + price + '\'' +
                ", date of purchase='" + dateOfPurchase + '\'' +
                '}';
    }
}
