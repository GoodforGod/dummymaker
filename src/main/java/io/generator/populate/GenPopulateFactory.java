package io.generator.populate;

import io.generator.annotation.prime.PrimeGenAnnotation;
import io.generator.scan.GenAnnotationScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 30.05.2017
 */
public class GenPopulateFactory<T> implements IPopulateFactory<T> {

    private final GenAnnotationScanner genScanner = new GenAnnotationScanner();

    @Override
    public T populate(T t) {
        Map<Field, Set<Annotation>> classAnnotatedFields = genScanner.scan(t.getClass());

        for(Map.Entry<Field, Set<Annotation>> annotated : classAnnotatedFields.entrySet()) {
            try {
                annotated.getKey().setAccessible(true);
                annotated.getKey().set(t, ((PrimeGenAnnotation) annotated.getValue().iterator().next()).value().newInstance().generate());
                annotated.getKey().setAccessible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return t;
    }

    @Override
    public List<T> populate(List<T> t) {
        return t.stream().map(this::populate).collect(Collectors.toList());
    }
}
