package io.dummymaker.scan.old.impl;

import io.dummymaker.factory.old.GenSupplier;
import io.dummymaker.model.old.GenContainer;
import io.dummymaker.model.old.GenRule;
import io.dummymaker.model.old.GenRules;
import java.lang.reflect.Field;
import java.util.*;
import org.jetbrains.annotations.NotNull;

/**
 * Scanner that sets gen rules where needed
 *
 * @author Anton Kurako (GoodforGod)
 * @see MainGenAutoScanner
 * @see GenRules
 * @since 18.08.2019
 */
public class GenRuledScanner extends MainGenAutoScanner {

    private final GenRules rules;

    public GenRuledScanner(GenSupplier supplier, GenRules rules) {
        super(supplier);
        this.rules = rules;
    }

    @Override
    public @NotNull List<GenContainer> scan(Class<?> target) {
        return scan(target, false);
    }

    @Override
    public @NotNull List<GenContainer> scan(Class<?> target, boolean isDefaultAuto) {
        final Optional<GenRule> targeted = (rules == null)
                ? Optional.empty()
                : rules.targeted(target);

        final boolean isAutoRuled = targeted.map(GenRule::isAuto).orElse(isDefaultAuto);
        final List<GenContainer> scanned = super.scan(target, isAutoRuled);
        if (!targeted.isPresent()) {
            return scanned;
        }

        final Map<Field, GenContainer> containers = new LinkedHashMap<>();
        targeted.ifPresent(r -> {
            final List<Field> validFields = getValidFields(target);
            validFields.stream()
                    .filter(f -> !isIgnored(f))
                    .forEach(f -> {
                        final GenContainer container = r.getDesiredExample(f)
                                .map(g -> GenContainer.asExample(f, g, isComplex(f)))
                                .orElseGet(() -> r.getDesired(f)
                                        .map(g -> GenContainer.asAuto(f, g, isComplex(f)))
                                        .orElseGet(() -> scanned.stream()
                                                .filter(c -> c.getField().equals(f))
                                                .findFirst().orElse(null)));

                        if (container != null && !r.isIgnored(f)) {
                            containers.put(f, container);
                        }
                    });
        });

        return new ArrayList<>(containers.values());
    }
}
