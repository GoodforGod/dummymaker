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
    private final int depthCurrent;
    private final GenNode graph;

    private SimpleGenContext(int depthMax, int depthCurrent, GenNode graph) {
        this.depthMax = depthMax;
        this.depthCurrent = depthCurrent;
        this.graph = graph;
    }

    static GenContext ofChild(GenContext context, Class<?> target) {
        final GenNode node = context.graph().nodes().stream()
                .filter(n -> n.value().type().raw().equals(target))
                .findFirst()
                .orElseGet(() -> context.graph().nodes().stream()
                        .filter(n -> n.value().type().flatten().stream().anyMatch(type -> type.plain().equals(target)))
                        .findFirst()
                        .orElse(null));

        return new SimpleGenContext(context.depthMax(), context.depthCurrent() + 1, node);
    }

    static GenContext ofParameterized(GenContext context, GenType target) {
        final GenNode node = context.graph().nodes().stream()
                .filter(n -> n.value().type().equals(target))
                .findFirst()
                .orElse(null);

        return new SimpleGenContext(context.depthMax(), context.depthCurrent(), node);
    }

    static GenContext ofNew(int depthMax, GenNode root) {
        return new SimpleGenContext(depthMax, 1, root);
    }

    @Override
    public int depthMax() {
        return depthMax;
    }

    @Override
    public int depthCurrent() {
        return depthCurrent;
    }

    @Override
    public @NotNull GenNode graph() {
        return graph;
    }
}
