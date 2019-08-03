package io.dummymaker.scan;

import io.dummymaker.container.FieldContainer;
import io.dummymaker.export.naming.ICase;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Scanner for special export annotations
 *
 * @author GoodforGod
 * @since 27.04.2018
 */
public interface IExportScanner extends IMapScanner<Field, FieldContainer, Class> {

    @Override
    Map<Field, FieldContainer> scan(Class target);

    Map<Field, FieldContainer> scan(Class target, ICase nameCase);
}
