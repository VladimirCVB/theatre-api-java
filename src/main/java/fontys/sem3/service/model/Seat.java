package fontys.sem3.service.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.Objects;

@XmlRootElement
public class Seat {

    private int id;
    private static int idSeeder = 0;
    private String number;
    private boolean available = true;
    private double price;

    public Seat(String number, double price) {

        this.id = idSeeder;
        this.number = number;
        this.price = price;

        idSeeder++;
    }

    public Seat(String number, double price, int id, boolean available) {

        this.number = number;
        this.price = price;
        this.id = id;
        this.available = available;

    }

    public Seat(){

    }

    public int getId() {
        return this.id;
    }
    public void setId(int id) {this.id = id;}

    public String getNumber() {
        return number;
    }
    public void setNumber(String number){this.number = number;}

    public double getPrice() {return price; }
    public void setPrice(double newPrice){
        this.price = newPrice;
    }

    public boolean getAvailable() {return this.available; }
    public void setAvailable(boolean available){
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(id, seat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seat number='" + number + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
