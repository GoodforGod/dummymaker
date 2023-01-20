package io.dummymaker.generator;

import static org.junit.jupiter.api.Assertions.*;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.generator.parameterized.SequenceParameterizedGenerator;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import io.dummymaker.generator.simple.NullGenerator;
import io.dummymaker.generator.simple.time.LocalDateTimeGenerator;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 20.08.2017
 */
class UniqueGeneratorsTests {

    @Test
    void enumerateGen() {
        Generator generator = new SequenceParameterizedGenerator(0);

        Object generated = generator.get();

        assertNotNull(generated);
    }

    @Test
    void localDateTimeGen() {
        Generator generator = new LocalDateTimeGenerator(0, GenTime.MAX);

        Object generated = generator.get();

        assertNotNull(generated);
        assertTrue(generated.getClass().equals(LocalDateTime.class));
    }

    @Test
    void embeddedGen() {
        Generator generator = new EmbeddedGenerator();

        Object generated = generator.get();

        assertNull(generated);
    }

    @Test
    void nullGen() {
        Generator generator = new NullGenerator();

        Object generated = generator.get();

        assertNull(generated);
    }
}
