package fontys.sem3.service.repository;

import fontys.sem3.service.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FakeDataStore {

    private final List<Eveniment> evenimentsList = new ArrayList<>();
    private final List<Seat> seatsList = new ArrayList<>();
    private final List<Seat> seatsList2 = new ArrayList<>();
    private final List<Ticket> ticketsList = new ArrayList<>();
    private final List<UserAccount> users = new ArrayList<>();

    public FakeDataStore() {
        //Working on Theater
        for(int i = 0; i < 10; i++){
            Seat seat = new Seat(i + "A", 12.5);
            Seat seat2 = new Seat(i + "A", 13.5);
            seatsList.add(seat);
            seatsList2.add(seat2);
        }

        String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla elit tellus, ultrices id commodo eget, iaculis id nunc. In interdum tellus nisl, vitae rhoncus libero mollis ac. Nulla orci magna, ultricies ut tristique at, sodales id purus. Donec euismod scelerisque orci, ac rhoncus massa ultricies et. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Curabitur non massa vitae nisi luctus cursus a id purus. Mauris purus urna, bibendum et nisi quis, dictum posuere massa. Sed ornare gravida quam ut venenatis. Donec convallis felis dolor, a porta nulla facilisis in. Morbi rhoncus non ligula sit amet venenatis. Pellentesque semper diam quis felis volutpat tempus. Integer lacus sem, porttitor et feugiat vel, pharetra commodo mauris. Morbi tristique dolor vel lectus vulputate gravida. Fusce eu turpis eget felis pellentesque mollis.";

        LocalDate dateOfEvent = LocalDate.of(2020, 10, 12);
        String imgSrc = "https://www.laiasi.ro/wp-content/uploads/2018/02/Teatrul-National-Iasi-inaugurat-la-1-decembrie-1896-laiasi.ro_.jpg";

        Eveniment eveniment = new Eveniment("Romeo and Juliet", description, seatsList, dateOfEvent, imgSrc);
        evenimentsList.add(eveniment);
        Eveniment eveniment2 = new Eveniment("Romeo and Juliet 2", description, seatsList2, dateOfEvent, imgSrc);
        eveniment2.setAccess(false);
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
