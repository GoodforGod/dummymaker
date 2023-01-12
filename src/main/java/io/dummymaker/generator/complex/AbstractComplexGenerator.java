package io.dummymaker.generator.complex;

import static io.dummymaker.util.CastUtils.generateObject;
import static io.dummymaker.util.CastUtils.instantiate;

import io.dummymaker.factory.old.GenStorage;
import io.dummymaker.factory.old.GenSupplier;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import io.dummymaker.generator.simple.NullGenerator;
import io.dummymaker.generator.simple.string.IdGenerator;
import io.dummymaker.util.CastUtils;
import io.dummymaker.util.RandomUtils;
import java.lang.reflect.Field;

/**
 * Basic complex generator implementation Can be used by other Complex generators Providing basic
 * methods, to build new generators on top of this one
 *
 * @author Anton Kurako (GoodforGod)
 * @see ComplexGenerator
 * @since 22.04.2018
 */
abstract class AbstractComplexGenerator implements ComplexGenerator {

    static final int MIN_DEFAULT = 1;
    static final int MAX_DEFAULT = 2;

    static int getDesiredSize(int min, int max, int fixed) {
        return (fixed > -1)
                ? fixed
                : RandomUtils.random(min, max);
    }

    boolean isGenDefault(Class<? extends Generator> generatorClass) {
        return Generator.class.equals(generatorClass);
    }

    <T> T generateValue(final Class<? extends Generator> generatorClass,
                        final Class<T> valueClass,
                        final GenStorage storage,
                        final int depth,
                        final int depthLimit) {
        final int parsedDepthLimit = EmbeddedGenerator.toDepth(depthLimit);
        if ((EmbeddedGenerator.class.equals(generatorClass) || NullGenerator.class.equals(generatorClass))
                && depth <= parsedDepthLimit
                && storage != null) {
            return storage.fillByDepth(instantiate(valueClass), depth + 1);
        }

        final Generator<?> generator = (storage == null)
                ? CastUtils.instantiate(generatorClass)
                : storage.getGenerator(generatorClass);

        return generateObject(generator, valueClass);
    }

    protected Class<? extends Generator> suitable(GenSupplier config, Field field, Class<?> fieldType) {
        return (config == null)
                ? IdGenerator.class
                : config.getSuitable(field, fieldType);
    }
}
