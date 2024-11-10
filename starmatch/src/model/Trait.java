package model;

public class Trait implements HasId {
    private Integer id;
    private Element element;
    private String traitName;

    public Trait(Element element, String traitName, Integer id) {
        this.element = element;
        this.traitName = traitName;
        this.id = id;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public String getTraitName() {
        return traitName;
    }

    public void setTraitName(String traitName) {
        this.traitName = traitName;
    }

    @Override
    public String toString() {
        return "Trait{" +
                "element=" + element +
                ", traitName='" + traitName + '\'' +
                '}';
    }

    @Override
    public Integer getId() {
        return id;
    }
}
