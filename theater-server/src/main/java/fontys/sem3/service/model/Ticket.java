package fontys.sem3.service.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement
public class Ticket {

    private int id;
    private static int idSeeder = 0;
    private Eveniment eveniment;
    private LocalDate dateOfPurchase;
    private double price;
    private List<Seat> seats = new ArrayList<Seat>();;

    public Ticket(Eveniment eveniment, LocalDate dateOfPurchase, List<Seat> seats) {

        this.id = idSeeder;
        this.eveniment = eveniment;
        this.dateOfPurchase = dateOfPurchase;
        this.price = calculatePrice();
        this.seats = seats;

        idSeeder++;
    }

    private double calculatePrice(){
        int totalPrice = 0;

        for(int i = 0; i < seats.size(); i++){
            totalPrice += seats.get(i).getPrice();
        }

        return totalPrice;
    }

    public int getId() {
        return this.id;
    }

    public Eveniment getEveniment() {
        return this.eveniment;
    }

    public double getPrice() { return price; }

    public List<Seat> getSeats(){
        return this.seats;
    }

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
                ", eveniment='" + eveniment + '\'' +
                ", date of purchase='" + dateOfPurchase + '\'' +
                '}';
    }
}
