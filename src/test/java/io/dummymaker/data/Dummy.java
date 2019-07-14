package io.dummymaker.data;

import io.dummymaker.annotation.simple.number.GenDoubleBig;
import io.dummymaker.annotation.simple.string.GenCity;
import io.dummymaker.annotation.simple.string.GenName;
import io.dummymaker.annotation.special.GenEnumerate;
import io.dummymaker.annotation.special.GenExportForce;
import io.dummymaker.annotation.special.GenExportIgnore;
import io.dummymaker.annotation.special.GenExportName;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Dummy Object used as data to proceed in tests
 *
 * @author GoodforGod
 * @since 17.08.2017
 */
public class Dummy {

    /**
     * Used for scanners test, to check for correct fields
     *
     * @see io.dummymaker.scan.ScannerImplTest
     */
    public enum DummyFieldNames {
        UNCOMPA("uncompatible", "uncompatible", GenDoubleBig.class, GenExportIgnore.class),
        GROUP(  "group",    "socialGroup",  GenExportName.class),
        LNG(    "lng",      "lng",          GenEnumerate.class, GenExportIgnore.class),
        BIGD(   "bigd",     "bigd",         GenDoubleBig.class, GenExportIgnore.class),
        CITY(   "city",     "city",         GenCity.class, GenExportIgnore.class),
        NUM(    "num",      "num",          GenEnumerate.class),
        NAME(   "name",     "name",         GenName.class);

        private String originFieldName;
        private String exportFieldName;
        private Set<Class> annotations = new HashSet<>();

        DummyFieldNames(final String originFieldName, final String exportFieldName, Class... annotations) {
            this.originFieldName = originFieldName;
            this.exportFieldName = exportFieldName;
            this.annotations.addAll(Arrays.asList(annotations));
        }

        public String exportName() {
            return exportFieldName;
        }

        public String getOriginFieldName() {
            return originFieldName;
        }

        public Set<Class> getAnnotations() {
            return annotations;
        }
    }

    @GenExportName("socialGroup")
    @GenExportForce
    private String group = "100";

    @GenCity
    @GenExportIgnore
    private String city;

    @GenExportIgnore
    @GenDoubleBig
    private List uncompatible;

    @GenExportIgnore
    @GenDoubleBig
    private String bigd;

    @GenExportIgnore
    @GenEnumerate
    private Long lng;

    @GenEnumerate
    private Integer num;

    @GenName
    private String name;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCity() {
        return city;
    }

    public Integer getNum() {
        return num;
    }

    public String getName() {
        return name;
    }
}
