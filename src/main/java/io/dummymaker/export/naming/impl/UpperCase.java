package io.dummymaker.export.naming.impl;

import io.dummymaker.export.naming.ICase;

/**
 * All words are in upper case: look - LOOK
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class UpperCase implements ICase {

    @Override
    public String format(String value) {
        return value.toUpperCase();
    }
}
