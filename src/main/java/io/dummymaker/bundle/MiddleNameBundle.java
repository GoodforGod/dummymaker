package io.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 16.07.2019
 */
public final class MiddleNameBundle extends AbstractBundle {

    private static final List<String> BUNDLE = Arrays.asList(
            "James",
            "William",
            "John",
            "Arthur",
            "Charles",
            "Alexander",
            "George",
            "Henry",
            "David",
            "Edward",
            "Peter",
            "Anthony",
            "Robin",
            "Michael",
            "Richard",
            "Francis",
            "Phillip",
            "Thomas",
            "Angus",
            "Christopher",
            "Frederick",
            "Nicholas",
            "Robert");

    public MiddleNameBundle() {
        super(BUNDLE);
    }
}
