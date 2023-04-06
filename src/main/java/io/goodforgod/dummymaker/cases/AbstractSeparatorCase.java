package io.goodforgod.dummymaker.cases;

import org.jetbrains.annotations.NotNull;

abstract class AbstractSeparatorCase implements NamingCase {

    @Override
    public @NotNull CharSequence apply(@NotNull CharSequence value) {
        final StringBuilder builder = new StringBuilder();

        char prevLetter = 0;
        char[] letters = value.toString().toCharArray();
        for (int i = 0; i < letters.length; i++) {
            final char letter = letters[i];
            if (!isSeparator(letter)) {
                if (isSeparator(prevLetter)) {
                    builder.append(mapAfterSeparator(letter));
                } else if (i == 0) {
                    builder.append(mapFirstLetter(letter));
                } else {
                    builder.append(mapDefault(prevLetter, letter));
                }
            }

            prevLetter = letter;
        }

        return builder.toString();
    }

    protected abstract String mapFirstLetter(char letter);

    protected abstract String mapAfterSeparator(char letter);

    protected abstract String mapDefault(char previousLetter, char letter);

    protected boolean isSeparator(char letter) {
        return Character.isSpaceChar(letter)
                || letter == '_'
                || letter == '-'
                || letter == '.'
                || letter == ','
                || letter == ':'
                || letter == ';'
                || letter == '\\'
                || letter == '/'
                || letter == '|'
                || letter == '\''
                || letter == '"'
                || letter == '`'
                || letter == '~'
                || letter == '{'
                || letter == '}'
                || letter == '['
                || letter == ']';
    }
}
