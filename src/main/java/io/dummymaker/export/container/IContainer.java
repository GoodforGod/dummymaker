package io.dummymaker.export.container;

import java.util.List;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 28.08.2017
 */
public interface IContainer {
    List<String> values();

    String value(final int index);
}
