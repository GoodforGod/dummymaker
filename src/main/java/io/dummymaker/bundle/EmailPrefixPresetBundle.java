package io.dummymaker.bundle;

import java.util.ArrayList;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 30.05.2017
 */
class EmailPrefixPresetBundle extends IPresetBundle<String> {

    public EmailPrefixPresetBundle() {
        super(new ArrayList<String>() {{
            add("");
        }});
    }
}
