package io.dummymaker.scan;

import io.dummymaker.container.impl.FieldContainer;
import io.dummymaker.export.naming.ICase;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Scanner for special export annotations
 *
 * @author GoodforGod
 * @since 27.04.2018
 */
public interface IExportScanner extends IScanner<Field, FieldContainer> {

    Map<Field, FieldContainer> scan(final Class t,
                                    final ICase nameCase);
}
