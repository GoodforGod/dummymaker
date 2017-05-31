package io.dummymaker.bundle;

import java.util.ArrayList;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public class EmailEndingPresetBundle extends IPresetBundle<String> {

    public EmailEndingPresetBundle() {
        super(new ArrayList<String>() {{
            add("");
        }});
    }
}
