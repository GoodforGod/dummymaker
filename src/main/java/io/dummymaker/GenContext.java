package io.dummymaker;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 17.11.2022
 */
final class GenContext {

    private final int depthMax;
    private final int depthCurrent;
    private final GenNode graph;
    private final GenRulesContext rules;
    private final GeneratorSupplier generatorSupplier;
    private final GenGraphBuilder graphBuilder;

    private GenContext(int depthMax,
                       int depthCurrent,
                       GenNode graph,
                       GenRulesContext rules,
                       GeneratorSupplier generatorSupplier,
                       GenGraphBuilder graphBuilder) {
        this.depthMax = depthMax;
        this.depthCurrent = depthCurrent;
        this.graph = graph;
        this.rules = rules;
        this.generatorSupplier = generatorSupplier;
        this.graphBuilder = graphBuilder;
    }

    static GenContext ofChild(GenContext context, Class<?> target) {
        final GenNode node = context.graph().nodes().stream()
                .filter(n -> n.value().type().raw().equals(target))
                .findFirst()
                .orElseGet(() -> context.graph().nodes().stream()
                        .filter(n -> n.value().type().flatten().stream().anyMatch(type -> {
                            Class<?> plain = type.raw();
                            if (plain.getTypeName().endsWith("[][]")) {
                                plain = plain.getComponentType().getComponentType();
                            } else if (plain.getTypeName().endsWith("[]")) {
                                plain = plain.getComponentType();
                            }

                            return plain.equals(target);
                        }))
                        .findFirst()
                        .orElse(null));

        return new GenContext(context.depthMax(), context.depthCurrent() + 1, node, context.rules(), context.generatorSupplier(),
                context.graphBuilder());
    }

    static GenContext ofParameterized(GenContext context, GenType target) {
        final GenNode node = context.graph().nodes().stream()
                .filter(n -> n.value().type().equals(target))
                .findFirst()
                .orElse(null);

        return new GenContext(context.depthMax(), context.depthCurrent(), node, context.rules(), context.generatorSupplier(),
                context.graphBuilder());
    }

    static GenContext ofUnknown(GenNode root, GenContext context) {
        return new GenContext(context.depthMax(), context.depthCurrent(), root, context.rules(), context.generatorSupplier(),
                context.graphBuilder());
    }

    static GenContext ofNew(int depthMax,
                            GenNode root,
                            GenRulesContext rules,
                            GeneratorSupplier generatorSupplier,
                            GenGraphBuilder graphBuilder) {
        return new GenContext(depthMax, 1, root, rules, generatorSupplier, graphBuilder);
    }

    GeneratorSupplier generatorSupplier() {
        return generatorSupplier;
    }

    GenGraphBuilder graphBuilder() {
        return graphBuilder;
    }

    GenRulesContext rules() {
        return rules;
    }

    int depthMax() {
        return depthMax;
    }

    int depthCurrent() {
        return depthCurrent;
    }

    GenNode graph() {
        return graph;
    }

    @Override
    public String toString() {
        return (graph == null)
                ? "[depthMax=" + depthMax + ", depthCurrent=" + depthCurrent + ']'
                : "[depthMax=" + depthMax + ", depthCurrent=" + depthCurrent + ", graph=" + graph.value() + ']';
    }
}
