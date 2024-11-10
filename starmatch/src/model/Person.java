package model;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Person implements HasId{
    protected int id;
    protected String name;
    protected LocalDate birthDate;
    protected LocalTime birthTime;
    protected String birthPlace;

    protected Person(int id, String name, LocalDate birthDate, LocalTime birthTime, String birthPlace) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.birthTime = birthTime;
        this.birthPlace = birthPlace;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalTime getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(LocalTime birthTime) {
        this.birthTime = birthTime;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

}
