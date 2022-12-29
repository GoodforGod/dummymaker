package io.dummymaker.factory.refactored;

import org.jetbrains.annotations.NotNull;

/**
 * Please Add Description Here.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 17.11.2022
 */
final class SimpleGenContext implements GenContext {

    private final int depthMax;
    private final int depth;
    private final GenNode graph;

    private SimpleGenContext(int depthMax, int depth, GenNode graph) {
        this.depthMax = depthMax;
        this.depth = depth;
        this.graph = graph;
    }

    static GenContext ofChild(GenContext context, Class<?> target) {
        final GenNode node = context.graph().nodes().stream()
                .filter(n -> n.value().getType().value().equals(target))
                .findFirst()
                .orElse(null);

        return new SimpleGenContext(context.depthMax(), context.depth() + 1, node);
    }

    static GenContext ofNew(int depthMax, GenNode root) {
        return new SimpleGenContext(depthMax, 1, root);
    }

    @Override
    public int depthMax() {
        return depthMax;
    }

    @Override
    public int depth() {
        return depth;
    }

    @Override
    public @NotNull GenNode graph() {
        return graph;
    }
}
