package model;

public class Trait {
    private Element element;
    private String traitName;

    public Trait(Element element, String traitName) {
        this.element = element;
        this.traitName = traitName;
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
}
