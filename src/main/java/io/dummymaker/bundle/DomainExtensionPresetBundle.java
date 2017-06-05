package io.dummymaker.bundle;

import java.util.ArrayList;

/**
 * Default Comment
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
public class DomainExtensionPresetBundle extends IPresetBundle<String> {

    public DomainExtensionPresetBundle() {
        super(new ArrayList<String>() {{
            add(".com");
            add(".co");
            add(".net");
            add(".org");
            add(".club");
            add(".shop");
            add(".ru");
            add(".me");
            add(".io");
            add(".ca");
            add(".mail");
            add(".be");
            add(".ch");
            add(".cl");
            add(".city");
            add(".center");
            add(".company");
            add(".person");
            add(".de");
        }});
    }
}
