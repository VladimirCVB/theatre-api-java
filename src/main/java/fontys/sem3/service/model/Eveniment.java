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
    private static int idSeeder = 0;
    private String name;
    private LocalDate date;
    private String description;
    private List<Seat> seats = new ArrayList<Seat>();

    public Eveniment(String name, String description, List<Seat> seats) {
        this.id = idSeeder;
        this.name = name;
        this.description = description;
        this.date = LocalDate.now();
        this.seats = seats;

        idSeeder++;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {return description; }
    public void setDescription(String description){
        this.description = description;
    }

    public LocalDate getDate() {return date;}

    public List<Seat> getSeats() { return seats; }

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
                '}';
    }
}
