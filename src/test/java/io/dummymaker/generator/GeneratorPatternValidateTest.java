package io.dummymaker.generator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.runners.Parameterized.Parameters;

/**
 * Generators Tests
 *
 * @author GoodforGod
 * @since 31.07.2017
 */
@RunWith(Parameterized.class)
public class GeneratorPatternValidateTest {

    private IGenerator generator;

    private Class genClass;

    private Pattern pattern;

    public GeneratorPatternValidateTest(IGenerator generator, Class genClass, Pattern pattern) {
        this.generator = generator;
        this.genClass = genClass;
        this.pattern = pattern;
    }

    @Parameters(name = "{index}: Generator ({0}), Regex {2}")
    public static Collection<Object> data() {
        return Arrays.asList(new Object[][] {
                { new BigDoubleGenerator(), Double.class,   Pattern.compile("-?[0-9]+.[0-9]+") },
                { new CityGenerator(),      String.class,   Pattern.compile("[a-zA-Z\\-]+") },
                { new CompanyGenerator(),   String.class,   Pattern.compile(".+(\\t.+)?") },
                { new CountryGenerator(),   String.class,   Pattern.compile("[a-zA-Z]+(\\s+[a-zA-Z]+)*") },
                { new DoubleGenerator(),    Double.class,   Pattern.compile("1|0.[0-9]+.") },
                { new EmailGenerator(),     String.class,   Pattern.compile("[0-9a-zA-Z\\-]+@[a-zA-Z]+\\.[a-zA-Z]+") },
                { new IntegerGenerator(),   Integer.class,  Pattern.compile("-?[0-9]+") },
                { new LongGenerator(),      Long.class,     Pattern.compile("-?[0-9]+") },
                { new NameGenerator(),      String.class,   Pattern.compile("[a-zA-Z]+") },
                { new NickGenerator(),      String.class,   Pattern.compile("[0-9a-zA-Z\\-]+") },
                { new PassGenerator(),      String.class,   Pattern.compile("[0-9a-zA-Z]{6,}") },
                { new PhoneGenerator(),     String.class,   Pattern.compile("[0-9]\\([0-9]{1,3}\\)[0-9]+") },
                { new PhraseGenerator(),    String.class,   Pattern.compile(".+(\\t.+)?") },
                { new StringGenerator(),    String.class,   Pattern.compile("[0-9a-zA-Z]+") },
                { new TagGenerator(),       String.class,   Pattern.compile("#[0-9a-zA-Z]+") }
        });
    }

    @Test
    public void genValueRegexCheck() {
        Object generated = generator.generate();

        assertNotNull(generated);
        assertTrue(generated.getClass().equals(genClass));
        assertTrue(pattern.matcher(String.valueOf(generated)).matches());
    }
}
