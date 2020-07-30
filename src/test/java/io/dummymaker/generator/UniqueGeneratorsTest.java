package io.dummymaker.generator;

import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import io.dummymaker.generator.simple.NullGenerator;
import io.dummymaker.generator.simple.SequenceGenerator;
import io.dummymaker.generator.simple.time.LocalDateTimeGenerator;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
public class UniqueGeneratorsTest {

    @Test
    public void enumerateGen() {
        IGenerator generator = new SequenceGenerator();

        final Person build = new GenFactory().build(Person::new);

        Object generated = generator.generate();

        assertNotNull(generated);
    }

    @Test
    public void localDateTimeGen() {
        IGenerator generator = new LocalDateTimeGenerator();

        Object generated = generator.generate();

        assertNotNull(generated);
        assertTrue(generated.getClass().equals(LocalDateTime.class));
    }

    @Test
    public void embeddedGen() {
        IGenerator generator = new EmbeddedGenerator();

        Object generated = generator.generate();

        assertNull(generated);
    }

    @Test
    public void nullGen() {
        IGenerator generator = new NullGenerator();

        Object generated = generator.generate();

        assertNull(generated);
    }
}
