package io.goodforgod.dummymaker.testdata;

import io.goodforgod.dummymaker.generator.GenParameters;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import io.goodforgod.dummymaker.generator.simple.number.FloatGenerator;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
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

        private final FloatGenerator generator = new FloatGenerator(0, 1);

        @Override
        public Queue get(GenParameters parameters) {
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
