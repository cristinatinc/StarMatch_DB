package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class User extends Person {

    private String email;
    private String password;
    private List<User> friends;

    public User(int id, String name, LocalDate birthDate, LocalTime birthTime, String birthPlace, String email, String password) {
        super(id, name, birthDate, birthTime, birthPlace);
        this.email = email;
        this.password = password;
        this.friends = new ArrayList<User>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", friends=" + friends +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", birthTime=" + birthTime +
                '}';
    }
}
