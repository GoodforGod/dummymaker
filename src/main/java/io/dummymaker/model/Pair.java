package io.dummymaker.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * Class for storing pairs
 *
 * @author Anton Kurako (GoodforGod)
 * @since 5.5.2020
 */
public class Pair<K, V> {

    private static final Pair<?, ?> EMPTY = new Pair<>(null, null);

    private final K left;
    private final V right;

    private Pair(K left, V right) {
        this.left = left;
        this.right = right;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Pair<K, V> empty() {
        return (Pair<K, V>) EMPTY;
    }

    public static <K, V> Pair<K, V> of(K left, V right) {
        return new Pair<>(left, right);
    }

    public boolean anyEmpty() {
        return left == null || right == null;
    }

    public boolean allEmpty() {
        return left == null && right == null;
    }

    public @Nullable K left() {
        return left;
    }

    public @Nullable V right() {
        return right;
    }

    public @NotNull Optional<K> first() {
        return Optional.ofNullable(left);
    }

    public @NotNull Optional<V> second() {
        return Optional.ofNullable(right);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(left, pair.left) &&
                Objects.equals(right, pair.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
