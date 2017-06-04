package io.dummymaker.scan;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 30.05.2017
 */
public interface IFieldScanner {

    /**
     * Scan class for field annotations
     *
     * @param t class to scan
     * @return Returns Map with field and annotations associated with that field
     */
    Map<Field, Set<Annotation>> scan(Class t);
}
