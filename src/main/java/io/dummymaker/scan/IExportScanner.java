package io.dummymaker.scan;

import io.dummymaker.export.ICase;
import io.dummymaker.model.export.FieldContainer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * Scanner for special export annotations
 *
 * @author GoodforGod
 * @since 27.04.2018
 */
public interface IExportScanner extends IScanner<FieldContainer, Class<?>> {

    @NotNull
    @Override
    Collection<FieldContainer> scan(Class<?> target);

    @NotNull
    Collection<FieldContainer> scan(Class<?> target, ICase nameCase);
}
