package io.dummymaker.export.container;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 29.08.2017
 */
public interface IClassContainer {

    String originClassName();
    String finalClassName();

    String convertUsingNamingStrategy(final String value);

    Map<String, Field> originFields();
    Map<String, Field> finalFields();

    Map<String, String> renamedFields();
}
