package io.dummymaker.scan.old;

import io.dummymaker.scan.old.impl.MainAnnotationScanner;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/**
 * Field scanner used to scan for field annotations Mostly used by factories to retrieve info for
 * them
 *
 * @author Anton Kurako (GoodforGod)
 * @see MainAnnotationScanner
 * @see io.dummymaker.scan.old.impl.UniqueScanner
 * @since 30.05.2017
 */
@Deprecated
public interface AnnotationScanner extends MapScanner<Field, List<Annotation>, Class<?>> {

    /**
     * Scan class for field annotations
     *
     * @param target class to scan
     * @return Returns Map with field and annotations associated with that field
     */
    @Override
    @NotNull
    Map<Field, List<Annotation>> scan(Class<?> target);
}
