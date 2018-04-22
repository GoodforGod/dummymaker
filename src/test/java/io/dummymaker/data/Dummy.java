package io.dummymaker.data;

import io.dummymaker.annotation.simple.number.GenDoubleBig;
import io.dummymaker.annotation.simple.string.GenCity;
import io.dummymaker.annotation.simple.string.GenName;
import io.dummymaker.annotation.special.GenEnumerate;
import io.dummymaker.annotation.special.GenForceExport;
import io.dummymaker.annotation.special.GenIgnoreExport;
import io.dummymaker.annotation.special.GenRenameExport;

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
        UNCOMPA("uncompatible", "uncompatible", GenDoubleBig.class, GenIgnoreExport.class),
        GROUP(  "group",    "socialGroup",  GenRenameExport.class),
        LNG(    "lng",      "lng",          GenEnumerate.class, GenIgnoreExport.class),
        BIGD(   "bigd",     "bigd",         GenDoubleBig.class, GenIgnoreExport.class),
        CITY(   "city",     "city",         GenCity.class, GenIgnoreExport.class),
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

        public String getExportFieldName() {
            return exportFieldName;
        }

        public String getOriginFieldName() {
            return originFieldName;
        }

        public Set<Class> getAnnotations() {
            return annotations;
        }
    }

    @GenRenameExport("socialGroup")
    @GenForceExport
    private String group = "100";

    @GenCity
    @GenIgnoreExport
    private String city;

    @GenIgnoreExport
    @GenDoubleBig
    private List uncompatible;

    @GenIgnoreExport
    @GenDoubleBig
    private String bigd;

    @GenIgnoreExport
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
