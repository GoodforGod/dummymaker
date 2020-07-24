package io.dummymaker.generator;

import io.dummymaker.generator.complex.ListComplexGenerator;
import io.dummymaker.generator.complex.MapComplexGenerator;
import io.dummymaker.generator.complex.SetComplexGenerator;
import io.dummymaker.generator.simple.BooleanGenerator;
import io.dummymaker.generator.simple.ObjectGenerator;
import io.dummymaker.generator.simple.UuidGenerator;
import io.dummymaker.generator.simple.number.*;
import io.dummymaker.generator.simple.string.*;
import io.dummymaker.generator.simple.time.*;
import io.dummymaker.model.DummyTime.Patterns;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;
import static org.junit.Assert.*;
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
    public static Collection<Object[]> data() {
        final String uuidPattern = "[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}";

        return Arrays.asList(new Object[][] {
                { new BooleanGenerator(), Boolean.class, compile("false|true") },
                { new ByteGenerator(), Byte.class, compile("-?[0-9]+") },
                { new ShortGenerator(), Short.class, compile("[0-9]+") },
                { new IntegerGenerator(), Integer.class, compile("[0-9]+") },
                { new FloatGenerator(), Float.class, compile("1|0\\.[0-9]+") },
                { new FloatBigGenerator(), Float.class, compile("[0-9]+\\.[0-9]+") },
                { new DoubleGenerator(), Double.class, compile("1|0\\.[0-9]+") },
                { new DoubleBigGenerator(), Double.class, compile("[0-9]+\\.[0-9]+") },
                { new BigIntegerGenerator(), BigInteger.class, compile("-?[0-9]+") },
                { new BigDecimalGenerator(), BigDecimal.class, compile("-?[0-9]+\\.[0-9]+") },
                { new IdBigGenerator(), String.class, compile("[0-9a-zA-Z\\-]+") },
                { new UnixTimeGenerator(), Long.class, compile("[0-9]+") },
                { new BtcAddressGenerator(), String.class, compile("[a-zA-Z0-9]{34}") },
                { new BtcTxHashGenerator(), String.class, compile("[a-zA-Z0-9]{64}") },
                { new CityGenerator(), String.class, compile("[a-zA-Z\\-]+") },
                { new CompanyGenerator(), String.class, compile(".+(\\t.+)?") },
                { new CountryGenerator(), String.class, compile("[a-zA-Z]+([\\s\\-]+[a-zA-Z]+)*") },
                { new EmailGenerator(), String.class, compile("[0-9a-zA-Z\\-.]+@[a-zA-Z]+\\.[a-zA-Z]+") },
                { new EthAddressGenerator(), String.class, compile("0x[0-9a-zA-Z]{40}") },
                { new EthTxHashGenerator(), String.class, compile("0x[0-9a-zA-Z]{64}") },
                { new HexDataGenerator(), String.class, compile("[0-9a-zA-Z]+") },
                { new HexNumberGenerator(), String.class, compile("[0-9a-zA-Z]+") },
                { new IdGenerator(), String.class, compile(uuidPattern) },
                { new JsonGenerator(), String.class, compile("\\{.*:.*}") },
                { new UriGenerator(), String.class, compile("(/[a-zA-Z]+)+") },
                { new UrlGenerator(), String.class, compile("https://[a-zA-Z0-9\\-]+\\.[a-zA-Z]+(/[a-zA-Z]+)+") },
                { new FormatGenerator(), String.class, compile("[a-zA-Z]+") },
                { new GenderGenerator(), String.class, compile("male|female") },
                { new NameGenerator(), String.class, compile("[a-zA-Z]+") },
                { new SurnameGenerator(), String.class, compile("[a-zA-Z]+") },
                { new LoginGenerator(), String.class, compile("[0-9a-zA-Z_]+") },
                { new NounGenerator(), String.class, compile("[0-9a-zA-Z]+") },
                { new DocGenerator(), String.class, compile("[0-9a-zA-Z]{6,}") },
                { new PhoneGenerator(), String.class, compile("[0-9]\\([0-9]{1,3}\\)[0-9]+") },
                { new StringGenerator(), String.class, compile("[0-9a-zA-Z]+") },
                { new VersionGenerator(), String.class, compile("[0-9]\\.[0-9]{1,2}\\.[0-9]{1,2}(-SNAPSHOT)?") },
                { new TagGenerator(), String.class, compile("#[0-9a-zA-Z]+") },
                { new UuidGenerator(), UUID.class, compile(uuidPattern) },
                { new CharacterGenerator(), Character.class, compile("[a-zA-Z]") },
                { new CharGenerator(), Character.class, compile(".") },
                { new ObjectGenerator(), String.class, compile("object_[0-9]+") },
                { new ListComplexGenerator(), ArrayList.class, compile("\\[(" + uuidPattern + "(, )?)+]") },
                { new SetComplexGenerator(), HashSet.class, compile("\\[(" + uuidPattern + "(, )?)+]") },
                { new MapComplexGenerator(), HashMap.class, compile("\\{(" + uuidPattern + "=" + uuidPattern + "(, )?)+}") },
                { new LocalDateTimeGenerator(), LocalDateTime.class, Patterns.LOCAL_DATETIME.getToStringPattern() },
                { new LocalDateGenerator(), LocalDate.class, Patterns.LOCAL_DATE.getToStringPattern() },
                { new LocalTimeGenerator(), LocalTime.class, Patterns.LOCAL_TIME.getToStringPattern() },
                { new TimestampGenerator(), Timestamp.class, Patterns.TIMESTAMP.getToStringPattern() },
                { new DateSqlGenerator(), java.sql.Date.class, Patterns.DATE_SQL.getToStringPattern() },
                { new DateGenerator(), Date.class, Patterns.DATE.getToStringPattern() },
                { new TimeGenerator(), Time.class, Patterns.TIME.getToStringPattern() }
        });
    }

    @Test
    public void valueRegexMatching() {
        // Due to bundles for some generators, need more than 1 iteration
        // for more confidence that value is matching
        for (int i = 0; i < 5; i++) {
            final Object generated = generator.generate();
            assertNotNull(generated);
            assertEquals(generated.getClass(), genClass);

            final String v = String.valueOf(generated);
            final String msg = generator.getClass().getSimpleName() + " : Pattern -" + pattern.pattern() + " : Value - " + v;
            assertTrue(msg, pattern.matcher(v).matches());
        }
    }
}
