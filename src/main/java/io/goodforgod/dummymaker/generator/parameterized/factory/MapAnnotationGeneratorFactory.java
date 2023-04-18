package io.goodforgod.dummymaker.generator.parameterized.factory;

import io.goodforgod.dummymaker.annotation.parameterized.GenMap;
import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import io.goodforgod.dummymaker.generator.parameterized.MapParameterizedGenerator;
import io.goodforgod.dummymaker.util.CastUtils;
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
