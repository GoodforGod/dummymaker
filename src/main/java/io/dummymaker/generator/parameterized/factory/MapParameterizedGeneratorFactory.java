package io.dummymaker.generator.parameterized.factory;

import io.dummymaker.annotation.complex.GenMap;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.ParameterizedGenerator;
import io.dummymaker.generator.ParameterizedGeneratorFactory;
import io.dummymaker.generator.parameterized.MapParameterizedGenerator;
import io.dummymaker.util.CastUtils;

/**
 * @see GenMap
 * @see MapParameterizedGenerator
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
public final class MapParameterizedGeneratorFactory implements ParameterizedGeneratorFactory<GenMap> {

    @Override
    public ParameterizedGenerator<?> get(GenMap annotation) {
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
