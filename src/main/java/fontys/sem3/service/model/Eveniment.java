package fontys.sem3.service.model;

import jdk.dynalink.linker.LinkerServices;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Eveniment {

    private int id;
    private String name;
    private String date;
    private String description;
    private List<Seat> seats = new ArrayList<Seat>();
    private String imgSrc;
    private Boolean access;
    private int price;


    public Eveniment() {

    }

    public Eveniment(int id, String name, String description, List<Seat> seats, String date, String imgSrc, boolean access) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.seats = seats;
        this.imgSrc = imgSrc;
        this.access = access;
    }

    public Eveniment(int id, String name, String description, String date, String imgSrc, boolean access) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.imgSrc = imgSrc;
        this.access = access;
    }

    public int getId() {
        return this.id;
    }
    public void setId(int id){this.id = id;}

    public String getName() {
        return name;
    }
    public void setName(String name){this.name = name;}

    public String getDescription() {return description; }
    public void setDescription(String description){
        this.description = description;
    }

    public String getDate() {return date;}
    public void setDate(String date){this.date = date;}

    public String getImgSrc() {return imgSrc;}
    public void setImgSrc(String imgSrc){this.imgSrc = imgSrc;}

    public Boolean getAccess() {return access;}
    public void setAccess(Boolean access){ this.access = access; }

    public double getPrice(){return price;}
    public void setPrice(int price){this.price = price;}

    public List<Seat> getSeats() { return seats; }
    public void setSeats(int numberOfSeats){

        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        int rows = Math.floorDiv(numberOfSeats, 10);

        for(int i = 1; i <= rows; i++){
            for(int y = 0; y < letters.length; y++){
                String number = String.valueOf(i) + letters[y];
                Seat seat = new Seat(this.price, number);

                seats.add(seat);
            }
        }
    }
    public void setSeatsList(List<Seat> seats){this.seats = seats;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Eveniment eveniment = (Eveniment) o;
        return Objects.equals(id, eveniment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Eveniment{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                '}';
    }
}
