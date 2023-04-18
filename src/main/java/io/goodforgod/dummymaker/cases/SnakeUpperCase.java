package io.goodforgod.dummymaker.cases;

/**
 * Each upper letter separated with underscore symbol, and transform to upper case EXCLUDING FIRST
 * LETTER, first letter to low case Example: ( DummyList - DUMMY_LIST )
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
final class SnakeUpperCase extends AbstractSeparatorCase {

    SnakeUpperCase() {}

    @Override
    protected String mapFirstLetter(char letter) {
        return String.valueOf(Character.toUpperCase(letter));
    }

    @Override
    protected String mapAfterSeparator(char letter) {
        return "_" + Character.toUpperCase(letter);
    }

    @Override
    protected String mapDefault(char previousLetter, char letter) {
        if (Character.isUpperCase(letter)) {
            if (Character.isUpperCase(previousLetter)) {
                return String.valueOf(Character.toUpperCase(letter));
            } else {
                return "_" + Character.toUpperCase(letter);
            }
        } else {
            return String.valueOf(Character.toUpperCase(letter));
        }
    }
}
