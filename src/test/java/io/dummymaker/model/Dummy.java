package io.dummymaker.model;

import io.dummymaker.annotation.GenIgnore;
import io.dummymaker.annotation.complex.GenSequence;
import io.dummymaker.annotation.export.GenExportForce;
import io.dummymaker.annotation.export.GenExportIgnore;
import io.dummymaker.annotation.export.GenExportName;
import io.dummymaker.annotation.simple.number.GenDoubleSmall;
import io.dummymaker.annotation.simple.string.GenCity;
import io.dummymaker.annotation.simple.string.GenName;
import java.lang.reflect.Field;
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

    private static final int IGNORED_INT = 1;

    public static long IGNORED_LONG = 2;

    private final int ignoredAuto = 10;

    /**
     * Used for scanners test, to check for correct fields
     */
    public enum DummyFields {

        UNCOMPA("uncompatible", "uncompatible", GenDoubleSmall.class, GenExportIgnore.class),
        GROUP("group", "socialGroup", GenExportName.class),
        LNG("lng", "lng", GenSequence.class, GenExportIgnore.class),
        BIGD("bigd", "bigd", GenDoubleSmall.class, GenExportIgnore.class),
        CITY("city", "city", GenCity.class, GenExportIgnore.class),
        NUM("num", "num", GenSequence.class),
        NAME("name", "name", GenName.class);

        private String origin;
        private String export;
        private Set<Class> annotations = new HashSet<>();

        DummyFields(final String origin, final String export, Class... annotations) {
            this.origin = origin;
            this.export = export;
            this.annotations.addAll(Arrays.asList(annotations));
        }

        public String exportName() {
            return export;
        }

        public String getOrigin() {
            return origin;
        }

        public Field getField() {
            try {
                return Dummy.class.getDeclaredField(origin);
            } catch (NoSuchFieldException e) {
                throw new IllegalStateException(e);
            }
        }

        public Set<Class> getAnnotations() {
            return annotations;
        }
    }

    public int getIgnoredInt() {
        return IGNORED_INT;
    }

    public long getIgnoredLong() {
        return IGNORED_LONG;
    }

    public int getIgnoredAuto() {
        return ignoredAuto;
    }

    @GenExportName("socialGroup")
    @GenExportForce
    @GenIgnore
    private String group = "100";

    @GenCity
    @GenExportIgnore
    private String city;

    @GenExportIgnore
    @GenDoubleSmall
    private List uncompatible;

    @GenExportIgnore
    @GenDoubleSmall
    private String bigd;

    @GenExportIgnore
    @GenSequence
    private Long lng;

    @GenSequence
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

    public List getUncompatible() {
        return uncompatible;
    }

    public String getBigd() {
        return bigd;
    }

    public Long getLng() {
        return lng;
    }
}
