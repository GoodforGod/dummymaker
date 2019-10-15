package io.dummymaker.beta.model;

import io.dummymaker.annotation.special.GenAuto;

import java.util.List;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 11.08.2019
 */
@GenAuto(depth = 3)
public class DummyEmbedded {

    public class DummyEmbeddedIntoSimple {
        private int number;
        private String name;
        private DummyEmbedded embedded;

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

    @GenAuto(depth = 4)
    public class DummyEmbeddedSimple {
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

    public class DummyEmbeddedChild {
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
}
