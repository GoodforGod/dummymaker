package io.dummymaker.scan;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Field scanner contract
 *
 * @author GoodforGod
 * @since 30.05.2017
 */
public interface IFieldScanner extends IScanner<Field, List<Annotation>> {

    /**
     * Scan class for field annotations
     *
     * @param t class to scan
     * @return Returns Map with field and annotations associated with that field
     */
    @Override
    Map<Field, List<Annotation>> scan(final Class t);
}
