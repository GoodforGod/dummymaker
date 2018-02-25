package io.dummymaker.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 25.02.2018
 */
public class BasicStreamUtils {

    public static <T> Collector<T, List<List<T>>, List<List<T>>> subSplitCollector(int amount) {
        return new GroupingCollector<>(amount);
    }

    private static class GroupingCollector<T> implements Collector<T, List<List<T>>, List<List<T>>> {

        private final int elementCountInGroup;

        public GroupingCollector(final int elementCountInGroup) {
            this.elementCountInGroup = elementCountInGroup;
        }

        @Override
        public Supplier<List<List<T>>> supplier() {
            return ArrayList::new;
        }

        @Override
        public BiConsumer<List<List<T>>, T> accumulator() {
            return (lists, t) -> {
                if (!lists.isEmpty()) {
                    final List<T> ts = lists.get(lists.size() - 1);
                    if (ts.size() < elementCountInGroup) {
                        ts.add(t);
                        return;
                    }
                }

                final List<T> list = new ArrayList<>();
                list.add(t);
                lists.add(list);
            };
        }

        @Override
        public BinaryOperator<List<List<T>>> combiner() {
            return (l, l2) -> {
                final List<List<T>> list = new ArrayList<>(l);
                list.addAll(l2);
                return list;
            };
        }

        @Override
        public Function<List<List<T>>, List<List<T>>> finisher() {
            return lists -> lists;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.emptySet();
        }
    }
}
