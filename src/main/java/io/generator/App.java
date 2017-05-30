package io.generator;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) {

        TestCaseClass a = new TestCaseClass();
        EntityGenFactory<TestCaseClass> factory = new EntityGenFactory<>(TestCaseClass.class);

        a = factory.populate(a);

        System.out.println( "Hello World!" );
    }
}
