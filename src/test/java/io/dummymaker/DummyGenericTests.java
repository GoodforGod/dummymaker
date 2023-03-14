package io.dummymaker;

import io.dummymaker.testdata.DummyGeneric;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DummyGenericTests extends Assertions {

    @Test
    void genericTypeFieldBuild() {
        final GenFactory factory = GenFactory.build();

        final DummyGeneric build = factory.build(DummyGeneric.class);

        assertNotNull(build);
        assertNotNull(build.getName());
        assertNotNull(build.getDummyChildChild());
        assertNotNull(build.getDummyChildChild().getFieldA());
        assertNotNull(build.getDummyChildChild().getFieldB());
        assertNotNull(build.getDummyChildChild().getFieldC());
    }
}
