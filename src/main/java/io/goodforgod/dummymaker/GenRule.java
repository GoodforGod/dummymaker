package io.goodforgod.dummymaker;

import io.goodforgod.dummymaker.annotation.GenAuto;
import io.goodforgod.dummymaker.annotation.GenDepth;
import io.goodforgod.dummymaker.generator.Generator;
import java.util.*;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Rule for settings generator type for specific field name or field type
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.11.2022
 */
public final class GenRule {

    static final Class<?> GLOBAL_MARKER = Void.class;

    private final Integer depth;
    private final Boolean isAuto;
    private final Class<?> target;
    private final Set<String> excludedFields;
    private final Set<GenRuleField> specifiedFields;

    private GenRule(Class<?> target, Boolean isAuto, Integer depth) {
        this.isAuto = isAuto;
        this.target = target;
        this.depth = depth;
        this.excludedFields = new LinkedHashSet<>();
        this.specifiedFields = new LinkedHashSet<>();
    }

    private GenRule(Integer depth,
                    Boolean isAuto,
                    Class<?> target,
                    Set<String> excludedFields,
                    Set<GenRuleField> specifiedFields) {
        this.depth = depth;
        this.isAuto = isAuto;
        this.target = target;
        this.excludedFields = excludedFields;
        this.specifiedFields = specifiedFields;
    }

    @NotNull
    public static GenRule ofClass(@NotNull Class<?> target) {
        return ofClassInner(target, null, null);
    }

    @NotNull
    public static GenRule ofClass(@NotNull Class<?> target, boolean isGenAuto) {
        return ofClassInner(target, isGenAuto, null);
    }

    @NotNull
    public static GenRule ofClass(@NotNull Class<?> target, int depth) {
        return ofClassInner(target, null, depth);
    }

    /**
     * @param target    class to which rule is applied
     * @param isGenAuto specify than class is applicable for auto generation {@link GenAuto}
     * @param depth     specify class maximum depth {@link GenDepth}
     * @return class specific rule
     */
    @NotNull
    public static GenRule ofClass(@NotNull Class<?> target, boolean isGenAuto, int depth) {
        return ofClassInner(target, isGenAuto, depth);
    }

    private static GenRule ofClassInner(@NotNull Class<?> target, @Nullable Boolean isAuto, @Nullable Integer depth) {
        if (GLOBAL_MARKER.equals(target) || void.class.equals(target)) {
            throw new IllegalArgumentException(GLOBAL_MARKER + " can't be GenRule target class type");
        }

        if (depth != null && (depth < 1 || depth > GenDepth.MAX)) {
            throw new IllegalArgumentException(
                    "Depth must be between 1 and " + GenDepth.MAX + ", but was " + depth + " for " + target);
        }

        return new GenRule(target, isAuto, depth);
    }

    /**
     * @return global rule that is applied to all classes
     */
    @NotNull
    public static GenRule ofGlobal() {
        return new GenRule(GLOBAL_MARKER, null, null);
    }

    /**
     * @param field             name where generator will be applied to
     * @param generatorSupplier to apply for field with specified name
     * @return self
     */
    @NotNull
    public GenRule generateForNames(@NotNull String field,
                                    @NotNull Supplier<Generator<?>> generatorSupplier) {
        return generateForNames(Arrays.asList(field), generatorSupplier);
    }

    @NotNull
    public GenRule generateForNames(@NotNull String field1,
                                    @NotNull String field2,
                                    @NotNull Supplier<Generator<?>> generatorSupplier) {
        return generateForNames(Arrays.asList(field1, field2), generatorSupplier);
    }

    @NotNull
    public GenRule generateForNames(@NotNull String field1,
                                    @NotNull String field2,
                                    @NotNull String field3,
                                    @NotNull Supplier<Generator<?>> generatorSupplier) {
        return generateForNames(Arrays.asList(field1, field2, field3), generatorSupplier);
    }

    @NotNull
    public GenRule generateForNames(@NotNull String field1,
                                    @NotNull String field2,
                                    @NotNull String field3,
                                    @NotNull String field4,
                                    @NotNull Supplier<Generator<?>> generatorSupplier) {
        return generateForNames(Arrays.asList(field1, field2, field3, field4), generatorSupplier);
    }

