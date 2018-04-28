package io.dummymaker.export.naming.impl;

import io.dummymaker.export.naming.ICase;

/**
 * All words are in low case: Bob - bob, TOnY - tony
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class LowCase implements ICase {

    @Override
    public String format(String value) {
        return value.toLowerCase();
    }
}
