package io.dummymaker.generator;

import io.dummymaker.beta.model.DummyTime;
import io.dummymaker.generator.complex.ListComplexGenerator;
import io.dummymaker.generator.complex.MapComplexGenerator;
import io.dummymaker.generator.complex.SetComplexGenerator;
import io.dummymaker.generator.simple.BooleanGenerator;
import io.dummymaker.generator.simple.ObjectGenerator;
import io.dummymaker.generator.simple.UuidGenerator;
import io.dummymaker.generator.simple.number.*;
import io.dummymaker.generator.simple.string.*;
import io.dummymaker.generator.simple.time.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

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

        return Arrays.asList(new Object[][]{
                {new BooleanGenerator(), Boolean.class, Pattern.compile("false|true")},
                {new ByteGenerator(), Byte.class, Pattern.compile("-?[0-9]+")},
                {new ShortGenerator(), Short.class, Pattern.compile("[0-9]+")},
                {new IntegerGenerator(), Integer.class, Pattern.compile("[0-9]+")},
                {new FloatGenerator(), Float.class, Pattern.compile("1|0\\.[0-9]+")},
                {new FloatBigGenerator(), Float.class, Pattern.compile("[0-9]+\\.[0-9]+")},
                {new DoubleGenerator(), Double.class, Pattern.compile("1|0\\.[0-9]+")},
                {new DoubleBigGenerator(), Double.class, Pattern.compile("[0-9]+\\.[0-9]+")},
                {new BigIntegerGenerator(), BigInteger.class, Pattern.compile("-?[0-9]+")},
                {new BigDecimalGenerator(), BigDecimal.class, Pattern.compile("-?[0-9]+\\.[0-9]+")},
                {new IdBigGenerator(), String.class, Pattern.compile("[0-9a-zA-Z]+")},
                {new BtcAddressGenerator(), String.class, Pattern.compile("[a-zA-Z0-9]{34}")},
                {new BtcTxHashGenerator(), String.class, Pattern.compile("[a-zA-Z0-9]{64}")},
                {new CityGenerator(), String.class, Pattern.compile("[a-zA-Z\\-]+")},
                {new CompanyGenerator(), String.class, Pattern.compile(".+(\\t.+)?")},
                {new CountryGenerator(), String.class, Pattern.compile("[a-zA-Z]+([\\s\\-]+[a-zA-Z]+)*")},
                {new EmailGenerator(), String.class, Pattern.compile("[0-9a-zA-Z\\-.]+@[a-zA-Z]+\\.[a-zA-Z]+")},
                {new EthAddressGenerator(), String.class, Pattern.compile("0x[0-9a-zA-Z]{40}")},
                {new EthTxHashGenerator(), String.class, Pattern.compile("0x[0-9a-zA-Z]{64}")},
                {new HexDataGenerator(), String.class, Pattern.compile("[0-9a-zA-Z]+")},
                {new HexNumberGenerator(), String.class, Pattern.compile("[0-9a-zA-Z]+")},
                {new IdGenerator(), String.class, Pattern.compile(uuidPattern)},
                {new JsonGenerator(), String.class, Pattern.compile("\\{.*:.*}")},
                {new NameGenerator(), String.class, Pattern.compile("[a-zA-Z]+")},
                {new SurnameGenerator(), String.class, Pattern.compile("[a-zA-Z]+")},
                {new NickGenerator(), String.class, Pattern.compile("[0-9a-zA-Z\\-.]+")},
                {new NounGenerator(), String.class, Pattern.compile("[0-9a-zA-Z]+")},
                {new PassGenerator(), String.class, Pattern.compile("[0-9a-zA-Z]{6,}")},
                {new PhoneGenerator(), String.class, Pattern.compile("[0-9]\\([0-9]{1,3}\\)[0-9]+")},
                {new PhraseGenerator(), String.class, Pattern.compile(".+(\\t.+)?")},
                {new StringGenerator(), String.class, Pattern.compile("[0-9a-zA-Z]+")},
                {new TagGenerator(), String.class, Pattern.compile("#[0-9a-zA-Z]+")},
                {new UuidGenerator(), UUID.class, Pattern.compile(uuidPattern)},
                {new CharacterGenerator(), Character.class, Pattern.compile("[a-zA-Z]")},
                {new CharGenerator(), Character.class, Pattern.compile(".")},
                {new ObjectGenerator(), String.class, Pattern.compile("object_[0-9]+")},
                {new ListComplexGenerator(), ArrayList.class, Pattern.compile("\\[(" + uuidPattern + "(, )?)+]")},
                {new SetComplexGenerator(), HashSet.class, Pattern.compile("\\[(" + uuidPattern + "(, )?)+]")},
                {new MapComplexGenerator(), HashMap.class, Pattern.compile("\\{(" + uuidPattern + "=" + uuidPattern + "(, )?)+}")},
                {new DateGenerator(), Date.class, DummyTime.Patterns.DATE.getPattern()},
                {new LocalDateGenerator(), LocalDate.class, DummyTime.Patterns.LOCAL_DATE.getPattern()},
                {new LocalDateTimeGenerator(), LocalDateTime.class, DummyTime.Patterns.LOCAL_DATETIME.getPattern()},
                {new DateSqlGenerator(), java.sql.Date.class, DummyTime.Patterns.DATE_SQL.getPattern()},
                {new TimeGenerator(), Time.class, DummyTime.Patterns.TIME.getPattern()}
        });
    }

    @Test
    public void genValueRegexCheck() {
        final Object generated = generator.generate();

        assertNotNull(generated);
        assertEquals(generated.getClass(), genClass);

        final String generatedAsString = String.valueOf(generated);
        assertTrue(generatedAsString, pattern.matcher(generatedAsString).matches());
    }
}
