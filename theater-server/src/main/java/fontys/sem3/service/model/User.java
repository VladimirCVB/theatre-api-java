package fontys.sem3.service.model;

import jdk.dynalink.linker.LinkerServices;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class User {

    private int id;
    private static int idSeeder = 0;
    private String name;
    private LocalDate accountCreatedAt;
    private int age;
    private UserRole role = UserRole.regular;
    private List<Ticket> tickets = new ArrayList<Ticket>();

    public User(String name, int age) {

        this.id = idSeeder;
        this.name = name;
        this.age = age;
        this.accountCreatedAt = LocalDate.now();

        idSeeder++;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {return age; }
    public void setAge(int age){
        this.age = age;
    }

    public UserRole getRole() {return this.role;}
    public void setRole(UserRole role){
        this.role = role;
    }

    public List<Ticket> getTickets() { return tickets; }
    public void addTicket(Ticket ticket){
        this.tickets.add(ticket);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", account creation='" + accountCreatedAt + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
