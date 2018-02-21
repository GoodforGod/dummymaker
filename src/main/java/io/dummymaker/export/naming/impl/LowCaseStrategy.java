package io.dummymaker.export.naming.impl;

import io.dummymaker.export.naming.IStrategy;

/**
 * All words are in low case: Bob - bob, TOnY - tony
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class LowCaseStrategy implements IStrategy {

    @Override
    public String toStrategy(String value) {
        return value.toLowerCase();
    }
}
