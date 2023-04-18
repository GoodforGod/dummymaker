package io.goodforgod.dummymaker;

import io.goodforgod.dummymaker.annotation.GenDepth;
import io.goodforgod.dummymaker.cases.NamingCase;
import io.goodforgod.dummymaker.cases.NamingCases;
import io.goodforgod.dummymaker.generator.Localisation;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 17.12.2022
 */
final class DefaultGenFactoryBuilder implements GenFactory.Builder {

    private final List<GenRule> rules = new ArrayList<>();
    private long seed = System.nanoTime();
    private boolean autoByDefault = true;
    private int depthByDefault = GenDepth.DEFAULT;
    private boolean ignoreErrors = false;
    private boolean overrideDefaultValues = true;
    @Nullable
    private Localisation localisation;
    private NamingCase namingCaseStrategy = NamingCases.DEFAULT;

    @NotNull
    @Override
    public GenFactory.Builder seed(long seed) {
        this.seed = seed;
        return this;
    }

    @NotNull
    @Override
    public GenFactory.Builder addRule(@NotNull GenRule rule) {
        this.rules.add(rule);
        return this;
    }

    @NotNull
    @Override
    public GenFactory.Builder applyCase(@NotNull NamingCase namingCase) {
        this.namingCaseStrategy = namingCase;
        return this;
    }

    @NotNull
    @Override
    public GenFactory.Builder autoByDefault(boolean autoByDefault) {
        this.autoByDefault = autoByDefault;
        return this;
    }

    @NotNull
    @Override
    public GenFactory.Builder depthByDefault(int depthByDefault) {
        if (depthByDefault > GenDepth.MAX || depthByDefault < 1) {
            throw new IllegalArgumentException("Depth should be from 1 to " + GenDepth.MAX);
        }

        this.depthByDefault = depthByDefault;
        return this;
    }

    @NotNull
    @Override
    public GenFactory.Builder ignoreExceptions(boolean ignoreExceptions) {
        this.ignoreErrors = ignoreExceptions;
        return this;
    }

    @NotNull
    @Override
    public GenFactory.Builder overrideDefaultValues(boolean overrideDefaultValues) {
        this.overrideDefaultValues = overrideDefaultValues;
        return this;
    }

    @NotNull
    @Override
    public GenFactory.Builder localisation(@NotNull Localisation localisation) {
        this.localisation = localisation;
        return this;
    }

    @Override
    public @NotNull GenFactory build() {
        return new DefaultGenFactory(seed, GenRules.of(rules), autoByDefault, depthByDefault,
                ignoreErrors, overrideDefaultValues, namingCaseStrategy, localisation);
    }
}
