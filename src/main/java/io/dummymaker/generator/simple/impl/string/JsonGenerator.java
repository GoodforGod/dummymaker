package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.generator.simple.IGenerator;

import java.util.HashSet;
import java.util.Set;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Generates dummy JSON object as String
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class JsonGenerator implements IGenerator<String> {

    private final IGenerator<String> idGenerator = new IdGenerator();
    private final IGenerator<String> nickGenerator = new NickGenerator();
    private final IGenerator<String> fieldGenerator = new NounGenerator();

    @Override
    public String generate() {
        final StringBuilder builder = new StringBuilder();

        final int depth = current().nextInt(1, 6);
        for (int i = 0; i < depth; i++) {
            final boolean lastDepth = (i == (depth - 1));
            builder.append("{");

            final int lines = current().nextInt(1, 5);
            final Set<String> usedFieldNames = new HashSet<>();
            for (int j = 0; j < lines; j++) {
                final String fieldName = generateFieldName(usedFieldNames);
                final String fieldValue = nickGenerator.generate()
                        + "-"
                        + idGenerator.generate();

                builder.append("\"")
                        .append(fieldName)
                        .append("\"")
                        .append(":")
                        .append("\"")
                        .append(fieldValue)
                        .append("\"");

                final boolean lastLine = (j == (lines - 1));

                if (!lastLine)
                    builder.append(",");

                if (lastLine && !lastDepth) {
                    final String objectName = generateFieldName(usedFieldNames);
                    builder.append(",")
                            .append("\"")
                            .append(objectName)
                            .append("\"")
                            .append(":");
                }
            }
        }

        for (int i = 0; i < depth; i++)
            builder.append("}");

        return builder.toString();
    }

    private String generateFieldName(Set<String> used) {
        String fieldName;
        while (true) {
            fieldName = fieldGenerator.generate();
            if (!used.contains(fieldName)) {
                used.add(fieldName);
                break;
            }
        }

        return fieldName;
    }
}
