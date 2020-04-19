package io.dummymaker.generator.simple.string;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates dummy JSON object as String
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class JsonGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("jsonb?", CASE_INSENSITIVE);

    private final IGenerator<String> idGenerator = new IdGenerator();
    private final IGenerator<String> nickGenerator = new NickGenerator();
    private final IGenerator<String> fieldGenerator = new NounGenerator();

    @Override
    public String generate() {
        final StringBuilder builder = new StringBuilder();

        final int depth = CollectionUtils.random(1, 6);
        for (int i = 0; i < depth; i++) {
            final boolean lastDepth = (i == (depth - 1));
            builder.append("{");

            final int lines = CollectionUtils.random(1, 5);
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

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
