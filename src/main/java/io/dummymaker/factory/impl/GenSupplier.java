package io.dummymaker.factory.impl;

import static io.dummymaker.util.CollectionUtils.getIndexWithSalt;
import static io.dummymaker.util.CollectionUtils.isEmpty;

import io.dummymaker.factory.IGenSupplier;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.complex.*;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import io.dummymaker.generator.simple.ObjectGenerator;
import io.dummymaker.generator.simple.string.JsonGenerator;
import io.dummymaker.model.Pair;
import io.dummymaker.scan.impl.ClassScanner;
import io.dummymaker.util.CastUtils;
import io.dummymaker.util.GenUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Default gen config implementation for generators discovery With all library generators and their
 * patterns availability
 *
 * @author GoodforGod
 * @see IGenSupplier
 * @since 27.07.2019
 */
public class GenSupplier implements IGenSupplier {

    /**
     * Salt used to select always the same generator for specific field
     */
    protected static final int SALT = ((Long) System.currentTimeMillis()).hashCode();

    /**
     * Map of classified generators and their target classes
     */
    private final Map<Class, List<IGenerator>> classifiers;

    public GenSupplier() {
        this.classifiers = getClassifiedGenerators().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream()
                        .map(CastUtils::instantiate)
                        .filter(Objects::nonNull)
                        .sorted(Comparator.comparing(g -> g.getClass().getSimpleName()))
                        .collect(Collectors.toList())));
    }

    @Override
    public @NotNull Class<? extends IGenerator> getSuitable(@NotNull Field field) {
        return getSuitable(field, field.getType());
    }

    /**
     * Default generator in case suitable not found
     *
     * @return generator class
     */
    protected @NotNull Class<? extends IGenerator> getDefault() {
        return EmbeddedGenerator.class;
    }

    /**
     * Try to find most suitable generator class for target field Using field value class and field name
     * <p>
     * In case field can not be found then treat field as embedded object
     *
     * @param field target field
     * @param type  target field value class
     * @return suitable generator class
     */
    @Override
    public @NotNull Class<? extends IGenerator> getSuitable(@NotNull Field field, @Nullable Class<?> type) {
        if (type == null)
            return getDefault();

        final List<? extends IGenerator> generators = classifiers.get(type);
        final String fieldName = field.getName();

        if (type.getTypeName().endsWith("[][]"))
            return Array2DComplexGenerator.class;
        if (type.getTypeName().endsWith("[]"))
            return ArrayComplexGenerator.class;
        if (type.isEnum())
            return EnumComplexGenerator.class;
        if (isEmpty(generators))
            return getDefault();

        return getSuitableGeneratorClass(fieldName, type).orElseGet(() -> {
            final Optional<Class<? extends IGenerator>> pluralSuitable = fieldName.endsWith("s")
                    ? getSuitableGeneratorClass(fieldName.substring(0, fieldName.length() - 1), type)
                    : Optional.empty();

            return pluralSuitable.orElseGet(() -> {
                final List<? extends IGenerator> nonJsonGenerators = generators.stream()
                        .filter(g -> !(g instanceof JsonGenerator))
                        .collect(Collectors.toList());

                return getIndexWithSalt(nonJsonGenerators, fieldName + type.getName(), SALT).getClass();
            });
        });
    }

    /**
     * Search for pattern suitable generator class
     *
     * @param fieldName to check for pattern mathing with generator
     * @param type      desired target type
     * @return suitable pattern generator
     */
    private Optional<Class<? extends IGenerator>> getSuitableGeneratorClass(String fieldName, Class<?> type) {
        final Stream<IGenerator> allGeneratorStream = classifiers.values().stream().flatMap(List::stream);
        final Optional<? extends IGenerator> patternSuitable = getSuitableGenerator(fieldName, allGeneratorStream);
        if (!patternSuitable.isPresent())
            return Optional.empty();

        final Object casted = CastUtils.castObject(patternSuitable.get().generate(), type);
        if (casted != null)
            return Optional.of(patternSuitable.get().getClass());

        final Stream<IGenerator> typedGeneratorStream = classifiers.get(type).stream();
        return getSuitableGenerator(fieldName, typedGeneratorStream)
                .map(g -> g.getClass());
    }

    @SuppressWarnings("ConstantConditions")
    private Optional<IGenerator> getSuitableGenerator(String fieldName, Stream<IGenerator> stream) {
        return stream.filter(g -> g.pattern() != null)
                .map(g -> Pair.of(g, g.pattern().matcher(fieldName)))
                .filter(p -> p.right().find())
                .sorted(Comparator.comparingInt((Pair<IGenerator, Matcher> p) -> FuzzySearch.ratio(fieldName, p.right().group()))
                        .reversed())
                .map(Pair::left)
                .findFirst();
    }

    @SuppressWarnings("unchecked")
    protected Map<Class, List<Class<? extends IGenerator>>> getClassifiedGenerators() {
        final String genPackage = "io.dummymaker.generator";
        final Map<Class, List<Class<? extends IGenerator>>> scannedClasses = new ClassScanner().scan(genPackage).stream()
                .filter(IGenerator.class::isAssignableFrom)
                .filter(c -> !c.isInterface())
                .filter(c -> !c.isAnonymousClass())
                .filter(c -> !c.isSynthetic())
                .map(c -> ((Class<? extends IGenerator>) c))
                .flatMap(c -> getGeneratorType(c).stream()
                        .map(t -> Pair.of(t, c)))
                .collect(Collectors.toMap(
                        Pair::left,
                        p -> Collections.singletonList(p.right()),
                        (l1, l2) -> Stream.of(l1, l2).flatMap(List::stream).collect(Collectors.toList())));

        return setPrimitiveClassifiers(scannedClasses);
    }

    protected Map<Class, List<Class<? extends IGenerator>>> setPrimitiveClassifiers(Map<Class, List<Class<? extends IGenerator>>> classifiers) {
        classifiers.put(boolean.class, classifiers.get(Boolean.class));
        classifiers.put(byte.class, classifiers.get(Byte.class));
        classifiers.put(short.class, classifiers.get(Short.class));
        classifiers.put(char.class, classifiers.get(Character.class));
        classifiers.put(int.class, classifiers.get(Integer.class));
        classifiers.put(long.class, classifiers.get(Long.class));
        classifiers.put(float.class, classifiers.get(Float.class));
        classifiers.put(double.class, classifiers.get(Double.class));
        classifiers.put(Object.class, Arrays.asList(ObjectGenerator.class));

        return classifiers;
    }

    private List<Class> getGeneratorType(Class<?> generator) {
        if (Object.class.equals(generator) || !IGenerator.class.isAssignableFrom(generator))
            return Collections.emptyList();

        final List<Class> special = getSpecialGeneratorTypes(generator);
        if (!special.isEmpty())
            return special;

        final List<Type> types = GenUtils.getTypes(generator);
        return types.stream()
                .filter(GenUtils::isGenerator)
                .map(GenUtils::getInterfaceType)
                .findFirst()
                .orElseGet(() -> Arrays.asList(Object.class));
    }

    protected List<Class> getSpecialGeneratorTypes(Class<?> generator) {
        if (generator.equals(ListComplexGenerator.class)) {
            return Arrays.asList(List.class, LinkedList.class, ArrayList.class, CopyOnWriteArrayList.class);
        } else if (generator.equals(SetComplexGenerator.class)) {
            return Arrays.asList(Set.class, HashSet.class, ConcurrentSkipListSet.class, CopyOnWriteArraySet.class,
                    LinkedHashSet.class);
        } else if (generator.equals(MapComplexGenerator.class)) {
            return Arrays.asList(Map.class, HashMap.class, IdentityHashMap.class, WeakHashMap.class, ConcurrentHashMap.class,
                    TreeMap.class, ConcurrentSkipListMap.class);
        } else if (generator.equals(Array2DComplexGenerator.class)) {
            return Arrays.asList(Object.class);
        } else if (generator.equals(ArrayComplexGenerator.class)) {
            return Arrays.asList(Object.class);
        } else if (generator.equals(TimeComplexGenerator.class)) {
            return Arrays.asList(Object.class);
        } else if (generator.equals(EnumComplexGenerator.class)) {
            return Arrays.asList(Enumeration.class);
        }

        return Collections.emptyList();
    }
}
