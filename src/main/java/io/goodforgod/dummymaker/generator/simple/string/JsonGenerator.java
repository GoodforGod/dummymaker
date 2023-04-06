package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.bundle.Bundle;
import io.goodforgod.dummymaker.bundle.LoginBundle;
import io.goodforgod.dummymaker.bundle.NounBundle;
import io.goodforgod.dummymaker.cases.NamingCase;
import io.goodforgod.dummymaker.cases.NamingCases;
import io.goodforgod.dummymaker.generator.GenParameters;
import io.goodforgod.dummymaker.generator.Localisation;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import io.goodforgod.dummymaker.util.RandomUtils;
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
public final class JsonGenerator implements ParameterizedGenerator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("jsonb?", CASE_INSENSITIVE);

    private static final Bundle FIELD_NAME_GENERATOR = new LoginBundle();
    private static final Bundle FIELD_VALUE_GENERATOR = new NounBundle();

    @Override
    public String get(@NotNull GenParameters parameters) {
        return get(parameters.localisation(), parameters.namingCase());
    }

    @Override
    public String get() {
        return get(Localisation.ENGLISH, NamingCases.DEFAULT);
    }

    private static String get(@NotNull Localisation localisation,
                              @NotNull NamingCase namingCase) {
        final StringBuilder builder = new StringBuilder();

        final int depth = RandomUtils.random(1, 6);
        for (int i = 0; i < depth; i++) {
            final boolean lastDepth = (i == (depth - 1));
            builder.append("{");

            final int lines = RandomUtils.random(1, 5);
            final Set<String> usedFieldNames = new HashSet<>();
            for (int j = 0; j < lines; j++) {
                final String fieldName = namingCase.apply(generateFieldName(localisation, usedFieldNames)).toString();
                final String fieldValue = namingCase.apply(FIELD_VALUE_GENERATOR.random(localisation)).toString();

                builder.append("\"")
                        .append(fieldName)
                        .append("\"")
                        .append(":")
                        .append("\"")
                        .append(fieldValue)
                        .append("\"");

                final boolean lastLine = (j == (lines - 1));

                if (!lastLine) {
                    builder.append(",");
                }

                if (lastLine && !lastDepth) {
                    final String objectName = namingCase.apply(generateFieldName(localisation, usedFieldNames)).toString();
                    builder.append(",")
                            .append("\"")
                            .append(objectName)
                            .append("\"")
                            .append(":");
                }
            }
        }

        for (int i = 0; i < depth; i++) {
            builder.append("}");
        }

        return builder.toString();
    }

    private static String generateFieldName(Localisation localisation, Set<String> used) {
        String fieldName;
        while (true) {
            fieldName = FIELD_NAME_GENERATOR.random(localisation);
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
