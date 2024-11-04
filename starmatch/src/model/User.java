package model;

import java.util.Date;
import java.util.List;

public class User extends Person {

    private String email;
    private String password;
    private List<Friend> friends;

    public User(int id, String name, Date birthDate, Integer birthTime, String birthPlace, String email, String password, List<Friend> friends) {
        super(id, name, birthDate, birthTime, birthPlace);
        this.email = email;
        this.password = password;
        this.friends = friends;
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

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
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
