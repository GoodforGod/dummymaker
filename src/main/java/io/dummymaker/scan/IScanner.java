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
public interface IScanner {
    Map<Field, Set<Annotation>> scan(Class t);
}
