package io.generator.scan;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 30.05.2017
 */
public class AnnotationScanner implements IScanner {

    @Override
    public List<Annotation> scan(Class t) {
        return Arrays.asList(t.getAnnotations());
    }
}
