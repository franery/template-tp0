package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegExGenerator {

    private int maxLength;

    private static final String questionMark = "?";
    private static final String plus = "+";
    private static final String asterisk = "*";
    private static final String dot = ".";
    private static final String leftBracket = "[";
    private static final String rightBracket = "]";
    private static final String slash = "\\";

    private static int firstASCIIchar = 33;
    private static int maxASCIIchar = 127;

    private ArrayList<String> quantifiers;

    private void addQuantifiers() {
        this.quantifiers = new ArrayList<>();
        this.quantifiers.add(questionMark);
        this.quantifiers.add(plus);
        this.quantifiers.add(asterisk);
    }

    public RegExGenerator() {
        this.maxLength = 4;
        this.addQuantifiers();
    }

    public RegExGenerator(int maxLength) {
        this.maxLength = maxLength;
        this.addQuantifiers();
    }

    private String getChar(String charSet) {
        Random rand = new Random();
        int value = 0;
        if (charSet.equals(dot)) {
            value = rand.nextInt(maxASCIIchar - firstASCIIchar) + firstASCIIchar;
            System.out.println("Value: " + value);
            return Character.toString((char) value);
        }
        if (charSet.length() > 1) {
            value = rand.nextInt(charSet.length()) + 1;
            return Character.toString(charSet.charAt(value));
        }
        return charSet;
    }

    private int generateQuantifierValue(String quantifier) {
        Random rand = new Random();
        int value = 0;
        if (quantifier.equals(questionMark)) {
            value = rand.nextInt(2);
        } else {
            if (quantifier.equals(plus)) {
                value = rand.nextInt(maxLength) + 1;
            } else {
                if (quantifier.equals(asterisk)) {
                    value = rand.nextInt(maxLength + 1);
                }
            }
        }
        return value;
    }

    private String generateQuantifierString(String previous, String quantifier) {
        StringBuilder generated = new StringBuilder();
        int value = generateQuantifierValue(quantifier);
        for (int i = 1; i <= value; i++) {
            generated.append(getChar(previous));
        }
        return generated.toString();
    }

    private String generateString(String regEx) {
        StringBuilder generatedString = new StringBuilder();
        String previous = "";
        for (int i = 0; i < regEx.length(); i++) {
            String current = Character.toString(regEx.charAt(i));
            if (quantifiers.contains(current)) {
                generatedString.append(generateQuantifierString(previous, current));
                previous = "";
            } else {
                generatedString.append(previous);
                previous = current;
            }
        }
        return generatedString.toString();
    }

    public List<String> generate(String regEx, int numberOfResults) {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < numberOfResults; i++) {
            String string = generateString(regEx);
            System.out.println(string);
            strings.add(string);
        }
        return strings;
    }


}