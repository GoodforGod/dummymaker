package io.goodforgod.dummymaker;

import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.parameterized.*;
import io.goodforgod.dummymaker.generator.simple.*;
import io.goodforgod.dummymaker.generator.simple.number.*;
import io.goodforgod.dummymaker.generator.simple.number.MccGenerator;
import io.goodforgod.dummymaker.generator.simple.string.*;
import io.goodforgod.dummymaker.generator.simple.time.*;
import io.goodforgod.dummymaker.util.CastUtils;
import io.goodforgod.dummymaker.util.CollectionUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import org.jetbrains.annotations.NotNull;

/**
 * Default gen config implementation for generators discovery With all library generators and their
 * patterns availability
 *
 * @author Anton Kurako (GoodforGod)
 * @since 24.11.2022
 */
final class GeneratorSupplier {

    private static final Generator<?> DEFAULT_GENERATOR = new EmbeddedGenerator();
    private static final Generator<?> ARRAY2D_GENERATOR = new Array2DParameterizedGenerator(1, 3, 1, 3);
    private static final Generator<?> ARRAY_GENERATOR = new ArrayParameterizedGenerator(1, 3);
    private static final Generator<?> ENUM_GENERATOR = new EnumParameterizedGenerator(new String[] {}, new String[] {});

    /**
     * Map of classified generators and their target classes
     */
    private static final Map<Class<?>, List<Generator<?>>> TYPE_TO_GENERATORS = new HashMap<>();

