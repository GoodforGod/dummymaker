package io.dummymaker;

import io.dummymaker.factory.impl.GenProduceFactory;

/**
 * Hello DummyMaker!
 */
class App {
    public static void main(String[] args) {
        Dum dum = new GenProduceFactory().produce(Dum.class);
        System.out.println(dum);
    }
}
