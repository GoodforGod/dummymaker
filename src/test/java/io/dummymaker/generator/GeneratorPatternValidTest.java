package io.dummymaker.generator;

import io.dummymaker.generator.impl.BooleanGenerator;
import io.dummymaker.generator.impl.UuidGenerator;
import io.dummymaker.generator.impl.collection.impl.ListGenerator;
import io.dummymaker.generator.impl.collection.impl.MapGenerator;
import io.dummymaker.generator.impl.collection.impl.SetGenerator;
import io.dummymaker.generator.impl.number.DoubleBigGenerator;
import io.dummymaker.generator.impl.number.DoubleGenerator;
import io.dummymaker.generator.impl.number.IntegerGenerator;
import io.dummymaker.generator.impl.number.LongGenerator;
import io.dummymaker.generator.impl.string.*;
import io.dummymaker.generator.impl.time.impl.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
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
public class GeneratorPatternValidTest {

    private IGenerator generator;

    private Class genClass;

    private Pattern pattern;

    public GeneratorPatternValidTest(IGenerator generator, Class genClass, Pattern pattern) {
        this.generator = generator;
        this.genClass = genClass;
        this.pattern = pattern;
    }

    @Parameters(name = "{index}: Generator ({0}), Regex {2}")
    public static Collection<Object> data() {
        return Arrays.asList(new Object[][] {
                { new DoubleBigGenerator(),     Double.class,   Pattern.compile("-?[0-9]+.[0-9]+") },
                { new DoubleGenerator(),        Double.class,   Pattern.compile("1|0.[0-9]+.") },
                { new IntegerGenerator(),       Integer.class,  Pattern.compile("-?[0-9]+") },
                { new LongGenerator(),          Long.class,     Pattern.compile("-?[0-9]+") },
                { new IdBigGenerator(),         String.class,   Pattern.compile("[0-9a-zA-Z]+") },
                { new CityGenerator(),          String.class,   Pattern.compile("[a-zA-Z\\-]+") },
                { new CompanyGenerator(),       String.class,   Pattern.compile(".+(\\t.+)?") },
                { new CountryGenerator(),       String.class,   Pattern.compile("[a-zA-Z]+(\\s+[a-zA-Z]+)*") },
                { new EmailGenerator(),         String.class,   Pattern.compile("[0-9a-zA-Z\\-.]+@[a-zA-Z]+\\.[a-zA-Z]+") },
                { new IdGenerator(),            String.class,   Pattern.compile("[0-9a-zA-Z]+") },
                { new JsonGenerator(),          String.class,   Pattern.compile("\\{.*:.*}") },
                { new NameGenerator(),          String.class,   Pattern.compile("[a-zA-Z]+") },
                { new NickGenerator(),          String.class,   Pattern.compile("[0-9a-zA-Z\\-.]+") },
                { new NounGenerator(),          String.class,   Pattern.compile("[0-9a-zA-Z]+") },
                { new PassGenerator(),          String.class,   Pattern.compile("[0-9a-zA-Z]{6,}") },
                { new PhoneGenerator(),         String.class,   Pattern.compile("[0-9]\\([0-9]{1,3}\\)[0-9]+") },
                { new PhraseGenerator(),        String.class,   Pattern.compile(".+(\\t.+)?") },
                { new StringGenerator(),        String.class,   Pattern.compile("[0-9a-zA-Z]+") },
                { new TagGenerator(),           String.class,   Pattern.compile("#[0-9a-zA-Z]+") },
                { new UuidGenerator(),          UUID.class,     Pattern.compile("[0-9a-zA-Z\\-]+") },
                { new BooleanGenerator(),       Boolean.class,  Pattern.compile("false|true") },
                { new ListGenerator(),          ArrayList.class,Pattern.compile("\\[([a-zA-Z0-9]+(, )?)+]") },
                { new SetGenerator(),           HashSet.class,  Pattern.compile("\\[([a-zA-Z0-9]+(, )?)+]") },
                { new MapGenerator(),           HashMap.class,  Pattern.compile("\\{([a-zA-Z0-9]+=[a-zA-Z0-9]+(, )?)+}") },
                { new DateGenerator(),          Date.class,     Pattern.compile("[A-Za-z]{3} [A-Za-z]{3} \\d{2} \\d{2}:\\d{2}:\\d{2} [A-Za-z]{3} \\d{4}") },
                { new LocalDateGenerator(),     LocalDate.class,Pattern.compile("\\d{4}-\\d{2}-\\d{2}") },
                { new LocalDateTimeGenerator(), LocalDateTime.class,   Pattern.compile("\\d{4}-\\d{2}-\\d{2}[A-Z]\\d{2}:\\d{2}:\\d{2}") },
                { new LocalTimeGenerator(),     LocalTime.class, Pattern.compile("\\d{2}:\\d{2}:\\d{2}(\\.\\d{1,10})?") },
                { new TimestampGenerator(),     Timestamp.class, Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}(\\.\\d{1,10})?") }
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
