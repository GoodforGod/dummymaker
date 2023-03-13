package io.dummymaker;

import io.dummymaker.generator.Generator;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 20.12.2022
 */
final class RuleBasedGeneratorSupplier implements GeneratorSupplier {

    private final GenRules rules;
    private final GeneratorSupplier defaultGeneratorSupplier;

    public RuleBasedGeneratorSupplier(GenRules rules, GeneratorSupplier defaultGeneratorSupplier) {
        this.rules = rules;
        this.defaultGeneratorSupplier = defaultGeneratorSupplier;
    }

    @Override
    public @NotNull Generator<?> get(@NotNull Class<?> type) {
        final Optional<Generator<?>> ruleBased = rules.rules().stream()
                .map(rule -> rule.find(type))
                .findFirst()
                .flatMap(generator -> generator);

        return (ruleBased.isPresent())
                ? ruleBased.get()
                : defaultGeneratorSupplier.get(type);
    }

    @Override
    public @NotNull Generator<?> get(@NotNull Class<?> type, @NotNull String fieldName) {
        final Optional<? extends Generator<?>> generator = rules.rules().stream()
                .map(rule -> rule.find(type, fieldName))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        return (generator.isPresent())
                ? generator.get()
                : defaultGeneratorSupplier.get(type, fieldName);
    }
}
