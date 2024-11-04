package model;

import java.util.List;

public class NatalChart {
    private List<Planet> planets;

    public NatalChart(List<Planet> planets) {
        this.planets = planets;
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(List<Planet> planets) {
        this.planets = planets;
    }

    @Override
    public String toString() {
        return "NatalChart{" +
                "planets=" + planets +
                '}';
    }
}
