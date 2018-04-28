package io.dummymaker.util;

import io.dummymaker.generator.complex.impl.ListComplexGenerator;
import io.dummymaker.generator.complex.impl.MapComplexGenerator;
import io.dummymaker.generator.complex.impl.SetComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.*;
import io.dummymaker.generator.simple.impl.number.DoubleBigGenerator;
import io.dummymaker.generator.simple.impl.number.DoubleGenerator;
import io.dummymaker.generator.simple.impl.number.IntegerGenerator;
import io.dummymaker.generator.simple.impl.number.LongGenerator;
import io.dummymaker.generator.simple.impl.string.*;
import io.dummymaker.generator.simple.impl.time.impl.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.dummymaker.util.BasicCastUtils.getGenericType;
import static io.dummymaker.util.BasicCollectionUtils.getIndexWithSalt;
import static java.util.Collections.singletonList;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 26.04.2018
 */
public class BasicGenUtils {

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
                DoubleBigGenerator.class,
                DoubleGenerator.class,
                IntegerGenerator.class,
                LongGenerator.class,

                // Strings
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
                DateGenerator.class,
                LocalDateGenerator.class,
                LocalTimeGenerator.class,
                TimestampGenerator.class,
                LocalDateTimeGenerator.class,

                // Special
                BooleanGenerator.class,
                ObjectGenerator.class,
                CharacterGenerator.class,
                UuidGenerator.class
        ).collect(Collectors.groupingBy(
                BasicGenUtils::getGeneratorType
        ));

        // Complex
        collectedGenerators.put(List.class, singletonList(ListComplexGenerator.class));
        collectedGenerators.put(Set.class,  singletonList(SetComplexGenerator.class));
        collectedGenerators.put(Map.class,  singletonList(MapComplexGenerator.class));

        // Primitives
        collectedGenerators.put(int.class,      singletonList(IntegerGenerator.class));
        collectedGenerators.put(long.class,     singletonList(LongGenerator.class));
        collectedGenerators.put(double.class,   Arrays.asList(DoubleGenerator.class, DoubleBigGenerator.class));
        collectedGenerators.put(char.class,     singletonList(CharacterGenerator.class));
        collectedGenerators.put(boolean.class,  singletonList(BooleanGenerator.class));

        return collectedGenerators;
    }

    private static Class<?> getGeneratorType(Class<? extends IGenerator> generator) {
        return (generator.getGenericInterfaces().length == 0)
                ? ((Class<?>) getGenericType(((Class<?>) generator.getGenericSuperclass()).getGenericInterfaces()[0]))
                : ((Class<?>) getGenericType(generator.getGenericInterfaces()[0]));
    }

    public static List<Class<? extends IGenerator>> getAutoGenerators(final Field field) {
        return getAutoGenerators(field.getDeclaringClass());
    }

    public static List<Class<? extends IGenerator>> getAutoGenerators(final Class<?> fieldClass) {
        final List<Class<? extends IGenerator>> autoGenerators = AUTO_GENERATORS.get(fieldClass);
        return (BasicCollectionUtils.isEmpty(autoGenerators))
                ? Collections.singletonList(NullGenerator.class)
                : autoGenerators;
    }

    public static Class<? extends IGenerator> getAutoGenerator(final Field field) {
        return (field != null)
                ? getAutoGenerator(field.getType())
                : NullGenerator.class;
    }

    public static Class<? extends IGenerator> getAutoGenerator(final Class<?> fieldClass) {
        if(fieldClass == null)
            return NullGenerator.class;

        final List<Class<? extends IGenerator>> autoGenerators = AUTO_GENERATORS.get(fieldClass);
        final String fieldName = fieldClass.getSimpleName();
        return (BasicCollectionUtils.isEmpty(autoGenerators))
                ? NullGenerator.class
                : autoGenerators.get(getIndexWithSalt(autoGenerators.size(), fieldName, SALT));
    }
}
