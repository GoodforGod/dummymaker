package io.dummymaker;

import java.util.List;

/**
 * Hello Generator Factory!
 *
 */
public class App {

    public static void main( String[] args ) {

        IPrimeFactory<TestCaseClass> factory = new GenPrimeFactory<>(TestCaseClass.class);

        TestCaseClass t = factory.produce();
        List<TestCaseClass> tList = factory.produce(10);

        System.out.println( "Hello World!" );
    }
}
