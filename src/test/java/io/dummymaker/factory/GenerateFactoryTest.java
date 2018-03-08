package io.dummymaker.factory;

import io.dummymaker.data.DummyTime;
import io.dummymaker.factory.impl.ListGenerateFactory;
import io.dummymaker.factory.impl.MapGenerateFactory;
import io.dummymaker.factory.impl.SetGenerateFactory;
import io.dummymaker.factory.impl.TimeGenerateFactory;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.collection.impl.ListGenerator;
import io.dummymaker.generator.impl.collection.impl.MapGenerator;
import io.dummymaker.generator.impl.collection.impl.SetGenerator;
import io.dummymaker.generator.impl.time.impl.LocalDateGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 08.03.2018
 */
@RunWith(Parameterized.class)
public class GenerateFactoryTest extends Assert {

    private IGenerateFactory generateFactory;

    private IGenerator generator;

    private Field field;
    private Annotation annotation;

    public GenerateFactoryTest(IGenerateFactory generateFactory,
                               IGenerator generator) {
        this.generateFactory = generateFactory;
        this.generator = generator;

        DummyTime dummyTime = new DummyTime();
        this.field = dummyTime.getClass().getDeclaredFields()[0];
        this.annotation = this.field.getDeclaredAnnotations()[0];
    }

    @Parameters(name = "{index}: Factory - ({0})")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new ListGenerateFactory(), new ListGenerator()},
                {new SetGenerateFactory(), new SetGenerator()},
                {new MapGenerateFactory(), new MapGenerator()},
                {new TimeGenerateFactory(), new LocalDateGenerator()}
        });
    }

    @Test
    public void nullableGeneratorTest() {
        Object object = generateFactory.generate(field, annotation, null);
        if (generateFactory.getClass().equals(TimeGenerateFactory.class)) {
            assertNotNull(object);
        } else {
            assertNull(object);
        }
    }

    @Test
    public void nullableFieldTest() {
        Object object = generateFactory.generate(null, annotation, generator);
        assertNull(object);
    }

    @Test
    public void noGeneratorExecution() {
        Object object = generateFactory.generate(field, annotation);
        if (generateFactory.getClass().equals(TimeGenerateFactory.class)) {
            assertNotNull(object);
        } else {
            assertNull(object);
        }
    }

    @Test
    public void nullableAnnotationTest() {
        Object object = generateFactory.generate(field, null, generator);
        assertNull(object);
    }
}
