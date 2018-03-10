package io.dummymaker.generator;

import io.dummymaker.generator.impl.collection.impl.basic.ListObjectGenerator;
import io.dummymaker.generator.impl.collection.impl.basic.ListStringGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 24.02.2018
 */
@RunWith(Parameterized.class)
public class GeneratorCollectionListValidTest extends Assert {

    private IGenerator<List<?>> generator;

    private Class collectionValueClass;

    public GeneratorCollectionListValidTest(IGenerator<List<?>> generator, Class collectionValueClass) {
        this.generator = generator;
        this.collectionValueClass = collectionValueClass;
    }

    @Parameters(name = "{index}: Generator ({0}), Regex {2}")
    public static Collection<Object> data() {
        return Arrays.asList(new Object[][] {
                { new ListObjectGenerator(), String.class },
                { new ListStringGenerator(), String.class }
        });
    }

    @Test
    public void checkNotEmpty() {
        List list = generator.generate();
        assertFalse(list.isEmpty());
    }

    @Test
    public void checkValueClassEquals() {
        List list = generator.generate();
        assertTrue(collectionValueClass.equals(list.get(0).getClass()));
    }
}
