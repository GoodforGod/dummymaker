package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.RandomUtils;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates dummy JSON object as String
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
public final class JsonGenerator implements Generator<String> {

    private static final Pattern PATTERN = Pattern.compile("jsonb?", CASE_INSENSITIVE);

    private final Generator<String> idGenerator = new IdGenerator();
    private final Generator<String> nickGenerator = new LoginGenerator();
    private final Generator<String> fieldGenerator = new NounGenerator();

    @Override
    public @NotNull String get() {
        final StringBuilder builder = new StringBuilder();

        final int depth = RandomUtils.random(1, 6);
        for (int i = 0; i < depth; i++) {
            final boolean lastDepth = (i == (depth - 1));
            builder.append("{");

            final int lines = RandomUtils.random(1, 5);
            final Set<String> usedFieldNames = new HashSet<>();
            for (int j = 0; j < lines; j++) {
                final String fieldName = generateFieldName(usedFieldNames);
                final String fieldValue = nickGenerator.get()
                        + "-"
                        + idGenerator.get();

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
            fieldName = fieldGenerator.get();
            if (!used.contains(fieldName)) {
                used.add(fieldName);
                break;
            }
        }

        return fieldName;
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
