package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Generator;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.LocalizedGenerator;
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
public final class JsonGenerator implements LocalizedGenerator<String> {

    private static final Pattern PATTERN = Pattern.compile("jsonb?", CASE_INSENSITIVE);

    private static final Generator<String> ID_GENERATOR = new IdGenerator();
    private static final LocalizedGenerator<String> NICK_GENERATOR = new LoginGenerator();
    private static final LocalizedGenerator<String> FIELD_GENERATOR = new NounGenerator();

    @Override
    public @NotNull String get(@NotNull Localisation localisation) {
        final StringBuilder builder = new StringBuilder();

        final int depth = RandomUtils.random(1, 6);
        for (int i = 0; i < depth; i++) {
            final boolean lastDepth = (i == (depth - 1));
            builder.append("{");

            final int lines = RandomUtils.random(1, 5);
            final Set<String> usedFieldNames = new HashSet<>();
            for (int j = 0; j < lines; j++) {
                final String fieldName = generateFieldName(usedFieldNames, localisation);
                final String fieldValue = NICK_GENERATOR.get(localisation)
                        + "-"
                        + ID_GENERATOR.get();

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
                    final String objectName = generateFieldName(usedFieldNames, localisation);
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

    private String generateFieldName(Set<String> used, Localisation localisation) {
        String fieldName;
        while (true) {
            fieldName = FIELD_GENERATOR.get(localisation);
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
