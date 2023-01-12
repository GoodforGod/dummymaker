package io.dummymaker.factory.refactored;

import io.dummymaker.annotation.GenDepth;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 17.12.2022
 */
final class DefaultGenFactoryBuilder implements GenFactory.Builder {

    private final List<GenRule> rules = new ArrayList<>();
    private long salt = System.nanoTime();
    private boolean autoByDefault = true;
    private int depthByDefault = GenDepth.DEFAULT;
    private boolean ignoreErrors = false;

    @NotNull
    @Override
    public GenFactory.Builder salt(long salt) {
        this.salt = salt;
        return this;
    }

    @NotNull
    @Override
    public GenFactory.Builder rule(@NotNull GenRule rule) {
        this.rules.add(rule);
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
        this.depthByDefault = depthByDefault;
        return this;
    }

    @Override
    public GenFactory.@NotNull Builder ignoreExceptions(boolean ignoreExceptions) {
        this.ignoreErrors = ignoreExceptions;
        return this;
    }

    @Override
    public @NotNull GenFactory build() {
        return new DefaultGenFactory(salt, GenRules.of(rules), autoByDefault, depthByDefault, ignoreErrors);
    }
}
