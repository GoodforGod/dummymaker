package io.dummymaker.scan.impl;

import io.dummymaker.factory.IGenSupplier;
import io.dummymaker.model.GenContainer;
import io.dummymaker.model.GenRule;
import io.dummymaker.model.GenRules;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * Scanner that sets gen rules where needed
 *
 * @author GoodforGod
 * @see GenAutoScanner
 * @see GenRules
 * @since 18.08.2019
 */
public class GenRuledScanner extends GenAutoScanner {

    private final GenRules rules;

    public GenRuledScanner(IGenSupplier supplier, GenRules rules) {
        super(supplier);
        this.rules = rules;
    }

    @Override
    public @NotNull Map<Field, GenContainer> scan(Class target) {
        return scan(target, false);
    }

    @Override
    public @NotNull Map<Field, GenContainer> scan(Class target, boolean isDefaultAuto) {
        final Optional<GenRule> targeted = (rules == null)
                ? Optional.empty()
                : rules.targeted(target);
        final boolean isAutoRuled = targeted.map(GenRule::isAuto).orElse(isDefaultAuto);

        final Map<Field, GenContainer> scanned = super.scan(target, isAutoRuled);
        if (!targeted.isPresent())
            return scanned;

        final Map<Field, GenContainer> containers = new LinkedHashMap<>();
        targeted.ifPresent(r -> getValidFields(target).stream()
                .filter(f -> !isIgnored(f))
                .forEach(f -> {
                    final GenContainer container = r.getDesiredExample(f)
                            .map(g -> GenContainer.asExample(f, g, isComplex(f)))
                            .orElseGet(() -> r.getDesired(f)
                                    .map(g -> GenContainer.asAuto(f, g, isComplex(f)))
                                    .orElse(scanned.get(f)));

                    if (container != null && !r.isIgnored(f))
                        containers.put(f, container);
                }));

        return containers;
    }
}
