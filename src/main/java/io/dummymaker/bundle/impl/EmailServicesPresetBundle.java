package io.dummymaker.bundle.impl;

import java.util.ArrayList;

/**
 * Contains EMAIL service provides as string
 *
 * @author GoodforGod
 * @since 30.05.2017
 */
public class EmailServicesPresetBundle extends BasicBundle<String> {

    public EmailServicesPresetBundle() {
        super(new ArrayList<String>() {{
            add("zoho");
            add("yandex");
            add("mail");
            add("gmail");
            add("outlook");
            add("proton");
            add("aim");
            add("icloud");
            add("yahoo");
            add("custom");
            add("aol");
            add("gmx");
            add("hotmail");
            add("inbox");
        }}
        );
    }
}
