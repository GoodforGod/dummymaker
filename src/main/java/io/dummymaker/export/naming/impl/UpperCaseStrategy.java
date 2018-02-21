package io.dummymaker.export.naming.impl;

import io.dummymaker.export.naming.IStrategy;

/**
 * All words are in upper case: look - LOOK
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class UpperCaseStrategy implements IStrategy {

    @Override
    public String toStrategy(String value) {
        return value.toUpperCase();
    }
}
