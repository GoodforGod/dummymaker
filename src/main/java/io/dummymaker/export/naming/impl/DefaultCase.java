package io.dummymaker.export.naming.impl;

import io.dummymaker.export.naming.ICase;

/**
 * Returns default value as is.
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class DefaultCase implements ICase {

    @Override
    public String format(String value) {
        return value;
    }
}
