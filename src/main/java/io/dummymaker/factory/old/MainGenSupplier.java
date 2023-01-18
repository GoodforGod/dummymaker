package io.dummymaker.factory.old;

import static io.dummymaker.util.CollectionUtils.getIndexWithSalt;

import io.dummymaker.generator.Generator;
import io.dummymaker.generator.complex.*;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import io.dummymaker.generator.simple.ObjectGenerator;
import io.dummymaker.generator.simple.string.JsonGenerator;
import io.dummymaker.model.old.Pair;
import io.dummymaker.scan.old.impl.ClassScanner;
import io.dummymaker.util.CastUtils;
import io.dummymaker.util.CollectionUtils;
import io.dummymaker.util.GenUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Default gen config implementation for generators discovery With all library generators and their
 * patterns availability
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenSupplier
 * @since 27.07.2019
 */
@Deprecated
public final class MainGenSupplier implements GenSupplier {

    private static final String DUMMY_GENERATOR_PACKAGE = "io.dummymaker.generator";

    /**
     * Salt used to select always the same generator for specific field
     */
    private static final int SALT = ((Long) System.currentTimeMillis()).hashCode();

    /**
     * Map of classified generators and their target classes
     */
    private static final Map<Class, List<Class<? extends Generator>>> classifiersClasses = getClassifiedGenerators();

    private final Map<Class, List<Generator>> classifiers = classifiersClasses.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream()
                    .map(CastUtils::instantiate)
                    .filter(Objects::nonNull)
                    .sorted(Generator::compareTo)
                    .collect(Collectors.toList())));

    @Override
    public @NotNull Class<? extends Generator> getSuitable(@NotNull Field field) {
        return getSuitable(field, field.getType());
    }

    /**
     * Default generator in case suitable not found
     *
     * @return generator class
     */
    @NotNull
    private Class<? extends Generator> getDefault() {
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
    public @NotNull Class<? extends Generator> getSuitable(@NotNull Field field, @Nullable Class<?> type) {
        if (type == null)
            return getDefault();

        final List<? extends Generator> generators = classifiers.get(type);
        final String fieldName = field.getName();

        if (type.getTypeName().endsWith("[][]"))
            return Array2DComplexGenerator.class;
        if (type.getTypeName().endsWith("[]"))
            return ArrayComplexGenerator.class;
        if (type.isEnum())
            return EnumComplexGenerator.class;
        if (CollectionUtils.isEmpty(generators))
            return getDefault();

        return getSuitableGeneratorClass(fieldName, type)
                .orElseGet(() -> {
                    final List<? extends Generator> nonJsonGenerators = generators.stream()
                            .filter(g -> !(g instanceof JsonGenerator))
                            .collect(Collectors.toList());

                    return getIndexWithSalt(nonJsonGenerators, fieldName + type.getName(), SALT).getClass();
                });
    }

    /**
     * Search for pattern suitable generator class
     *
     * @param fieldName to check for pattern mathing with generator
     * @param type      desired target type
     * @return suitable pattern generator
     */
    private Optional<Class<? extends Generator>> getSuitableGeneratorClass(String fieldName, Class<?> type) {
        final Collection<Generator> allGenerators = classifiers.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        final Optional<? extends Generator> patternSuitable = getSuitableGenerator(fieldName, allGenerators);
        if (!patternSuitable.isPresent()) {
            return Optional.empty();
        }

        final Object casted = CastUtils.castObject(patternSuitable.get().get(), type);
        if (casted != null) {
            return Optional.of(patternSuitable.get().getClass());
        }

        final Collection<Generator> typedGeneratorStream = classifiers.get(type);
        return getSuitableGenerator(fieldName, typedGeneratorStream)
                .map(Generator::getClass);
    }

    @SuppressWarnings("ConstantConditions")
    private Optional<Generator> getSuitableGenerator(String fieldName, Collection<Generator> generators) {
        return generators.stream()
                .filter(g -> g.pattern() != null && g.pattern().matcher(fieldName).find())
                .min(Generator::compareTo);
    }

    @SuppressWarnings("unchecked")
    private static Map<Class, List<Class<? extends Generator>>> getClassifiedGenerators() {
        final ClassScanner scanner = new ClassScanner();
        final Map<Class, List<Class<? extends Generator>>> scannedClasses = scanner.scan(DUMMY_GENERATOR_PACKAGE).stream()
                .filter(Generator.class::isAssignableFrom)
                .filter(c -> !c.isInterface())
                .filter(c -> !c.isAnonymousClass())
                .filter(c -> !c.isSynthetic())
                .map(c -> ((Class<? extends Generator>) c))
                .flatMap(c -> getGeneratorType(c).stream()
                        .map(t -> Pair.of(t, c)))
                .collect(Collectors.toMap(
                        Pair::left,
                        p -> Collections.singletonList(p.right()),
                        (l1, l2) -> Stream.of(l1, l2).flatMap(List::stream).collect(Collectors.toList())));

        return setPrimitiveClassifiers(scannedClasses);
    }

    private static Map<Class, List<Class<? extends Generator>>>
            setPrimitiveClassifiers(Map<Class, List<Class<? extends Generator>>> classifiers) {
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

    private static List<Class> getGeneratorType(Class<?> generator) {
        if (Object.class.equals(generator) || !Generator.class.isAssignableFrom(generator))
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

    private static List<Class> getSpecialGeneratorTypes(Class<?> generator) {
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
