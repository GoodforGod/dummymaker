package io.dummymaker.cases;

/**
 * Each upper letter separated with underscore symbol, and transform to low case EXCLUDE FIRST
 * LETTER, first letter to low case Example: ( DummyList - dummy-list )
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.04.2018
 */
final class KebabLowerCase extends AbstractSeparatorCase {

    KebabLowerCase() {}

    @Override
    protected String mapFirstLetter(char letter) {
        return String.valueOf(Character.toLowerCase(letter));
    }

    @Override
    protected String mapAfterSeparator(char letter) {
        return "-" + Character.toLowerCase(letter);
    }

    @Override
    protected String mapDefault(char previousLetter, char letter) {
        if (Character.isUpperCase(letter)) {
            if (Character.isUpperCase(previousLetter)) {
                return String.valueOf(Character.toLowerCase(letter));
            } else {
                return "-" + Character.toLowerCase(letter);
            }
        } else {
            return String.valueOf(Character.toLowerCase(letter));
        }
    }
}
