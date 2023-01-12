package io.dummymaker.scan;

import io.dummymaker.model.export.FieldContainer;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Scanner for special export annotations
 *
 * @author Anton Kurako (GoodforGod)
 * @since 27.04.2018
 */
public interface ExportScanner extends ListScanner<FieldContainer, Class<?>> {

    @NotNull
    List<FieldContainer> scan(Class<?> target);
}
