package io.dummymaker.generator;

import io.dummymaker.generator.impl.collection.impl.basic.SetObjectGenerator;
import io.dummymaker.generator.impl.collection.impl.basic.SetStringGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 24.02.2018
 */
@RunWith(Parameterized.class)
public class GeneratorCollectionSetValidTest extends Assert {

    private IGenerator<Set<?>> generator;

    private Class collectionValueClass;

    public GeneratorCollectionSetValidTest(IGenerator<Set<?>> generator, Class collectionValueClass) {
        this.generator = generator;
        this.collectionValueClass = collectionValueClass;
    }

    @Parameters(name = "{index}: Generator ({0}), Regex {2}")
    public static Collection<Object> data() {
        return Arrays.asList(new Object[][] {
                { new SetObjectGenerator(), String.class },
                { new SetStringGenerator(), String.class }
        });
    }

    @Test
    public void checkNotEmpty() {
        Set list = generator.generate();
        assertFalse(list.isEmpty());
    }

    @Test
    public void checkValueClassEquals() {
        Set set = generator.generate();
        assertTrue(collectionValueClass.equals(set.iterator().next().getClass()));
    }
}
