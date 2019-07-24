package io.dummymaker.util;

import io.dummymaker.generator.complex.impl.*;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.BooleanGenerator;
import io.dummymaker.generator.simple.impl.NullGenerator;
import io.dummymaker.generator.simple.impl.ObjectGenerator;
import io.dummymaker.generator.simple.impl.UuidGenerator;
import io.dummymaker.generator.simple.impl.number.*;
import io.dummymaker.generator.simple.impl.string.*;
import io.dummymaker.generator.simple.impl.time.impl.*;
import io.dummymaker.scan.impl.PackageScanner;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.dummymaker.util.CastUtils.getGenericType;
import static io.dummymaker.util.CollectionUtils.getIndexWithSalt;
import static java.util.Collections.singletonList;

/**
 * Utils for auto gen mapping
 *
 * @author GoodforGod
 * @since 26.04.2018
 */
public class GenUtils {

    /**
     * Salt used to select always the same generator for specific field
     */
    private static final int SALT = ((Long) System.currentTimeMillis()).hashCode();

    /**
     * Map with class values (int, Long, String, etc) as keys and suitable generators classes
     */
    private static final Map<Class, List<Class<? extends IGenerator>>> AUTO_GENERATORS;

    static {
        AUTO_GENERATORS = instantiateAutoGeneratorsMap();
    }

    private static Map<Class, List<Class<? extends IGenerator>>> instantiateAutoGeneratorsMap() {

        final Map<Class, List<Class<? extends IGenerator>>> collectedGenerators = Stream.of(
                ByteGenerator.class,
                UByteGenerator.class,
                ShortGenerator.class,
                UShortGenerator.class,
                IntegerGenerator.class,
                UIntegerGenerator.class,
                LongGenerator.class,
                FloatGenerator.class,
                FloatBigGenerator.class,
                DoubleBigGenerator.class,
                DoubleGenerator.class,
                BigIntegerGenerator.class,
                BigDecimalGenerator.class,

                // Strings
                HexNumberGenerator.class,
                HexDataGenerator.class,
                CityGenerator.class,
                CompanyGenerator.class,
                CountryGenerator.class,
                EmailGenerator.class,
                IdBigGenerator.class,
                IdGenerator.class,
                JsonGenerator.class,
                NameGenerator.class,
                NickGenerator.class,
                NounGenerator.class,
                PassGenerator.class,
                PhoneGenerator.class,
                PhraseGenerator.class,
                StringGenerator.class,
                TagGenerator.class,

                // Time
                DateSqlGenerator.class,
                TimeGenerator.class,
                TimestampGenerator.class,
                DateGenerator.class,
                LocalDateGenerator.class,
                LocalTimeGenerator.class,
                LocalDateTimeGenerator.class,

                // Special
                BooleanGenerator.class,
                ObjectGenerator.class,
                CharacterGenerator.class,
                CharGenerator.class,
                UuidGenerator.class
        ).collect(Collectors.groupingBy(
                GenUtils::getGeneratorType
        ));

        // Complex
        collectedGenerators.put(List.class, singletonList(ListComplexGenerator.class));
        collectedGenerators.put(Set.class, singletonList(SetComplexGenerator.class));
        collectedGenerators.put(Map.class, singletonList(MapComplexGenerator.class));

        // Primitives
        collectedGenerators.put(byte.class, Arrays.asList(ByteGenerator.class, UByteGenerator.class));
        collectedGenerators.put(short.class, Arrays.asList(ShortGenerator.class, UShortGenerator.class));
        collectedGenerators.put(int.class, Arrays.asList(IntegerGenerator.class, UIntegerGenerator.class));
        collectedGenerators.put(long.class, singletonList(LongGenerator.class));
        collectedGenerators.put(float.class, Arrays.asList(FloatGenerator.class, FloatBigGenerator.class));
        collectedGenerators.put(double.class, Arrays.asList(DoubleGenerator.class, DoubleBigGenerator.class));
        collectedGenerators.put(char.class, Arrays.asList(CharGenerator.class, CharacterGenerator.class));
        collectedGenerators.put(boolean.class, singletonList(BooleanGenerator.class));

        try {
            Enumeration<URL> resources = GenUtils.class.getClassLoader().getResources("*");
            Map<Class<?>, String> scan = new PackageScanner().scan("io.dummymaker.generator");
            resources.nextElement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return collectedGenerators;
    }

    private static Class<?> getGeneratorType(Class<? extends IGenerator> generator) {
        return (generator.getGenericInterfaces().length == 0)
                ? ((Class<?>) getGenericType(((Class<?>) generator.getGenericSuperclass()).getGenericInterfaces()[0]))
                : ((Class<?>) getGenericType(generator.getGenericInterfaces()[0]));
    }

    public static Class<? extends IGenerator> getAutoGenerator(final Class<?> fieldClass) {
        if (fieldClass == null)
            return NullGenerator.class;

        final List<Class<? extends IGenerator>> generators = AUTO_GENERATORS.get(fieldClass);
        final String fieldName = fieldClass.getSimpleName();

        if (fieldClass.getTypeName().endsWith("[][]"))
            return Array2DComplexGenerator.class;
        if (fieldClass.getTypeName().endsWith("[]"))
            return ArrayComplexGenerator.class;
        if (fieldClass.isEnum())
            return EnumComplexGenerator.class;
        if (CollectionUtils.isEmpty(generators))
            return NullGenerator.class;

        return generators.get(getIndexWithSalt(generators.size(), fieldName, SALT));
    }

    public static Class<? extends IGenerator> getAutoGenerator(final String classTypeName) {
        if (StringUtils.isEmpty(classTypeName))
            return NullGenerator.class;

        for (Map.Entry<Class, List<Class<? extends IGenerator>>> entry : AUTO_GENERATORS.entrySet()) {
            if (entry.getKey().getTypeName().equals(classTypeName))
                return entry.getValue().get(getIndexWithSalt(entry.getValue().size(), classTypeName, SALT));
        }

        return NullGenerator.class;
    }
}
