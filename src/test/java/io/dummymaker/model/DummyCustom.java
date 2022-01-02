package io.dummymaker.model;

import io.dummymaker.annotation.special.GenCustom;
import io.dummymaker.factory.IGenStorage;
import io.dummymaker.generator.IComplexGenerator;
import io.dummymaker.generator.simple.BooleanGenerator;
import io.dummymaker.generator.simple.number.FloatGenerator;
import io.dummymaker.generator.simple.number.ShortGenerator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.jetbrains.annotations.NotNull;

/**
 * Custom annotation dummy tester
 *
 * @author GoodforGod
 * @since 12.10.2019
 */
public class DummyCustom {

    @GenCustom(ShortGenerator.class)
    private String nnn;

    @GenCustom(BooleanGenerator.class)
    private String type;

    @GenCustom(QueueGenerator.class)
    private BlockingQueue<String> queue;

    public String getNnn() {
        return nnn;
    }

    public String getType() {
        return type;
    }

    public BlockingQueue<String> getQueue() {
        return queue;
    }

    public class QueueGenerator implements IComplexGenerator {

        private final FloatGenerator generator = new FloatGenerator();

        @Override
        public Object generate(@NotNull Class<?> parent,
                               @NotNull Field field,
                               @NotNull IGenStorage storage,
                               Annotation annotation,
                               int depth) {
            return generate();
        }

        @Override
        public Object generate() {
            final BlockingQueue<String> queue = new ArrayBlockingQueue<>(4);
            queue.add(String.valueOf(generator.generate()));
            queue.add(String.valueOf(generator.generate()));
            queue.add(String.valueOf(generator.generate()));
            queue.add(String.valueOf(generator.generate()));
            return queue;
        }
    }
}
