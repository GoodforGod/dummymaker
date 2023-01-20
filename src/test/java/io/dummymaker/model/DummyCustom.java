package io.dummymaker.model;

import io.dummymaker.factory.GenType;
import io.dummymaker.factory.GenTypeBuilder;
import io.dummymaker.generator.ParameterizedGenerator;
import io.dummymaker.generator.simple.number.FloatGenerator;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Custom annotation dummy tester
 *
 * @author GoodforGod
 * @since 12.10.2019
 */
public class DummyCustom {

    private String inn;

    private String type;

    private BlockingQueue<String> queue;

    public String getInn() {
        return inn;
    }

    public String getType() {
        return type;
    }

    public BlockingQueue<String> getQueue() {
        return queue;
    }

    public static class QueueGenerator implements ParameterizedGenerator<Queue> {

        private final FloatGenerator generator = new FloatGenerator();

        @Override
        public @Nullable Queue get(@NotNull GenType fieldType, @NotNull GenTypeBuilder typeBuilder) {
            return get();
        }

        @Override
        public Queue get() {
            final BlockingQueue<String> queue = new ArrayBlockingQueue<>(4);
            queue.add(String.valueOf(generator.get()));
            queue.add(String.valueOf(generator.get()));
            queue.add(String.valueOf(generator.get()));
            queue.add(String.valueOf(generator.get()));
            return queue;
        }
    }
}
