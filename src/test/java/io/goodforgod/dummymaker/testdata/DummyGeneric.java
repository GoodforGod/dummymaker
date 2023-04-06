package io.goodforgod.dummymaker.testdata;

public class DummyGeneric {

    public static class DummyParent<A> {

        private A fieldA;

        public A getFieldA() {
            return fieldA;
        }
    }

    public static class DummyChild<B> extends DummyParent<String> {

        private B fieldB;

        public B getFieldB() {
            return fieldB;
        }
    }

    public static class DummyChildChild extends DummyChild<Integer> {

        private Long fieldC;

        public Long getFieldC() {
            return fieldC;
        }
    }

    private String name;
    private DummyChildChild dummyChildChild;

    public String getName() {
        return name;
    }

    public DummyChildChild getDummyChildChild() {
        return dummyChildChild;
    }
}
