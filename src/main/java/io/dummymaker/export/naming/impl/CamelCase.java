package io.dummymaker.export.naming.impl;

import io.dummymaker.export.naming.ICase;

/**
 * First letter is low case, next letters are as is: Bobby - bobby, TonNy - tonNy
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class CamelCase implements ICase {

    @Override
    public String format(String value) {
        return (value.length() == 1)
                ? value.toLowerCase()
                : value.substring(0, 1).toLowerCase() + value.substring(1, value.length());
    }
}
