package io.dummymaker.factory.impl;

import io.dummymaker.factory.IGenerateFactory;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.BasicCollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Logger;

/**
 * Basic core implementation of generate factory with suitable find methods implementation
 * Used by full factory implementations
 *
 * @see IGenerateFactory
 *
 * @author GoodforGod
 * @since 08.03.2018
 */
public abstract class BasicGenerateFactory<T extends IGenerator> implements IGenerateFactory<T> {

    protected static final Logger logger = Logger.getLogger(BasicGenerateFactory.class.getName());

    protected final Class<? extends Annotation> suitableAnnotation;

    public BasicGenerateFactory(final Class<? extends Annotation> suitableAnnotation) {
        if(suitableAnnotation == null)
            throw new NullPointerException("Annotation can not be nullable");

        this.suitableAnnotation = suitableAnnotation;
    }

    @Override
    public boolean isSuitable(final Annotation annotation) {
        return annotation != null && annotation.getClass().equals(suitableAnnotation);
    }

    @Override
    public Annotation findSuitable(final List<Annotation> annotations) {
        return (BasicCollectionUtils.isEmpty(annotations))
                ? null
                : annotations.stream()
                        .filter(a -> a.annotationType().equals(suitableAnnotation))
                        .findFirst().orElse(null);
    }

    @Override
    public abstract Object generate(final Field field,
                                    final Annotation annotation);

    @Override
    public abstract Object generate(final Field field,
                                    final Annotation annotation,
                                    final T generator);
}
