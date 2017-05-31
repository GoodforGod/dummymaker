package io.generator;

import io.generator.factory.EntityGenFactory;

/**
 * Hello Generator Factory!
 *
 */
public class App {

    public static void main( String[] args ) {

        EntityGenFactory<TestCaseClass> factory = new EntityGenFactory<>(TestCaseClass.class);

        TestCaseClass a = factory.populate(new TestCaseClass());
        TestCaseClass t = factory.produce();

        System.out.println( "Hello World!" );
    }
}
