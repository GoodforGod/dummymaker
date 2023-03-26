package io.dummymaker;

import io.dummymaker.generator.Generator;
import java.util.*;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * Rule for settings generator type for specific field name or field type
 *
 * @author Anton Kurako (GoodforGod)
 * @since 27.03.2023
 */
final class GenRuleContext {

    private final Integer depth;
    private final Boolean isAuto;
    private final Class<?> target;
    private final Set<String> excludedFields;
    private final Set<GenRuleFieldContext> specifiedFields;

    GenRuleContext(GenRule rule) {
        this.depth = rule.getDepth().orElse(null);
        this.isAuto = rule.isAuto().orElse(null);
        this.target = rule.getTarget();
        this.excludedFields = rule.getExcludedFields();
        this.specifiedFields = rule.getSpecifiedFields().stream()
                .map(GenRuleFieldContext::new)
                .collect(Collectors.toSet());
    }

    Optional<Generator<?>> find(@NotNull Class<?> type, @NotNull String fieldName) {
        if (isExcluded(fieldName)) {
            return Optional.empty();
        }

        final Optional<Generator<?>> namedGenerator = specifiedFields.stream()
                .filter(rule -> fieldName.equals(rule.getName()))
                .findFirst()
                .map(GenRuleFieldContext::getGenerator);

        if (namedGenerator.isPresent()) {
            return namedGenerator;
        }

        return find(type);
    }

    Optional<Generator<?>> find(GenType type) {
        return find(type.raw());
    }

    Optional<Generator<?>> find(Class<?> type) {
        if (type == null) {
            return Optional.empty();
        }

        final Optional<? extends Generator<?>> equalGenerator = specifiedFields.stream()
                .filter(GenRuleFieldContext::isTyped)
                .filter(rule -> type.equals(rule.getType()))
                .findFirst()
                .map(GenRuleFieldContext::getGenerator);

        if (equalGenerator.isPresent()) {
            return Optional.of(equalGenerator.get());
        }

        return specifiedFields.stream()
                .filter(GenRuleFieldContext::isTyped)
                .filter(rule -> rule.getType().isAssignableFrom(type))
                .findFirst()
                .map(GenRuleFieldContext::getGenerator);
    }

    boolean isGlobal() {
        return GenRule.GLOBAL_MARKER.equals(target);
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

    Set<GenRuleFieldContext> getSpecifiedFields() {
        return specifiedFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GenRuleContext))
            return false;
        GenRuleContext genRule = (GenRuleContext) o;
        return Objects.equals(depth, genRule.depth) && Objects.equals(isAuto, genRule.isAuto)
                && Objects.equals(target, genRule.target) && Objects.equals(excludedFields, genRule.excludedFields)
                && Objects.equals(specifiedFields, genRule.specifiedFields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depth, isAuto, target, excludedFields, specifiedFields);
    }
}
