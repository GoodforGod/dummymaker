package io.dummymaker.export.asstring;

import io.dummymaker.data.Dummy;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.SqlExporter;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.export.naming.Strategies;
import io.dummymaker.export.validators.SqlValidator;
import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.factory.impl.GenProduceFactory;
import org.junit.Test;

import java.util.List;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
public class SqlExportAsStringTest extends StringExportAssert {

    private final IProduceFactory produceFactory = new GenProduceFactory();

    private final SqlValidator validation = new SqlValidator();

    public SqlExportAsStringTest() {
        super(new SqlExporter().withPath(null).withStrategy((IStrategy) null).withPath("            "),
                new SqlValidator(), 9, 10);
    }

    @Test
    public void exportListOfDummiesInSqlWithNamingStrategy() throws Exception {
        final IStrategy strategy = Strategies.UNDERSCORED_LOW_CASE.getStrategy();

        final List<Dummy> dummies = produceFactory.produce(Dummy.class, 2);
        final IExporter exporter = new SqlExporter().withStrategy(strategy).withPath("    ");

        final String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(10, sqlArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(sqlArray, strategy);
    }
}
