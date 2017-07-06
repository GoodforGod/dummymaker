package io.dummymaker.annotation.special;

import java.lang.annotation.*;

/**
 * Allow to rename fields and class names when export
 * To rename class you should annotate its constructor
 *
 * @author GoodforGod (Anton Kurako)
 * @since 06.07.2017
 */
@Inherited
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD, ElementType.CONSTRUCTOR })
public @interface GenRenameExport {
    String name();
}
