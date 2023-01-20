package io.dummymaker.model;

import io.dummymaker.annotation.GenAuto;
import io.dummymaker.annotation.GenDepth;
import java.util.List;

/**
 * @author GoodforGod
 * @since 11.08.2019
 */
@GenDepth(3)
@GenAuto
public class DummyEmbedded {

    public static class DummyEmbeddedIntoSimple {

        private int number;
        private String name;
        private DummyEmbedded embedded;
        private DummyEmbeddedIntoSimple self;

        public int getNumber() {
            return number;
        }

        public String getName() {
            return name;
        }

        public DummyEmbedded getEmbedded() {
            return embedded;
        }
    }

    @GenDepth(4)
    @GenAuto
    public static class DummyEmbeddedSimple {

        private int number;
        private String simpleName;
        private DummyEmbeddedIntoSimple intoEmbedded;

        public int getNumber() {
            return number;
        }

        public String getSimpleName() {
            return simpleName;
        }

        public DummyEmbeddedIntoSimple getIntoEmbedded() {
            return intoEmbedded;
        }
    }

    public static class DummyEmbeddedChild {

        private String name;
        private String childId;
        private DummyEmbedded embedded;

        public String getChildId() {
            return childId;
        }

        public String getName() {
            return name;
        }

        public DummyEmbedded getEmbedded() {
            return embedded;
        }
    }

    private String id;
    private String name;
    private DummyEmbeddedChild child;
    private DummyEmbeddedSimple simpleChild;
    private List<DummyEmbedded> embeddedList;

    public List<DummyEmbedded> getEmbeddedList() {
        return embeddedList;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DummyEmbeddedChild getChild() {
        return child;
    }

    public DummyEmbeddedSimple getSimpleChild() {
        return simpleChild;
    }

    @Override
    public String toString() {
        return "DummyEmbedded{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", child=" + child +
                ", simpleChild=" + simpleChild +
                ", embeddedList=" + embeddedList +
                '}';
    }
}
