package io.dummymaker.generator.impl.string;

import io.dummymaker.generator.IGenerator;

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

    private final IGenerator<String> stringGenerator = new StringGenerator();
    private final IGenerator<String> nickGenerator = new NickGenerator();
    private final IGenerator<String> fieldGenerator = new NounGenerator();

    @Override
    public String generate() {
        final StringBuilder builder = new StringBuilder("{");

        final int depth = current().nextInt(1, 6);
        for (int i = 0; i < depth; i++) {
            if (i != 0) {
                builder.append(",");
            }

            builder.append("{");

            final int lines = current().nextInt(1, 5);
            final Set<String> usedFieldNames = new HashSet<>();
            for (int j = 0; j < lines; j++) {
                String fieldName;
                while (true) {
                    fieldName = fieldGenerator.generate();
                    if (!usedFieldNames.contains(fieldName)) {
                        usedFieldNames.add(fieldName);
                        break;
                    }
                }

                final String fieldValue = nickGenerator.generate()
                        + "-"
                        + stringGenerator.generate();

                builder.append("\"")
                        .append(fieldName)
                        .append("\"")
                        .append(":")
                        .append("\"")
                        .append(fieldValue)
                        .append("\"");

                if (j < lines - 1) {
                    builder.append(",");
                }
            }
        }

        for ( int i = -1; i < depth; i++) {
            builder.append("}");
        }

        return builder.toString();
    }
}
