package io.dummymaker.scan;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * Field scanner contract
 *
 * @author GoodforGod
 * @since 30.05.2017
 */
public interface IFieldScanner extends IScanner<Field, Set<Annotation>> {

    /**
     * Scan class for field annotations
     *
     * @param t class to scan
     * @return Returns Map with field and annotations associated with that field
     */
    @Override
    Map<Field, Set<Annotation>> scan(final Class t);
}
