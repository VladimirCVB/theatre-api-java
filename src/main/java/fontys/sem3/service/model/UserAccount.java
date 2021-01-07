package fontys.sem3.service.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserAccount {

    private int id;
    private String name;
    private String email;
    private String password;
    private UserRole role = UserRole.regular;
    private List<Ticket> tickets;

    public UserAccount(int id, String email, String password, String name){

        this.id = id;
        this.email = email;
        this.password = password;
        tickets = new ArrayList<>();
        this.name = name;
    }

    public UserAccount(){}

    public int getId() {
        return this.id;
    }
    public void setId(int id){ this.id = id; }

    public String getEmail() { return this.email; }
    public void setEmail(String email){ this.email = email; }

    public String getPassword() { return this.password; }
    public void setPassword(String password){ this.password = password; }

    public UserRole getRole() {return this.role;}
    public void setRole(UserRole role){
        this.role = role;
    }

    public List<Ticket> getTickets() { return tickets; }
    public void setTicket(List<Ticket> ticketsList){
        this.tickets = ticketsList;
    }

    public String getName(){ return this.name; }
    public void setName(String name) { this.name = name; }

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
                ", name='" + name + '\'' +
                '}';
    }
}
