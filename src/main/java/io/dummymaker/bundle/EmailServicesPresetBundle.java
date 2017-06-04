package io.dummymaker.bundle;

import java.util.ArrayList;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 30.05.2017
 */
public class EmailServicesPresetBundle extends IPresetBundle<String> {

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
        }});
    }
}
