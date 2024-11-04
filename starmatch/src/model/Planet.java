package model;

public class Planet implements HasId{
    private Integer id;
    private StarSign sign;
    private String planetName;

    public Planet(String planetName, StarSign sign, Integer id) {
        this.planetName = planetName;
        this.sign = sign;
        this.id = id;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public StarSign getSign() {
        return sign;
    }

    public void setSign(StarSign sign) {
        this.sign = sign;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "id=" + id +
                ", sign=" + sign +
                ", planetName='" + planetName + '\'' +
                '}';
    }
}
