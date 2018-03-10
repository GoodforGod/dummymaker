package io.dummymaker.factory;

import io.dummymaker.data.DummyCollection;
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
                               IGenerator generator,
                               Field field,
                               Annotation annotation) {
        this.generateFactory = generateFactory;
        this.generator = generator;
        this.field = field;
        this.annotation = annotation;
    }

    @Parameters(name = "{index}: Factory - ({0})")
    public static Collection<Object[]> data() {
        final DummyCollection dummyCollection = new DummyCollection();
        Field listField = dummyCollection.getClass().getDeclaredFields()[0];
        Annotation listAnnotation = listField.getDeclaredAnnotations()[0];

        Field setField = dummyCollection.getClass().getDeclaredFields()[1];
        Annotation setAnnotation = setField.getDeclaredAnnotations()[0];

        Field mapField = dummyCollection.getClass().getDeclaredFields()[2];
        Annotation mapAnnotation = mapField.getDeclaredAnnotations()[0];

        final DummyTime dummyTime = new DummyTime();
        Field timeField = dummyTime.getClass().getDeclaredFields()[0];
        Annotation timeAnnotation = timeField.getDeclaredAnnotations()[0];

        return Arrays.asList(new Object[][]{
                {new ListGenerateFactory(), new ListGenerator(), listField, listAnnotation},
                {new SetGenerateFactory(), new SetGenerator(), setField, setAnnotation},
                {new MapGenerateFactory(), new MapGenerator(), mapField, mapAnnotation},
                {new TimeGenerateFactory(), new LocalDateGenerator(), timeField, timeAnnotation}
        });
    }

    @Test
    public void nullableGeneratorTest() {
        Object object = generateFactory.generate(field, annotation, null);
        assertNotNull(object);
    }

    @Test
    public void nullableFieldTest() {
        Object object = generateFactory.generate(null, annotation, generator);
        assertNull(object);
    }

    @Test
    public void noGeneratorExecution() {
        Object object = generateFactory.generate(field, annotation);
        assertNotNull(object);
    }

    @Test
    public void nullableAnnotationTest() {
        Object object = generateFactory.generate(field, null, generator);
        assertNull(object);
    }
}
