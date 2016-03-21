package ar.fiuba.tdd.template.tp0;

/**
 * Created by fran on 21/03/16.
 */
public class RegularExpression {

    private String regEx;
    private String previous;
    private int index;

    public RegularExpression(String regEx) {
        this.regEx = regEx;
        this.previous = "";
    }

    public String getRegEx() {
        return this.regEx;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getPrevious() {
        return this.previous;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

}
