package io.dummymaker.generator;

import io.dummymaker.generator.impl.collection.impl.basic.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 24.02.2018
 */
@RunWith(Parameterized.class)
public class GeneratorCollectionMapValidTest extends Assert {

    private IGenerator<Map<?, ?>> generator;

    private Class keyClass;
    private Class valueClass;

    public GeneratorCollectionMapValidTest(IGenerator<Map<?, ?>> generator, Class keyClass, Class valueClass) {
        this.generator = generator;
        this.keyClass = keyClass;
        this.valueClass = valueClass;
    }

    @Parameters(name = "{index}: Generator ({0}), Regex {2}")
    public static Collection<Object> data() {
        return Arrays.asList(new Object[][] {
                { new MapIntIntGenerator(),         Integer.class,  Integer.class },
                { new MapIntObjectGenerator(),      Integer.class,  String.class },
                { new MapIntStringGenerator(),      Integer.class,  String.class },
                { new MapLongLongGenerator(),       Long.class,     Long.class },
                { new MapLongObjectGenerator(),     Long.class,     String.class },
                { new MapLongStringGenerator(),     Long.class,     String.class },
                { new MapObjectObjectGenerator(),   String.class,  String.class },
                { new MapStringObjectGenerator(),   String.class,  String.class },
                { new MapStringStringGenerator(),   String.class,  String.class }
        });
    }

    @Test
    public void checkNotEmpty() {
        Map map = generator.generate();
        assertFalse(map.isEmpty());
    }

    @Test
    public void checkValueClassEquals() {
        Map map = generator.generate();
        assertTrue(keyClass.equals(map.keySet().iterator().next().getClass()));
        assertTrue(valueClass.equals(map.values().iterator().next().getClass()));
    }
}
