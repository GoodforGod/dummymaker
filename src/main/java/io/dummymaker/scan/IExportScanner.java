package io.dummymaker.scan;

import io.dummymaker.export.ICase;
import io.dummymaker.model.export.FieldContainer;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Scanner for special export annotations
 *
 * @author GoodforGod
 * @since 27.04.2018
 */
public interface IExportScanner extends IScanner<FieldContainer, Class<?>> {

    @NotNull
    Collection<FieldContainer> scan(Class<?> target);
}
