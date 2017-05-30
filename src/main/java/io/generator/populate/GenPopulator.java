package io.generator.populate;

import io.generator.annotations.PrimeGenAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 30.05.2017
 */
public class GenPopulator<T> implements IPopulator<T> {

    @Override
    public T populate(T t) {
        for(Field field : t.getClass().getDeclaredFields()) {
            for(Annotation annotation : field.getAnnotations()) {
                for(Annotation primeAnnotation : annotation.annotationType().getDeclaredAnnotations()) {
                    if(primeAnnotation.annotationType().equals(PrimeGenAnnotation.class)) {
                        try {
                            field.setAccessible(true);
                            field.set(t, ((PrimeGenAnnotation) primeAnnotation).value().newInstance().generate());
                            field.setAccessible(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return t;
    }
}
