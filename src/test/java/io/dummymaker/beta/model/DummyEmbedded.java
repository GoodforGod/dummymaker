package io.dummymaker.beta.model;

import io.dummymaker.annotation.special.GenAuto;

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

        public int getNumber() {
            return number;
        }

        public String getName() {
            return name;
        }
    }

    @GenAuto(depth = 4)
    public class DummyEmbeddedSimple {
        private int number;
        private String name;

        private DummyEmbeddedIntoSimple embedded;

        public int getNumber() {
            return number;
        }

        public String getName() {
            return name;
        }

        public DummyEmbeddedIntoSimple getEmbedded() {
            return embedded;
        }
    }

    @GenAuto(depth = 2)
    public class DummyEmbeddedChild {

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

    private String id;
    private String name;
    private DummyEmbeddedChild child;
    private DummyEmbeddedSimple simpleChild;

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