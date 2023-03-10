package io.dummymaker.generator.parameterized.factory;

import io.dummymaker.annotation.parameterized.GenMap;
import io.dummymaker.generator.AnnotationGeneratorFactory;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.ParameterizedGenerator;
import io.dummymaker.generator.parameterized.MapParameterizedGenerator;
import io.dummymaker.util.CastUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @see GenMap
 * @see MapParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public final class MapAnnotationGeneratorFactory implements AnnotationGeneratorFactory<GenMap> {

    @Override
    public @NotNull ParameterizedGenerator<?> get(GenMap annotation) {
        final Generator<?> keyGenerator = annotation.key().equals(Generator.class)
                ? null
                : CastUtils.instantiate(annotation.key());

        final Generator<?> valueGenerator = annotation.value().equals(Generator.class)
                ? null
                : CastUtils.instantiate(annotation.value());

        return new MapParameterizedGenerator(annotation.min(), annotation.max(), annotation.fixed(), keyGenerator,
                valueGenerator);
    }
}
