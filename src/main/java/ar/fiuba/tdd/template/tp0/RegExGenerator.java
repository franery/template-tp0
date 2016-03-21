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

    private ArrayList<String> specialCharacters;

    private void addCharacters() {
        this.specialCharacters = new ArrayList<>();
        this.specialCharacters.add(questionMark);
        this.specialCharacters.add(plus);
        this.specialCharacters.add(asterisk);
        this.specialCharacters.add(leftBracket);
        this.specialCharacters.add(rightBracket);
    }

    public RegExGenerator() {
        this.maxLength = 4;
        this.addCharacters();
    }

    public RegExGenerator(int maxLength) {
        this.maxLength = maxLength;
        this.addCharacters();
    }

    private String getChar(String charSet) {
        Random rand = new Random();
        int value = 0;
        if (charSet.equals(dot)) {
            value = rand.nextInt(maxASCIIchar - firstASCIIchar) + firstASCIIchar;
            return Character.toString((char) value);
        } else {
            if (charSet.length() > 1) {
                if (charSet.charAt(0) == '\\') {
                    return Character.toString(charSet.charAt(1));
                } else {
                    value = rand.nextInt(charSet.length());
                    return Character.toString(charSet.charAt(value));
                }
            }
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

    private String generateBracketSet(RegularExpression regularExpression) throws Exception {
        String regEx = regularExpression.getRegEx();
        int index = regularExpression.getIndex();
        int positionBracket = regEx.indexOf(rightBracket,index);
        if (positionBracket != -1) {
            regularExpression.setPrevious(regEx.substring(index + 1, positionBracket));
            regularExpression.setIndex(positionBracket);
            if (regularExpression.getIndex() == regEx.length() - 1) {
                return getChar(regularExpression.getPrevious());
            }
        } else {
            throw new Exception();
        }
        return "";
    }

    private String generateSpecialString(RegularExpression regularExpression, String current) throws Exception {
        if (current.equals(questionMark) || current.equals(asterisk) || current.equals(plus)) {
            String previous = regularExpression.getPrevious();
            regularExpression.setPrevious("");
            return generateQuantifierString(previous, current);
        }
        if (current.equals(leftBracket)) {
            return generateBracketSet(regularExpression);
        }
        return "";
    }

    private String readLiteral(RegularExpression regularExpression, String current) {
        if (regularExpression.getPrevious().equals(slash)) {
            regularExpression.setPrevious(slash + current);
            return "";
        } else {
            String previous = regularExpression.getPrevious();
            regularExpression.setPrevious(current);
            return previous;
        }
    }

    private String checkBracket(RegularExpression regularExpression, String current) {
        if (current.equals(leftBracket) && !regularExpression.getPrevious().equals(slash)) {
            return regularExpression.getPrevious();
        }
        return "";
    }

    private String generateString(String regEx) throws Exception {
        StringBuilder generatedString = new StringBuilder();
        RegularExpression regularExpression = new RegularExpression(regEx);
        for (int i = 0; i < regEx.length(); i++) {
            regularExpression.setIndex(i);
            String current = Character.toString(regEx.charAt(i));
            generatedString.append(getChar(checkBracket(regularExpression, current)));
            if (specialCharacters.contains(current) && !regularExpression.getPrevious().equals(slash)) {
                generatedString.append(generateSpecialString(regularExpression, current));
                i = regularExpression.getIndex();
            } else {
                generatedString.append(getChar(readLiteral(regularExpression, current)));
                if (i == regEx.length() - 1) {
                    generatedString.append(getChar(regularExpression.getPrevious()));
                }
            }
        }
        return generatedString.toString();
    }

    public List<String> generate(String regEx, int numberOfResults) throws Exception {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < numberOfResults; i++) {
            String string = generateString(regEx);
            strings.add(string);
        }
        return strings;
    }


}