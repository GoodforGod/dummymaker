package io.dummymaker.util;

import io.dummymaker.generator.complex.impl.*;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.NullGenerator;
import io.dummymaker.generator.simple.impl.ObjectGenerator;
import io.dummymaker.generator.simple.impl.time.ITimeGenerator;
import io.dummymaker.scan.impl.ClassScanner;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static io.dummymaker.util.CollectionUtils.getIndexWithSalt;

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

    @SuppressWarnings("unchecked")
    private static Map<Class, List<Class<? extends IGenerator>>> instantiateAutoGeneratorsMap() {
        final Collection<Class> scannedClasses = new ClassScanner().scan("io.dummymaker.generator");
        final List<Class<? extends IGenerator>> classes = scannedClasses.stream()
                .filter(IGenerator.class::isAssignableFrom)
                .filter(c -> !c.isInterface())
                .filter(c -> !c.isAnonymousClass())
                .filter(c -> !c.isSynthetic())
                .map(c -> ((Class<? extends IGenerator>) c))
                .collect(Collectors.toList());

        final Map<Class, List<Class<? extends IGenerator>>> generators = new HashMap<>();
        classes.forEach(c -> {
            final List<Class> types = getGeneratorType(c);
            types.forEach(t -> {
                final List<Class<? extends IGenerator>> list = generators.computeIfAbsent(t, (k) -> new ArrayList<>());
                list.add(c);
            });
        });

        generators.put(boolean.class, generators.get(Boolean.class));
        generators.put(byte.class, generators.get(Byte.class));
        generators.put(short.class, generators.get(Short.class));
        generators.put(char.class, generators.get(Character.class));
        generators.put(int.class, generators.get(Integer.class));
        generators.put(long.class, generators.get(Long.class));
        generators.put(float.class, generators.get(Float.class));
        generators.put(double.class, generators.get(Double.class));
        generators.put(Object.class, Arrays.asList(ObjectGenerator.class));

        return generators;
    }

    private static List<Class> getGeneratorType(Class<?> generator) {
        if (Object.class.equals(generator) || !IGenerator.class.isAssignableFrom(generator))
            return Collections.emptyList();

        final List<Class> special = getTypeForSpecial(generator);
        if (!special.isEmpty())
            return special;

        final List<Type> types = getTypes(generator);
        return types.stream()
                .filter(GenUtils::isGenerator)
                .map(GenUtils::getGeneratorInterfaceType)
                .findFirst().orElse(Arrays.asList(Object.class));
    }

    private static List<Class> getTypeForSpecial(Class<?> generator) {
        if (generator.equals(ListComplexGenerator.class)) {
            return Arrays.asList(List.class, LinkedList.class, ArrayList.class, CopyOnWriteArrayList.class);
        } else if (generator.equals(SetComplexGenerator.class)) {
            return Arrays.asList(Set.class, HashSet.class, ConcurrentSkipListSet.class, CopyOnWriteArraySet.class, LinkedHashSet.class);
        } else if (generator.equals(MapComplexGenerator.class)) {
            return Arrays.asList(Map.class, HashMap.class, IdentityHashMap.class, WeakHashMap.class, ConcurrentHashMap.class, TreeMap.class, ConcurrentSkipListMap.class);
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

    private static List<Class> getGeneratorInterfaceType(Type type) {
        try {
            return Arrays.asList((Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0]);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private static boolean isGenerator(Type type) {
        try {
            return ((ParameterizedType) type).getRawType().equals(IGenerator.class)
                    || ((ParameterizedType) type).getRawType().equals(ITimeGenerator.class);
        } catch (Exception e) {
            return false;
        }
    }

    private static List<Type> getTypes(Class<?> target) {
        if (Object.class.equals(target))
            return new ArrayList<>();

        try {
            final List<Type> types = getTypesFromClass(target);
            if (target.getSuperclass() != null)
                types.addAll(getTypes(target.getSuperclass()));

            return types;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static List<Type> getTypesFromInterfaces(Type targetType) {
        if (isGenerator(targetType) || ParameterizedType.class.equals(targetType))
            return new ArrayList<>();

        final Class targetClass = (Class) targetType;
        return getTypesFromClass(targetClass);
    }

    private static List<Type> getTypesFromClass(Class targetClass) {
        final List<Type> types = Arrays.stream((targetClass).getGenericInterfaces()).collect(Collectors.toList());
        final List<Type> collect = types.stream()
                .filter(i -> !ParameterizedType.class.equals(i))
                .map(GenUtils::getTypesFromInterfaces)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        types.addAll(collect);

        return types;
    }

    public static Class<? extends IGenerator> getAutoGenerator(Field field, Class<?> fieldClass) {
        return getAutoGenerator(fieldClass);
    }

    public static Class<? extends IGenerator> getAutoGenerator(Class<?> fieldClass) {
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
}