    static {
        TYPE_TO_GENERATORS.put(String.class, Arrays.asList(new AddressFullGenerator(), new AddressGenerator(),
                new BtcAddressGenerator(), new BtcTxHashGenerator(), new CadastralGenerator(), new CategoryGenerator(),
                new CityGenerator(), new CompanyGenerator(), new CountryGenerator(), new CurrencyGenerator(),
                new DescriptionGenerator(), new DistrictGenerator(), new DocumentGenerator(), new EmailGenerator(),
                new EthAddressGenerator(), new EthTxHashGenerator(), new ExtensionGenerator(), new FileGenerator(),
                new FormatGenerator(), new FrequencyGenerator(), new FullnameGenerator(), new GenderGenerator(),
                new HexDataGenerator(), new HexNumberGenerator(), new HouseGenerator(), new IdBigGenerator(),
                new IdGenerator(), new JobGenerator(), new LevelGenerator(), new LoginGenerator(),
                new io.goodforgod.dummymaker.generator.simple.number.MccGenerator(), new MerchantGenerator(),
                new MiddleNameGenerator(), new NameGenerator(), new NounGenerator(), new PasswordGenerator(),
                new PhoneGenerator(false), new PhotoGenerator(), new ProductGenerator(), new RoleGenerator(),
                new StatusGenerator(), new StreetGenerator(), new StringGenerator(), new SurnameGenerator(),
                new TagGenerator(), new TypeGenerator(), new VersionGenerator(), new IPv4Generator(), new IPv6Generator()));

        TYPE_TO_GENERATORS.put(CharSequence.class, TYPE_TO_GENERATORS.get(String.class));

        TYPE_TO_GENERATORS.put(void.class, Collections.singletonList(new NullGenerator()));
        TYPE_TO_GENERATORS.put(Void.class, Collections.singletonList(new NullGenerator()));

        TYPE_TO_GENERATORS.put(Object.class, Collections.singletonList(new ObjectGenerator()));
        TYPE_TO_GENERATORS.put(Boolean.class, Collections.singletonList(new BooleanGenerator()));
        TYPE_TO_GENERATORS.put(Byte.class, Collections.singletonList(new ByteGenerator()));
        TYPE_TO_GENERATORS.put(Character.class, Arrays.asList(new CharacterGenerator(), new CharGenerator()));
        TYPE_TO_GENERATORS.put(Short.class, Collections.singletonList(new ShortGenerator()));
        TYPE_TO_GENERATORS.put(Integer.class, Arrays.asList(new IntegerGenerator(),
                new IntegerSmallGenerator(), new PostalGenerator(), new MccGenerator()));
        TYPE_TO_GENERATORS.put(Long.class, Arrays.asList(new LongGenerator(), new UnixTimeGenerator()));
        TYPE_TO_GENERATORS.put(Float.class, Collections.singletonList(new FloatSmallGenerator()));
        TYPE_TO_GENERATORS.put(Double.class, Collections.singletonList(new DoubleSmallGenerator()));
        TYPE_TO_GENERATORS.put(BigInteger.class, Collections.singletonList(new BigIntegerGenerator()));
        TYPE_TO_GENERATORS.put(BigDecimal.class, Arrays.asList(new BigDecimalGenerator(), new PriceGenerator()));

        TYPE_TO_GENERATORS.put(boolean.class, TYPE_TO_GENERATORS.get(Boolean.class));
        TYPE_TO_GENERATORS.put(byte.class, TYPE_TO_GENERATORS.get(Byte.class));
        TYPE_TO_GENERATORS.put(short.class, TYPE_TO_GENERATORS.get(Short.class));
        TYPE_TO_GENERATORS.put(char.class, TYPE_TO_GENERATORS.get(Character.class));
        TYPE_TO_GENERATORS.put(int.class, TYPE_TO_GENERATORS.get(Integer.class));
        TYPE_TO_GENERATORS.put(long.class, TYPE_TO_GENERATORS.get(Long.class));
        TYPE_TO_GENERATORS.put(float.class, TYPE_TO_GENERATORS.get(Float.class));
        TYPE_TO_GENERATORS.put(double.class, TYPE_TO_GENERATORS.get(Double.class));

        TYPE_TO_GENERATORS.put(Calendar.class, Collections.singletonList(new CalendarGenerator()));
        TYPE_TO_GENERATORS.put(Period.class, Collections.singletonList(new PeriodGenerator()));
        TYPE_TO_GENERATORS.put(Duration.class, Collections.singletonList(new DurationGenerator()));
        TYPE_TO_GENERATORS.put(Time.class, Collections.singletonList(new TimeSqlGenerator()));
        TYPE_TO_GENERATORS.put(Timestamp.class, Collections.singletonList(new TimestampGenerator()));
        TYPE_TO_GENERATORS.put(Date.class, Collections.singletonList(new DateGenerator()));
        TYPE_TO_GENERATORS.put(java.sql.Date.class, Collections.singletonList(new DateSqlGenerator()));
        TYPE_TO_GENERATORS.put(Instant.class, Collections.singletonList(new InstantGenerator()));
        TYPE_TO_GENERATORS.put(LocalTime.class, Collections.singletonList(new LocalTimeGenerator()));
        TYPE_TO_GENERATORS.put(LocalDate.class, Collections.singletonList(new LocalDateGenerator()));
        TYPE_TO_GENERATORS.put(LocalDateTime.class,
                Collections.singletonList(new LocalDateTimeGenerator()));
        TYPE_TO_GENERATORS.put(OffsetTime.class, Collections.singletonList(new OffsetTimeGenerator()));
        TYPE_TO_GENERATORS.put(OffsetDateTime.class,
                Collections.singletonList(new OffsetDateTimeGenerator()));
        TYPE_TO_GENERATORS.put(ZoneOffset.class, Collections.singletonList(new ZonedOffsetGenerator()));
        TYPE_TO_GENERATORS.put(ZonedDateTime.class,
                Collections.singletonList(new ZonedDateTimeGenerator()));
        TYPE_TO_GENERATORS.put(DayOfWeek.class, Collections.singletonList(new DayOfWeekGenerator()));
        TYPE_TO_GENERATORS.put(Month.class, Collections.singletonList(new MonthGenerator()));
        TYPE_TO_GENERATORS.put(MonthDay.class, Collections.singletonList(new MonthDayGenerator()));
        TYPE_TO_GENERATORS.put(Year.class, Collections.singletonList(new YearGenerator()));
        TYPE_TO_GENERATORS.put(YearMonth.class, Collections.singletonList(new YearMonthGenerator()));

        TYPE_TO_GENERATORS.put(URI.class, Collections.singletonList(new UriGenerator()));
        TYPE_TO_GENERATORS.put(URL.class, Collections.singletonList(new UrlGenerator()));
        TYPE_TO_GENERATORS.put(UUID.class, Collections.singletonList(new UuidGenerator()));

        final ListParameterizedGenerator listGenerator = new ListParameterizedGenerator(1, 3);
        final List<Generator<?>> listComplexGenerators = Collections.singletonList(listGenerator);
        TYPE_TO_GENERATORS.put(Collection.class, listComplexGenerators);
        TYPE_TO_GENERATORS.put(List.class, listComplexGenerators);
        TYPE_TO_GENERATORS.put(ArrayList.class, listComplexGenerators);
        TYPE_TO_GENERATORS.put(LinkedList.class, listComplexGenerators);
        TYPE_TO_GENERATORS.put(CopyOnWriteArrayList.class, listComplexGenerators);

        final SetParameterizedGenerator setGenerator = new SetParameterizedGenerator(1, 3);
        final List<Generator<?>> setComplexGenerators = Collections.singletonList(setGenerator);
        TYPE_TO_GENERATORS.put(Set.class, setComplexGenerators);
        TYPE_TO_GENERATORS.put(HashSet.class, setComplexGenerators);
        TYPE_TO_GENERATORS.put(ConcurrentSkipListSet.class, setComplexGenerators);
        TYPE_TO_GENERATORS.put(CopyOnWriteArraySet.class, setComplexGenerators);

        final MapParameterizedGenerator mapGenerator = new MapParameterizedGenerator(1, 3);
        final List<Generator<?>> mapComplexGenerators = Collections.singletonList(mapGenerator);
        TYPE_TO_GENERATORS.put(Map.class, mapComplexGenerators);
        TYPE_TO_GENERATORS.put(TreeMap.class, mapComplexGenerators);
        TYPE_TO_GENERATORS.put(HashMap.class, mapComplexGenerators);
        TYPE_TO_GENERATORS.put(WeakHashMap.class, mapComplexGenerators);
        TYPE_TO_GENERATORS.put(IdentityHashMap.class, mapComplexGenerators);
        TYPE_TO_GENERATORS.put(ConcurrentHashMap.class, mapComplexGenerators);
        TYPE_TO_GENERATORS.put(ConcurrentSkipListMap.class, mapComplexGenerators);

        TYPE_TO_GENERATORS.put(Enumeration.class, Collections.singletonList(ENUM_GENERATOR));
    }

