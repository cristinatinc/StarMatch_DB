package model;

import java.util.Date;

public class Friend extends Person{

    protected Friend(int id, String name, Date birthDate, Integer birthTime, String birthPlace) {
        super(id, name, birthDate, birthTime, birthPlace);
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", birthTime=" + birthTime +
                '}';
    }
}
