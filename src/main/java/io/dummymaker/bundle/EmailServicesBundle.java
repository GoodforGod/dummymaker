package io.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * Contains EMAIL service provides as string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 30.05.2017
 */
public final class EmailServicesBundle extends AbstractBundle {

    private static final List<String> BUNDLE = Arrays.asList(
                "zoho",
                "yandex",
                "mail",
                "gmail",
                "outlook",
                "proton",
                "aim",
                "icloud",
                "yahoo",
                "custom",
                "aol",
                "gmx",
                "hotmail",
                "inbox");

    public EmailServicesBundle() {
        super(BUNDLE);
    }
}
