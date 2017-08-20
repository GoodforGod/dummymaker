package io.dummymaker.data;

import io.dummymaker.annotation.GenCity;
import io.dummymaker.annotation.GenName;
import io.dummymaker.annotation.special.GenEnumerate;
import io.dummymaker.annotation.special.GenForceExport;
import io.dummymaker.annotation.special.GenIgnoreExport;
import io.dummymaker.annotation.special.GenRenameExport;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Dummy Object used as data to proceed in tests
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
public class Dummy {

    /**
     * Used for scanners test, to check for correct fields
     *
     * @see io.dummymaker.scan.ScannerImplTest
     */
    public enum DummyFieldNames {
        GROUP("group", GenRenameExport.class),
        CITY("city", GenCity.class, GenIgnoreExport.class),
        NUM("num", GenEnumerate.class),
        NAME("name", GenName.class);

        private String fieldName;
        private Set<Class> annotations = new HashSet<>();

        DummyFieldNames(final String fieldName, Class... annotations) {
            this.fieldName = fieldName;
            this.annotations.addAll(Arrays.asList(annotations));
        }

        public String getFieldName() {
            return fieldName;
        }

        public Set<Class> getAnnotations() {
            return annotations;
        }
    }

    @GenRenameExport(name = "socialGroup")
    @GenForceExport
    private String group = "100";

    @GenCity
    @GenIgnoreExport
    private String city;

    @GenEnumerate
    private Integer num;

    @GenName
    private String name;
}
