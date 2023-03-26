package io.dummymaker;

import io.dummymaker.annotation.GenAuto;
import io.dummymaker.annotation.GenDepth;
import io.dummymaker.generator.Generator;
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
        this.excludedFields = new HashSet<>();
        this.specifiedFields = new HashSet<>();
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
     * @param generatorSupplier that will be applied to fields by their names
     * @param fieldNames        to register generator for
     * @return self
     */
    @NotNull
    public GenRule registerFieldNames(@NotNull Supplier<Generator<?>> generatorSupplier, @NotNull String... fieldNames) {
        for (String fieldName : fieldNames) {
            final GenRuleField rule = new GenRuleField(generatorSupplier, fieldName);
            this.specifiedFields.add(rule);
        }
        return this;
    }

    /**
     * @param generatorSupplier that will be applied to fields by their type
     * @param fieldType         to register generator for
     * @return self
     */
    @NotNull
    public GenRule registerFieldType(@NotNull Supplier<Generator<?>> generatorSupplier, @NotNull Class<?> fieldType) {
        final GenRuleField rule = new GenRuleField(generatorSupplier, fieldType);
        this.specifiedFields.add(rule);
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

    @NotNull
    GenRule merge(@NotNull GenRule rule) {
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
        return Objects.equals(depth, genRule.depth) && Objects.equals(isAuto, genRule.isAuto)
                && Objects.equals(target, genRule.target) && Objects.equals(excludedFields, genRule.excludedFields)
                && Objects.equals(specifiedFields, genRule.specifiedFields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depth, isAuto, target, excludedFields, specifiedFields);
    }
}
