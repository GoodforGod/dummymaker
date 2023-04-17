package io.goodforgod.dummymaker.generator;

import static java.util.regex.Pattern.compile;
import static org.junit.jupiter.api.Assertions.*;

import io.goodforgod.dummymaker.annotation.parameterized.GenTime;
import io.goodforgod.dummymaker.generator.parameterized.ListParameterizedGenerator;
import io.goodforgod.dummymaker.generator.parameterized.MapParameterizedGenerator;
import io.goodforgod.dummymaker.generator.parameterized.SetParameterizedGenerator;
import io.goodforgod.dummymaker.generator.simple.*;
import io.goodforgod.dummymaker.generator.simple.number.*;
import io.goodforgod.dummymaker.generator.simple.number.MccGenerator;
import io.goodforgod.dummymaker.generator.simple.string.*;
import io.goodforgod.dummymaker.generator.simple.time.*;
import io.goodforgod.dummymaker.testdata.DummyTime.Patterns;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;
import java.util.regex.Pattern;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author GoodforGod
 * @since 31.07.2017
 */
class GeneratorPatternValidTests {

    public static Collection<Object[]> data() {
        final String uuidPattern = "[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}";

        return Arrays.asList(new Object[][] {
                { new BooleanGenerator(), Boolean.class, compile("false|true") },
                { new ByteGenerator(), Byte.class, compile("-?[0-9]+") },
                { new ShortGenerator((short) 0, Short.MAX_VALUE), Short.class, compile("[0-9]+") },
                { new IntegerGenerator(0, Integer.MAX_VALUE), Integer.class, compile("[0-9]+") },
                { new LongGenerator(0, Integer.MAX_VALUE), Long.class, compile("[0-9]+") },
                { new FloatGenerator(0, 1), Float.class, compile("1|0\\.[0-9]+(E-[0-9])?") },
                { new FloatSmallGenerator(), Float.class, compile("[0-9]+\\.[0-9]+(E-[0-9])?") },
                { new DoubleGenerator(0, 1), Double.class, compile("1|0\\.[0-9]+") },
                { new DoubleSmallGenerator(), Double.class, compile("[0-9]+\\.[0-9]+") },
                { new PriceGenerator(), BigDecimal.class, compile("[0-9]+\\.[0-9]+") },
                { new BigIntegerGenerator(), BigInteger.class, compile("-?[0-9]+") },
                { new BigDecimalGenerator(), BigDecimal.class, compile("-?[0-9]+\\.[0-9]+") },
                { new IdBigGenerator(), String.class, compile("[0-9a-zA-Z\\-]+") },
                { new BtcAddressGenerator(), String.class, compile("[a-zA-Z0-9]{34}") },
                { new BtcTxHashGenerator(), String.class, compile("[a-zA-Z0-9]{64}") },
                { new CityGenerator(), String.class, compile("[a-zA-Z\\-]+") },
                { new MccGenerator(), Integer.class, compile("[0-9]{4}") },
                { new StringValuesGenerator(Collections.singletonList("OPS")), String.class, compile("OPS") },
                { new CadastralGenerator(), String.class, compile("[0-9]{2}:[0-9]{2}:[0-9]{6,7}:[0-9]{2}") },
                { new PasswordGenerator(), String.class, compile("[0-9a-zA-Z]{6,20}") },
                { new CompanyGenerator(), String.class, compile(".+(\\t.+)?") },
                { new CountryGenerator(), String.class, compile("[a-zA-Z]+([\\s\\-]+[a-zA-Z]+)*") },
                { new EmailGenerator(), String.class, compile("[0-9a-zA-Z\\-.]+@[a-zA-Z]+\\.[a-zA-Z]+") },
                { new EthAddressGenerator(), String.class, compile("0x[0-9a-zA-Z]{40}") },
                { new EthTxHashGenerator(), String.class, compile("0x[0-9a-zA-Z]{64}") },
                { new HexDataGenerator(), String.class, compile("[0-9a-zA-Z]+") },
                { new HexNumberGenerator(), String.class, compile("[0-9a-zA-Z]+") },
                { new IdGenerator(), String.class, compile(uuidPattern) },
                { new JsonGenerator(), String.class, compile("\\{.*:.*}") },
                { new UriGenerator(), URI.class, compile("(/[a-zA-Z]+)+") },
                { new UrlGenerator(), URL.class, compile("https://[a-zA-Z0-9\\-]+\\.[a-zA-Z]+(/[a-zA-Z]+)+") },
                { new FormatGenerator(), String.class, compile("[a-zA-Z]+") },
                { new GenderGenerator(), String.class, compile("male|female") },
                { new FullnameGenerator(), String.class, compile("[a-zA-Z ]+") },
                { new NameGenerator(), String.class, compile("[a-zA-Z]+") },
                { new SurnameGenerator(), String.class, compile("[a-zA-Z]+") },
                { new LoginGenerator(), String.class, compile("[0-9a-zA-Z_]+") },
                { new NounGenerator(), String.class, compile("[0-9a-zA-Z]+") },
                { new IPv4Generator(), String.class, compile("[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+") },
                {
                        new IPv6Generator(),
                        String.class,
                        compile("[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+") },
                { new CategoryGenerator(), String.class, compile("[0-9a-zA-Z &]+") },
                { new CurrencyGenerator(), String.class, compile("[0-9a-zA-Z ]+") },
                { new FrequencyGenerator(), String.class, compile("[0-9a-zA-Z ]+") },
                { new DocumentGenerator(), String.class, compile("[0-9a-zA-Z]{6,}") },
                { new PhoneGenerator(true), String.class, compile("\\+[0-9\\-]+") },
                { new StringGenerator(6, 12), String.class, compile("[0-9a-zA-Z]+") },
                { new VersionGenerator(), String.class, compile("[0-9]\\.[0-9]{1,2}\\.[0-9]{1,2}(-SNAPSHOT)?") },
                { new TagGenerator(), String.class, compile("#[0-9a-zA-Z]+") },
                { new UuidGenerator(), UUID.class, compile(uuidPattern) },
                { new CharacterGenerator(), Character.class, compile("[a-zA-Z]") },
                { new CharGenerator(), Character.class, compile(".") },
                { new ObjectGenerator(), String.class, compile("object_[0-9]+") },
                { new ListParameterizedGenerator(1, 3), ArrayList.class, compile("\\[(object_[0-9]+(, )?)+]") },
                { new SetParameterizedGenerator(1, 3), HashSet.class, compile("\\[(object_[0-9]+(, )?)+]") },
                {
                        new MapParameterizedGenerator(1, 3),
                        HashMap.class,
                        compile("\\{(object_[0-9]+=object_[0-9]+(, )?)+}") },
                { new UnixTimeGenerator(GenTime.MIN, GenTime.MAX), Long.class, compile("[0-9]+") },
                { new YearMonthGenerator(GenTime.MIN, GenTime.MAX), YearMonth.class, compile("[0-9]+-[0-9]+") },
                { new YearGenerator(GenTime.MIN, GenTime.MAX), Year.class, compile("[0-9]+") },
                { new MonthDayGenerator(GenTime.MIN, GenTime.MAX), MonthDay.class, compile("--[0-9]+-[0-9]+") },
                { new MonthGenerator(GenTime.MIN, GenTime.MAX), Month.class, compile("[A-Z]+") },
                { new InstantGenerator(GenTime.MIN, GenTime.MAX), Instant.class, Patterns.OFFSET_DATETIME.getToStringPattern() },
                {
                        new ZonedDateTimeGenerator(GenTime.MIN, GenTime.MAX),
                        ZonedDateTime.class,
                        Patterns.OFFSET_DATETIME.getToStringPattern() },
                {
                        new OffsetDateTimeGenerator(GenTime.MIN, GenTime.MAX),
                        OffsetDateTime.class,
                        Patterns.OFFSET_DATETIME.getToStringPattern() },
                {
                        new LocalDateTimeGenerator(GenTime.MIN, GenTime.MAX),
                        LocalDateTime.class,
                        Patterns.LOCAL_DATETIME.getToStringPattern() },
                { new LocalDateGenerator(GenTime.MIN, GenTime.MAX), LocalDate.class, Patterns.LOCAL_DATE.getToStringPattern() },
                { new LocalTimeGenerator(GenTime.MIN, GenTime.MAX), LocalTime.class, Patterns.LOCAL_TIME.getToStringPattern() },
                { new TimestampGenerator(GenTime.MIN, GenTime.MAX), Timestamp.class, Patterns.TIMESTAMP.getToStringPattern() },
                { new DateSqlGenerator(GenTime.MIN, GenTime.MAX), java.sql.Date.class, Patterns.DATE_SQL.getToStringPattern() },
                { new DateGenerator(GenTime.MIN, GenTime.MAX), Date.class, Patterns.DATE.getToStringPattern() },
                { new TimeSqlGenerator(GenTime.MIN, GenTime.MAX), Time.class, Patterns.TIME.getToStringPattern() }
        });
    }

    @MethodSource("data")
    @ParameterizedTest
    void valueRegexMatching(Generator generator, Class genClass, Pattern pattern) {
        // Due to bundles for some generators, need more than 1 iteration
        // for more confidence that value is matching
        for (int i = 0; i < 5; i++) {
            final Object generated = generator.get();
            assertNotNull(generated);
            assertEquals(generated.getClass(), genClass);

            final String v = String.valueOf(generated);
            final String msg = generator.getClass().getSimpleName() + " : Pattern - '" + pattern.pattern() + "' : Value - '" + v
                    + "'";
            assertTrue(pattern.matcher(v).matches(), msg);
        }
    }
}
