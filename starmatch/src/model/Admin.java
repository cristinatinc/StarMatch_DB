package model;

import java.util.Date;
import java.util.List;

public class Admin extends User{
    public Admin(int id, String name, Date birthDate, Integer birthTime, String birthPlace, String email, String password, List<Friend> friends) {
        super(id, name, birthDate, birthTime, birthPlace, email, password, friends);
    }
}
