package io.dummymaker.scan;

import io.dummymaker.scan.impl.AnnotationScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Field scanner used to scan for field annotations
 * Mostly used by factories to retrieve info for them
 *
 * @author GoodforGod
 * @see AnnotationScanner
 * @see io.dummymaker.scan.impl.UniqueScanner
 * @see io.dummymaker.factory.IPopulateFactory
 * @since 30.05.2017
 */
public interface IAnnotationScanner extends IScanner<Field, List<Annotation>> {

    /**
     * Scan class for field annotations
     *
     * @param target class to scan
     * @return Returns Map with field and annotations associated with that field
     */
    @Override
    Map<Field, List<Annotation>> scan(Class target);
}
