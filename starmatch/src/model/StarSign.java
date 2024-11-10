package model;

import java.util.List;

public class StarSign implements HasId{
    private Integer id;
    private String starName;
    private Element element;
    private List<Trait> traits;

    public StarSign(String starName, Element element, List<Trait> traits, Integer id) {
        this.starName = starName;
        this.element = element;
        this.traits = traits;
        this.id = id;
    }

    public String getStarName() {
        return starName;
    }

    public void setStarName(String starName) {
        this.starName = starName;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public List<Trait> getTraits() {
        return traits;
    }

    public void setTraits(List<Trait> traits) {
        this.traits = traits;
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
        return "StarSign{" +
                "id=" + id +
                ", starName='" + starName + '\'' +
                ", element=" + element +
                ", traits=" + traits +
                '}';
    }
}
