package io.dummymaker.export.container;

import java.util.ArrayList;
import java.util.List;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 28.08.2017
 */
public class OriginContainer implements IContainer {

    protected final List<String> values = new ArrayList<>();

    @Override
    public List<String> values() {
        return values;
    }

    @Override
    public String value(int index) {
        return (values.size() <= index)
                ? null
                : values.get(index);
    }
}
