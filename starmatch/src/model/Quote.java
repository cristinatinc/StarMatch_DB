package model;

public class Quote implements HasId {
    private Element element;
    private String quoteText;
    private Integer id;

    public Quote(Element element, String quoteText, Integer id) {
        this.element = element;
        this.quoteText = quoteText;
        this.id = id;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
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
        return "Quote{" +
                "element=" + element +
                ", quoteText='" + quoteText + '\'' +
                '}';
    }
}
