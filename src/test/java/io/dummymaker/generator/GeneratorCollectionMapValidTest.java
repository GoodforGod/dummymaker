package io.dummymaker.generator;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 24.02.2018
 */
@RunWith(Parameterized.class)
public class GeneratorCollectionMapValidTest extends Assert {

    private IGenerator generator;

    private Class keyClass;
    private Class valueClass;

    public GeneratorCollectionMapValidTest(IGenerator generator, Class keyClass, Class valueClass) {
        this.generator = generator;
        this.keyClass = keyClass;
        this.valueClass = valueClass;
    }

    @Parameters(name = "{index}: Generator ({0}), Regex {2}")
    public static Collection<Object> data() {
        return Arrays.asList(new Object[][] {

        });
    }
}
