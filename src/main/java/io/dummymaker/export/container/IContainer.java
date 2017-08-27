package io.dummymaker.export.container;

import java.util.List;

/**
 * Container class stores exported data
 *
 * Used to easily manipulate and store data
 */
public interface IContainer {
    List<String> values();

    String value(final int index);
}