    @NotNull
    public GenRule generateForNames(@NotNull String field1,
                                    @NotNull String field2,
                                    @NotNull String field3,
                                    @NotNull String field4,
                                    @NotNull String field5,
                                    @NotNull Supplier<Generator<?>> generatorSupplier) {
        return generateForNames(Arrays.asList(field1, field2, field3, field4, field5), generatorSupplier);
    }

    @NotNull
    public GenRule generateForNames(@NotNull String field1,
                                    @NotNull String field2,
                                    @NotNull String field3,
                                    @NotNull String field4,
                                    @NotNull String field5,
                                    @NotNull String field6,
                                    @NotNull Supplier<Generator<?>> generatorSupplier) {
        return generateForNames(Arrays.asList(field1, field2, field3, field4, field5, field6), generatorSupplier);
    }

    @NotNull
    public GenRule generateForNames(@NotNull String field1,
                                    @NotNull String field2,
                                    @NotNull String field3,
                                    @NotNull String field4,
                                    @NotNull String field5,
                                    @NotNull String field6,
                                    @NotNull String field7,
                                    @NotNull Supplier<Generator<?>> generatorSupplier) {
        return generateForNames(Arrays.asList(field1, field2, field3, field4, field5, field6, field7), generatorSupplier);
    }

    @NotNull
    public GenRule generateForNames(@NotNull String field1,
                                    @NotNull String field2,
                                    @NotNull String field3,
                                    @NotNull String field4,
                                    @NotNull String field5,
                                    @NotNull String field6,
                                    @NotNull String field7,
                                    @NotNull String field8,
                                    @NotNull Supplier<Generator<?>> generatorSupplier) {
        return generateForNames(Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8), generatorSupplier);
    }

    @NotNull
    public GenRule generateForNames(@NotNull String field1,
                                    @NotNull String field2,
                                    @NotNull String field3,
                                    @NotNull String field4,
                                    @NotNull String field5,
                                    @NotNull String field6,
                                    @NotNull String field7,
                                    @NotNull String field8,
                                    @NotNull String field9,
                                    @NotNull Supplier<Generator<?>> generatorSupplier) {
        return generateForNames(Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9),
                generatorSupplier);
    }

    /**
     * @param fieldNames        where generator will be applied to
     * @param generatorSupplier to apply for field with specified name
     * @return self
     */
    @NotNull
    public GenRule generateForNames(@NotNull Collection<String> fieldNames,
                                    @NotNull Supplier<Generator<?>> generatorSupplier) {
        for (String fieldName : fieldNames) {
            final GenRuleField rule = new GenRuleField(generatorSupplier, fieldName);
            this.specifiedFields.add(rule);
        }
        return this;
    }

    /**
     * @param type              where generator will be applied to
     * @param generatorSupplier to apply for field with specified name
     * @return self
     */
    @NotNull
    public GenRule generateForTypes(@NotNull Class<?> type,
                                    @NotNull Supplier<Generator<?>> generatorSupplier) {
        return generateForTypes(Arrays.asList(type), generatorSupplier);
    }

    @NotNull
    public GenRule generateForTypes(@NotNull Class<?> type1,
                                    @NotNull Class<?> type2,
                                    @NotNull Supplier<Generator<?>> generatorSupplier) {
        return generateForTypes(Arrays.asList(type1, type2), generatorSupplier);
    }

    @NotNull
    public GenRule generateForTypes(@NotNull Class<?> type1,
                                    @NotNull Class<?> type2,
                                    @NotNull Class<?> type3,
                                    @NotNull Supplier<Generator<?>> generatorSupplier) {
        return generateForTypes(Arrays.asList(type1, type2, type3), generatorSupplier);
    }

    @NotNull
    public GenRule generateForTypes(@NotNull Class<?> type1,
                                    @NotNull Class<?> type2,
                                    @NotNull Class<?> type3,
                                    @NotNull Class<?> type4,
                                    @NotNull Supplier<Generator<?>> generatorSupplier) {
        return generateForTypes(Arrays.asList(type1, type2, type3, type4), generatorSupplier);
    }

    @NotNull
    public GenRule generateForTypes(@NotNull Class<?> type1,
                                    @NotNull Class<?> type2,
                                    @NotNull Class<?> type3,
                                    @NotNull Class<?> type4,
                                    @NotNull Class<?> type5,
                                    @NotNull Supplier<Generator<?>> generatorSupplier) {
        return generateForTypes(Arrays.asList(type1, type2, type3, type4, type5), generatorSupplier);
    }

    @NotNull
    public GenRule generateForTypes(@NotNull Class<?> type1,
                                    @NotNull Class<?> type2,
                                    @NotNull Class<?> type3,
                                    @NotNull Class<?> type4,
                                    @NotNull Class<?> type5,
                                    @NotNull Class<?> type6,
                                    @NotNull Supplier<Generator<?>> generatorSupplier) {
        return generateForTypes(Arrays.asList(type1, type2, type3, type4, type5, type6), generatorSupplier);
    }

    @NotNull
    public GenRule generateForTypes(@NotNull Class<?> type1,
                                    @NotNull Class<?> type2,
                                    @NotNull Class<?> type3,
                                    @NotNull Class<?> type4,
                                    @NotNull Class<?> type5,
                                    @NotNull Class<?> type6,
                                    @NotNull Class<?> type7,
                                    @NotNull Supplier<Generator<?>> generatorSupplier) {
        return generateForTypes(Arrays.asList(type1, type2, type3, type4, type5, type6, type7), generatorSupplier);
    }

    @NotNull
    public GenRule generateForTypes(@NotNull Class<?> type1,
                                    @NotNull Class<?> type2,
                                    @NotNull Class<?> type3,
                                    @NotNull Class<?> type4,
                                    @NotNull Class<?> type5,
                                    @NotNull Class<?> type6,
                                    @NotNull Class<?> type7,
                                    @NotNull Class<?> type8,
                                    @NotNull Supplier<Generator<?>> generatorSupplier) {
        return generateForTypes(Arrays.asList(type1, type2, type3, type4, type5, type6, type7, type8), generatorSupplier);
    }

    @NotNull
    public GenRule generateForTypes(@NotNull Class<?> type1,
                                    @NotNull Class<?> type2,
                                    @NotNull Class<?> type3,
                                    @NotNull Class<?> type4,
                                    @NotNull Class<?> type5,
                                    @NotNull Class<?> type6,
                                    @NotNull Class<?> type7,
                                    @NotNull Class<?> type8,
                                    @NotNull Class<?> type9,
                                    @NotNull Supplier<Generator<?>> generatorSupplier) {
        return generateForTypes(Arrays.asList(type1, type2, type3, type4, type5, type6, type7, type8, type9), generatorSupplier);
    }

    /**
     * @param fieldTypes        where generator will be applied to
     * @param generatorSupplier to apply for field with specified name
     * @return self
     */
    @NotNull
    public GenRule generateForTypes(@NotNull List<Class<?>> fieldTypes,
                                    @NotNull Supplier<Generator<?>> generatorSupplier) {
        for (Class<?> fieldType : fieldTypes) {
            final GenRuleField rule = new GenRuleField(generatorSupplier, fieldType);
            this.specifiedFields.add(rule);
        }
        return this;
    }

    /**
     * @param fieldNames to exclude from generating random values
     * @return self
     */
    @NotNull
    public GenRule excludeFields(@NotNull String... fieldNames) {
        this.excludedFields.addAll(Arrays.asList(fieldNames));
        return this;
    }

    GenRule build() {
        return new GenRule(depth, isAuto, target, Collections.unmodifiableSet(excludedFields),
                Collections.unmodifiableSet(specifiedFields));
    }

    GenRule merge(GenRule rule) {
        if (getTarget().equals(rule.getTarget()) || rule.isGlobal()) {
            this.specifiedFields.addAll(rule.specifiedFields);
            this.excludedFields.addAll(rule.excludedFields);
        }
        return this;
    }

    boolean isGlobal() {
        return GLOBAL_MARKER.equals(target);
    }

    Optional<Integer> getDepth() {
        return Optional.ofNullable(depth);
    }

    Optional<Boolean> isAuto() {
        return Optional.ofNullable(isAuto);
    }

    Class<?> getTarget() {
        return target;
    }

    Set<String> getExcludedFields() {
        return excludedFields;
    }

    Set<GenRuleField> getSpecifiedFields() {
        return specifiedFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GenRule))
            return false;
        GenRule genRule = (GenRule) o;
        return Objects.equals(target, genRule.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(target);
    }

    @Override
    public String toString() {
        return "[type=" + target + ']';
    }
}
