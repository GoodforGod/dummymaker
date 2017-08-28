package io.dummymaker.export.container.format;

import java.util.List;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 28.08.2017
 */
public interface IFormatContainer {
    List<String> values();

    String value(final int index);
}
