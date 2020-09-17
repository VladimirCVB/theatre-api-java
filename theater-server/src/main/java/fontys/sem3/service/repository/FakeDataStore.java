package fontys.sem3.service.repository;

import fontys.sem3.service.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FakeDataStore {

    private final List<Country> countryList = new ArrayList<>();
    private final List<Student> studentList = new ArrayList<>();

    private final List<Eveniment> evenimentsList = new ArrayList<>();
    private final List<Seat> seatsList = new ArrayList<>();
    private final List<Ticket> ticketsList = new ArrayList<>();

    public FakeDataStore() {
        // work this out better, add few more countries and students
        Country netherlands = new Country("NL", "The Netherlands");
        Country bulgaria = new Country("BG", "Bulgaria");
        Country china = new Country("CHN", "China");
        countryList.add(netherlands);
        countryList.add(bulgaria);
        countryList.add(china);

        studentList.add(new Student(1, "Joe Smith",netherlands));
        studentList.add(new Student(2, "Ann Johnsson", bulgaria));
        studentList.add(new Student(3, "Ann Johnsson", bulgaria));
        studentList.add(new Student(4, "Miranda Winslet", china));

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

    public List<Student> getStudents(Country country) {
        List<Student> filetered = new ArrayList<>();
        for (Student student : studentList) {
            if (student.getCountry().equals(country)) {
                filetered.add(student);
            }
        }
        return filetered;
    }
}
