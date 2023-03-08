package io.dummymaker.cases;

/**
 * Each upper letter separated with underscore symbol, and transform to low case EXCLUDE FIRST
 * LETTER, first letter to low case Example: ( DummyList - dummy_list )
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
public final class SnakeLowerCase extends AbstractSeparatorCase {

    SnakeLowerCase() {}

    @Override
    protected String mapFirstLetter(char letter) {
        return String.valueOf(Character.toLowerCase(letter));
    }

    @Override
    protected String mapAfterSeparator(char letter) {
        return "_" + Character.toLowerCase(letter);
    }

    @Override
    protected String mapDefault(char previousLetter, char letter) {
        if (Character.isUpperCase(letter)) {
            if (Character.isUpperCase(previousLetter)) {
                return String.valueOf(Character.toLowerCase(letter));
            } else {
                return "_" + Character.toLowerCase(letter);
            }
        } else {
            return String.valueOf(Character.toLowerCase(letter));
        }
    }
}
