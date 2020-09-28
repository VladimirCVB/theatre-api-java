package fontys.sem3.service.repository;

import fontys.sem3.service.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FakeDataStore {

    private final List<Eveniment> evenimentsList = new ArrayList<>();
    private final List<Seat> seatsList = new ArrayList<>();
    private final List<Ticket> ticketsList = new ArrayList<>();
    private final List<UserAccount> users = new ArrayList<>();

    public FakeDataStore() {
        //Working on Theater
        for(int i = 0; i < 10; i++){
            Seat seat = new Seat(i + "A", 12.5);
            seatsList.add(seat);
        }

        Eveniment eveniment = new Eveniment("Romeo and Juliet", "Unique spectacle", seatsList);
        evenimentsList.add(eveniment);
        Eveniment eveniment2 = new Eveniment("Romeo and Juliet 2", "Unique spectacle", seatsList);
        evenimentsList.add(eveniment2);

        Ticket ticket = new Ticket(eveniment, LocalDate.now(), seatsList);
        ticketsList.add(ticket);

        User user = new User("Johnny Test", 18);

        UserAccount userAccount = new UserAccount("johny@example.com", "pass", user);
        userAccount.addTicket(ticket);

        users.add(userAccount);
    }

    public Eveniment getEveniment(int id){
        for (Eveniment eveniment : evenimentsList) {
            if (eveniment.getId() == id)
                return eveniment;
        }
        return null;
    }

    public List<Eveniment> getEveniments(){ return evenimentsList; }

    public boolean addEveniment(Eveniment eveniment){
        if (this.getEveniment(eveniment.getId()) != null){
            return false;
        }
        evenimentsList.add(eveniment);
        return true;
    }

    public boolean deleteEveniment(int id) {
        Eveniment eveniment = getEveniment(id);
        if (eveniment == null){
            return false;
        }

        return evenimentsList.remove(eveniment);
    }

    public boolean updateEveniment(Eveniment eveniment) {
        Eveniment old = this.getEveniment(eveniment.getId());
        if (old == null) {
            return false;
        }
        old.setDescription(eveniment.getDescription());
        return true;
    }

    public List<UserAccount> getUsers() {return this.users;}

    public UserAccount getUserAccount(int id){
        for (UserAccount userAccount : users) {
            if (userAccount.getId() == id)
                return userAccount;
        }
        return null;
    }
}
