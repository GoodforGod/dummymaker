package io.dummymaker;

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

    private static final Class<?> GLOBAL_MARKER = Void.class;

    private final Integer depth;
    private final Boolean isAuto;
    private final Class<?> target;
    private final Set<String> excludedFields = new HashSet<>();
    private final Set<GenRuleField> specifiedFields = new HashSet<>();

    private GenRule(Class<?> target, Boolean isAuto, Integer depth) {
        this.isAuto = isAuto;
        this.target = target;
        this.depth = depth;
    }

    @NotNull
    public static GenRule ofClass(@NotNull Class<?> target) {
        return ofClassInner(target, null, null);
    }

    @NotNull
    public static GenRule ofClass(@NotNull Class<?> target, boolean isAuto) {
        return ofClassInner(target, isAuto, null);
    }

    @NotNull
    public static GenRule ofClass(@NotNull Class<?> target, int depth) {
        return ofClassInner(target, null, depth);
    }

    @NotNull
    public static GenRule ofClass(@NotNull Class<?> target, boolean isAuto, int depth) {
        return ofClassInner(target, isAuto, depth);
    }

    private static GenRule ofClassInner(@NotNull Class<?> target, @Nullable Boolean isAuto, @Nullable Integer depth) {
        if (GLOBAL_MARKER.equals(target)) {
            throw new IllegalArgumentException(GLOBAL_MARKER + " can't be GenRule type");
        }

        if (depth != null && (depth < 1 || depth > GenDepth.MAX)) {
            throw new IllegalArgumentException("Depth must be between 1 and 50, but was " + depth + " for " + target);
        }

        return new GenRule(target, isAuto, depth);
    }

    @NotNull
    public static GenRule ofGlobal() {
        return new GenRule(GLOBAL_MARKER, null, null);
    }

    @NotNull
    public GenRule registerFields(@NotNull Supplier<Generator<?>> generatorSupplier, @NotNull String... fieldNames) {
        final GenRuleField rule = new GenRuleField(generatorSupplier, fieldNames);
        this.specifiedFields.add(rule);
        return this;
    }

    @NotNull
    public GenRule registerType(@NotNull Supplier<Generator<?>> generatorSupplier, @NotNull Class<?> fieldType) {
        final GenRuleField rule = new GenRuleField(generatorSupplier, fieldType);
        this.specifiedFields.add(rule);
        return this;
    }

    @NotNull
    public GenRule excludeFields(@NotNull String... fieldNames) {
        this.excludedFields.addAll(Arrays.asList(fieldNames));
        return this;
    }

    @NotNull
    Optional<Generator<?>> find(@NotNull Class<?> type, @NotNull String fieldName) {
        if (isExcluded(fieldName)) {
            return Optional.empty();
        }

        final Optional<Generator<?>> namedGenerator = specifiedFields.stream()
                .filter(r -> r.getNames().contains(fieldName))
                .findAny()
                .map(rule -> rule.getGeneratorSupplier().get());

        if (namedGenerator.isPresent()) {
            return namedGenerator;
        }

        return find(type);
    }

    @NotNull
    Optional<Generator<?>> find(Class<?> type) {
        if (type == null) {
            return Optional.empty();
        }

        final Optional<? extends Generator<?>> equalType = specifiedFields.stream()
                .filter(GenRuleField::isTyped)
                .filter(rule -> type.equals(rule.getType()))
                .findAny()
                .map(r -> r.getGeneratorSupplier().get());

        if (equalType.isPresent()) {
            return Optional.of(equalType.get());
        }

        return specifiedFields.stream()
                .filter(GenRuleField::isTyped)
                .filter(rule -> type.isAssignableFrom(rule.getType()))
                .findAny()
                .map(r -> r.getGeneratorSupplier().get());
    }

    @NotNull
    GenRule merge(@NotNull GenRule rule) {
        if (!getTarget().equals(rule.getTarget())) {
            return this;
        }

        for (GenRuleField fieldRule : rule.specifiedFields) {
            for (GenRuleField innerFieldRule : specifiedFields) {
                if (fieldRule.equals(innerFieldRule) && fieldRule.isTyped()) {
                    throw new IllegalArgumentException("Multiple Rules describe same type: " + fieldRule.getType());
                }
            }
        }

        this.specifiedFields.addAll(rule.specifiedFields);
        this.excludedFields.addAll(rule.excludedFields);
        return this;
    }

    boolean isGlobal() {
        return GLOBAL_MARKER.equals(target);
    }

    boolean isExcluded(String fieldName) {
        return excludedFields.contains(fieldName);
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
