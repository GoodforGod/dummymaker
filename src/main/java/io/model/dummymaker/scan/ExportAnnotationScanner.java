package io.model.dummymaker.scan;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 03.06.2017
 */
public class ExportAnnotationScanner extends AnnotationScanner {

    @Override
    public Map<Field, Set<Annotation>> scan(Class t) {
        return super.scan(t);
    }
}
