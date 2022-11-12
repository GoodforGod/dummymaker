package io.dummymaker.generator;

import static org.junit.Assert.*;

import io.dummymaker.generator.simple.EmbeddedGenerator;
import io.dummymaker.generator.simple.NullGenerator;
import io.dummymaker.generator.simple.SequenceGenerator;
import io.dummymaker.generator.simple.time.LocalDateTimeGenerator;
import java.time.LocalDateTime;
import org.junit.Test;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
public class UniqueGeneratorsTest {

    @Test
    public void enumerateGen() {
        Generator generator = new SequenceGenerator();

        Object generated = generator.get();

        assertNotNull(generated);
    }

    @Test
    public void localDateTimeGen() {
        Generator generator = new LocalDateTimeGenerator();

        Object generated = generator.get();

        assertNotNull(generated);
        assertTrue(generated.getClass().equals(LocalDateTime.class));
    }

    @Test
    public void embeddedGen() {
        Generator generator = new EmbeddedGenerator();

        Object generated = generator.get();

        assertNull(generated);
    }

    @Test
    public void nullGen() {
        Generator generator = new NullGenerator();

        Object generated = generator.get();

        assertNull(generated);
    }
}