    /**
     * Seed used to select always the same generator for specific field
     */
    private final long seed;

    GeneratorSupplier(long seed) {
        this.seed = seed;
    }

    @NotNull
    Generator<?> get(@NotNull Class<?> type) {
        if (type.getTypeName().endsWith("[][]")) {
            return ARRAY2D_GENERATOR;
        } else if (type.getTypeName().endsWith("[]")) {
            return ARRAY_GENERATOR;
        } else if (type.isEnum()) {
            return ENUM_GENERATOR;
        }

        final List<? extends Generator<?>> generators = TYPE_TO_GENERATORS.get(type);
        if (generators == null) {
            return DEFAULT_GENERATOR;
        }

        return CollectionUtils.getIndexWithSalt(generators, "null", seed);
    }

    @NotNull
    Generator<?> get(@NotNull Class<?> type, @NotNull String fieldName) {
        if (type.getTypeName().endsWith("[][]")) {
            return ARRAY2D_GENERATOR;
        } else if (type.getTypeName().endsWith("[]")) {
            return ARRAY_GENERATOR;
        } else if (type.isEnum()) {
            return ENUM_GENERATOR;
        }

        return TYPE_TO_GENERATORS.values().stream()
                .flatMap(Collection::stream)
                .filter(g -> g.hints().pattern() != null && g.hints().pattern().matcher(fieldName).find())
                .sorted((g1, g2) -> Integer.compare(g2.hints().priority(), g1.hints().priority())) // DESC
                .filter(g -> CastUtils.castObject(g.get(), type) != null)
                .findFirst()
                .orElseGet(() -> getRandomGenerator(type, fieldName));
    }

    private Generator<?> getRandomGenerator(Class<?> type, String fieldName) {
        final List<? extends Generator<?>> generators = TYPE_TO_GENERATORS.get(type);
        if (generators == null) {
            return DEFAULT_GENERATOR;
        }

        return CollectionUtils.getIndexWithSalt(generators, fieldName, seed);
    }
}
