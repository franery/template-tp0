package ar.fiuba.tdd.template.tp0;

import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class RegExGeneratorTest {

    private boolean validate(String regEx, int numberOfResults) throws Exception {
        RegExGenerator generator = new RegExGenerator();
        // TODO: Uncomment parameters
        List<String> results = generator.generate(regEx, numberOfResults);
        // force matching the beginning and the end of the strings
        Pattern pattern = Pattern.compile("^" + regEx + "$");
        return results
                .stream()
                .reduce(true,
                    (acc, item) -> {
                        Matcher matcher = pattern.matcher(item);
                        return acc && matcher.find();
                    },
                    (item1, item2) -> item1 && item2);
    }

    @Test
    public void testPlus() throws Exception {
        assertTrue(validate("ad+",10));
    }

    @Test
    public void testAsterisk() throws Exception {
        assertTrue(validate("p*",10));
    }

    @Test
    public void testQuestionMark() throws Exception {
        assertTrue(validate("av?",10));
    }

    @Test
    public void testAnyCharacter() throws Exception {
        assertTrue(validate(".", 1));
    }

    @Test
    public void testMultipleCharacters() throws Exception {
        assertTrue(validate("...", 1));
    }

    @Test
    public void testCharacterSet() throws Exception {
        assertTrue(validate("[abc]", 10));
       // assertTrue(validate("[abc][sd]", 10));
    }

    @Test
    public void testCharacterSetAndLiteral() throws Exception {
        assertTrue(validate("a[abc]d", 10));
    }

    @Test
    public void testCharacterSetAndQuantifier() throws Exception {
        assertTrue(validate("[abc]*", 10));
    }

    @Test
    public void testLiteral() throws Exception {
        assertTrue(validate("\\@", 10));
    }

    @Test
    public void testLiteralDotCharacter() throws Exception {
        assertTrue(validate("\\@..", 10));
    }

    @Test
    public void testZeroOrOneCharacter() throws Exception {
        assertTrue(validate("\\@.h?", 10));
    }

    @Test
    public void testCharacterSetWithQuantifiers() throws Exception {
        assertTrue(validate("[abc]+", 10));
    }

    @Test
    public void testSlash() throws Exception {
        assertTrue(validate("\\.*", 10));
        assertTrue(validate("\\[*", 10));
    }

    @Test(expected = Exception.class)
    public void badExpression() throws Exception {
        assertTrue(validate("[d",1));
    }

    @Test
    public void complex() throws Exception {
        assertTrue(validate("..+[ab]*d?c",10));
        assertTrue(validate(".\\.+[ab]*d?c",10));
    }
}
