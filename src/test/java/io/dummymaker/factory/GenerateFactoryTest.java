package io.dummymaker.factory;

import io.dummymaker.data.DummyCollection;
import io.dummymaker.data.DummyTime;
import io.dummymaker.generator.complex.IComplexGenerator;
import io.dummymaker.generator.complex.impl.ListComplexGenerator;
import io.dummymaker.generator.complex.impl.MapComplexGenerator;
import io.dummymaker.generator.complex.impl.SetComplexGenerator;
import io.dummymaker.generator.complex.impl.TimeComplexGenerator;
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

    private IComplexGenerator complexGenerator;

    private Field field;
    private Annotation annotation;

    public GenerateFactoryTest(final IComplexGenerator complexGenerator,
                               final Field field,
                               final Annotation annotation) {
        this.complexGenerator = complexGenerator;
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
                {new ListComplexGenerator(),    listField,  listAnnotation},
                {new SetComplexGenerator(),     setField,   setAnnotation},
                {new MapComplexGenerator(),     mapField,   mapAnnotation},
                {new TimeComplexGenerator(),    timeField,  timeAnnotation}
        });
    }

    @Test
    public void nullableGeneratorTest() {
        Object object = complexGenerator.generate(annotation, field);
        assertNotNull(object);
    }

    @Test
    public void nullableFieldTest() {
        Object object = complexGenerator.generate(annotation, null);
        assertNull(object);
    }

    @Test
    public void noGeneratorExecution() {
        Object object = complexGenerator.generate(null, field);
        assertNull(object);
    }
}
