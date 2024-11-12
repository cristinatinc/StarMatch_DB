package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user with personal details, login information, and a list of friends.
 * Extends the {@link Person} class to include attributes for email and password.
 */
public class User extends Person {

    private String email;
    private String password;
    private List<User> friends;

    /**
     * Constructs a User with the specified details and initializes an empty friends list.
     *
     * @param id         the unique identifier of the user
     * @param name       the name of the user
     * @param birthDate  the birth date of the user
     * @param birthTime  the birth time of the user
     * @param birthPlace the birth place of the user
     * @param email      the email address of the user
     * @param password   the password of the user
     */
    public User(int id, String name, LocalDate birthDate, LocalTime birthTime, String birthPlace, String email, String password) {
        super(id, name, birthDate, birthTime, birthPlace);
        this.email = email;
        this.password = password;
        this.friends = new ArrayList<User>();
    }

    /**
     * Gets the email of the user.
     *
     * @return the email address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the new email address for the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the new password for the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the list of friends for this user.
     *
     * @return a list of friends
     */
    public List<User> getFriends() {
        return friends;
    }

    /**
     * Sets the list of friends for this user.
     *
     * @param friends the new list of friends for the user
     */
    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    /**
     * Returns a string representation of the user, including their personal details and friends.
     *
     * @return a string representation of the user
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birthDate=" + birthDate +
                ", birthTime=" + birthTime +
                ", birthPlace='" + birthPlace + '\'' +
                ", friends=" + friends +
                '}';
    }

}
