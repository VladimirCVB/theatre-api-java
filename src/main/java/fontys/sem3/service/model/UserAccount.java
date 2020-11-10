package fontys.sem3.service.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserAccount {

    private int id;
    private User user;
    private String email;
    private String password;
    private UserRole role = UserRole.regular;
    private List<Ticket> tickets;

    public UserAccount(int id, String email, String password, User user){

        this.id = id;
        this.email = email;
        this.password = password;
        tickets = new ArrayList<>();
        this.user = user;
    }

    public int getId() {
        return this.id;
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
        UserAccount userAccount = (UserAccount) o;
        return Objects.equals(id, userAccount.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + user.getName() + '\'' +
                '}';
    }
}
