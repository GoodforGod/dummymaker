package io.dummymaker.export.naming;

import io.dummymaker.export.naming.impl.*;

/**
 * Naming strategy for class and field names
 *
 * Used in all exporters
 *
 * @see io.dummymaker.export.IExporter
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public enum Strategies {

    DEFAULT(new DefaultStrategy()),
    UPPER_CASE(new UpperCaseStrategy()),
    LOW_CASE(new LowCaseStrategy()),
    UNDERSCORED_LOW_CASE(new UnderscoredLowCaseStrategy()),
    UNDERSCORED_UPPER_CASE(new UnderscoredUpperCaseStrategy()),
    INITIAL_LOW_CASE(new InitialLowCaseStrategy());

    private final IStrategy strategy;

    Strategies(IStrategy strategy) {
        this.strategy = strategy;
    }

    public IStrategy getStrategy() {
        return strategy;
    }
}
