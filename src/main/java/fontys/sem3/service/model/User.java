package fontys.sem3.service.model;

import jdk.dynalink.linker.LinkerServices;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class User {

    private String name;
    private int age;

    public User(String name, int age) {

        this.name = name;
        this.age = age;
    }

    public User(){}

    public String getName() {
        return name;
    }
    public void setName(String name){this.name = name;}

    public int getAge() {return age; }
    public void setAge(int age){
        this.age = age;
    }
}
