package io.dummymaker.factory.impl;

import io.dummymaker.annotation.ComplexGen;
import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.annotation.simple.number.GenDouble;
import io.dummymaker.annotation.simple.number.GenDoubleBig;
import io.dummymaker.annotation.simple.number.GenInteger;
import io.dummymaker.annotation.simple.number.GenLong;
import io.dummymaker.annotation.simple.string.GenCity;
import io.dummymaker.annotation.simple.string.GenCompany;
import io.dummymaker.annotation.simple.string.GenCountry;
import io.dummymaker.annotation.simple.string.GenEmail;
import io.dummymaker.container.impl.GenContainer;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.BooleanGenerator;
import io.dummymaker.generator.simple.impl.NullGenerator;
import io.dummymaker.generator.simple.impl.UuidGenerator;
import io.dummymaker.generator.simple.impl.collection.impl.ListGenerator;
import io.dummymaker.generator.simple.impl.collection.impl.MapGenerator;
import io.dummymaker.generator.simple.impl.collection.impl.SetGenerator;
import io.dummymaker.generator.simple.impl.number.DoubleBigGenerator;
import io.dummymaker.generator.simple.impl.number.DoubleGenerator;
import io.dummymaker.generator.simple.impl.number.IntegerGenerator;
import io.dummymaker.generator.simple.impl.number.LongGenerator;
import io.dummymaker.generator.simple.impl.string.*;
import io.dummymaker.generator.simple.impl.time.impl.*;
import io.dummymaker.util.BasicCastUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.dummymaker.util.BasicCastUtils.getGenericType;
import static io.dummymaker.util.BasicCollectionUtils.getRandomIndex;
import static io.dummymaker.util.BasicCollectionUtils.isEmpty;
import static java.util.Collections.singletonList;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 24.04.2018
 */
public class AutoGeneratorsFactory {

    private final IGenerator nullGenerator = new NullGenerator();

    private void availableListGenerators() {
        final List<Class<? extends IGenerator>> generators = new ArrayList<>();

        // Numbers
        generators.add(DoubleBigGenerator.class);
        generators.add(DoubleGenerator.class);
        generators.add(IntegerGenerator.class);
        generators.add(LongGenerator.class);

        // Strings
        generators.add(CityGenerator.class);
        generators.add(CompanyGenerator.class);
        generators.add(CountryGenerator.class);
        generators.add(EmailGenerator.class);
        generators.add(IdBigGenerator.class);
        generators.add(IdGenerator.class);
        generators.add(JsonGenerator.class);
        generators.add(NameGenerator.class);
        generators.add(NickGenerator.class);
        generators.add(NounGenerator.class);
        generators.add(PassGenerator.class);
        generators.add(PhoneGenerator.class);
        generators.add(PhraseGenerator.class);
        generators.add(StringGenerator.class);
        generators.add(TagGenerator.class);

        // Time
        generators.add(DateGenerator.class);
        generators.add(LocalDateGenerator.class);
        generators.add(LocalTimeGenerator.class);
        generators.add(TimestampGenerator.class);
        generators.add(LocalDateTimeGenerator.class);

        // Special
        generators.add(BooleanGenerator.class);
        generators.add(UuidGenerator.class);
    }

    public void availableGenContainers() {
        final List<Class<? extends Annotation>> genAnnotations = Stream.of(

                // Num
                GenDouble.class,
                GenDoubleBig.class,
                GenInteger.class,
                GenLong.class,

                // Strings
                GenCity.class,
                GenCompany.class,
                GenCountry.class,
                GenEmail.class
        ).collect(Collectors.toList());

        final Predicate<Annotation> isGen = (a) -> a.annotationType().equals(PrimeGen.class)
                || a.annotationType().equals(ComplexGen.class);

        final Annotation annotation = BasicCastUtils.instantiate(genAnnotations.get(0));
        if(annotation == null)
            return;

        GenContainer container = null;
        for(Annotation inline : annotation.annotationType().getDeclaredAnnotations()) {
            if(isGen.test(inline)) {
                container = GenContainer.asGen(inline, annotation);
            }
        }

        if(container != null)
            container.toString();
    }

    public Map<Class<?>, List<Class<? extends IGenerator>>> availableGenerators() {
        final List<Class<? extends IGenerator>> generators = new ArrayList<>();

        // Numbers
        return Stream.of(
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
                UuidGenerator.class
        ).collect(Collectors.groupingBy(
                this::extractGenericType
        ));
    }

    public IGenerator getGenerator(final Field field) {
        final Class<?> fieldClass = field.getDeclaringClass();

        if(fieldClass.equals(List.class)) {
            final Class<?> genericType      = ((Class<?>) getGenericType(field.getGenericType()));
            return new ListGenerator(getGenerator(genericType));
        } else if(fieldClass.equals(Set.class)) {
            final Class<?> genericType      = ((Class<?>) getGenericType(field.getGenericType()));
            return new SetGenerator(getGenerator(genericType));
        } else if(fieldClass.equals(Map.class)) {
            final Class<?> genericKeyType   = ((Class<?>) getGenericType(field.getGenericType(), 0));
            final Class<?> genericValueType = ((Class<?>) getGenericType(field.getGenericType(), 1));
            return new MapGenerator(getGenerator(genericKeyType), getGenerator(genericValueType));
        }

        return getGenerator(fieldClass);
    }

    public IGenerator getGenerator(final Class<?> fieldClass) {
        final Map<Class, List<IGenerator>> map = instantiateGenerators();

        final List<IGenerator> generators = map.get(fieldClass);
        return isEmpty(generators)
                ? nullGenerator
                : generators.get(getRandomIndex(generators));
    }

    public Map<Class, List<IGenerator>> instantiateGenerators() {

        final Map<Class, List<IGenerator>> collectedGenerators = Stream.of(
                // Numbers
                new DoubleBigGenerator(),
                new DoubleGenerator(),
                new IntegerGenerator(),
                new LongGenerator(),

                // Strings
                new CityGenerator(),
                new CompanyGenerator(),
                new CountryGenerator(),
                new EmailGenerator(),
                new IdBigGenerator(),
                new IdGenerator(),
                new JsonGenerator(),
                new NameGenerator(),
                new NickGenerator(),
                new NounGenerator(),
                new PassGenerator(),
                new PhoneGenerator(),
                new PhraseGenerator(),
                new StringGenerator(),
                new TagGenerator(),

                // Time
                new DateGenerator(),
                new LocalDateGenerator(),
                new LocalTimeGenerator(),
                new TimestampGenerator(),
                new LocalDateTimeGenerator(),

                // Special
                new BooleanGenerator(),
                new UuidGenerator()
        ).collect(Collectors.groupingBy(
                g -> extractGenericType(g.getClass())
        ));

        collectedGenerators.put(List.class, singletonList(new ListGenerator()));
        collectedGenerators.put(Set.class, singletonList(new SetGenerator()));
        collectedGenerators.put(Map.class, singletonList(new MapGenerator()));

        return collectedGenerators;
    }

    private Class<?> extractGenericType(Class<? extends IGenerator> generator) {
        return (generator.getGenericInterfaces().length == 0)
                ? ((Class<?>) getGenericType(((Class<?>) generator.getGenericSuperclass()).getGenericInterfaces()[0]))
                : ((Class<?>) getGenericType(generator.getGenericInterfaces()[0]));
    }
}
