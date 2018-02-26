package io.dummymaker.export.asstring;

import io.dummymaker.data.Dummy;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.StaticSqlExporter;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.export.naming.PresetStrategies;
import io.dummymaker.export.validation.SqlValidation;
import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.factory.impl.GenProduceFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
public class SqlExportAsStringTest {

    private IProduceFactory produceFactory = new GenProduceFactory();

    private SqlValidation validation = new SqlValidation();

    @Test
    public void exportSingleDummyInSql() throws Exception {
        Dummy dummy = produceFactory.produce(Dummy.class);
        IExporter exporter = new StaticSqlExporter();

        String dummyAsString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsString);

        String[] sqlArray = dummyAsString.split("\n");
        assertEquals(9, sqlArray.length);

        validation.isSingleDummyValid(sqlArray);
    }

    @Test
    public void exportListOfDummiesInSql() throws Exception {
        List<Dummy> dummies = produceFactory.produce(Dummy.class, 2);
        IExporter exporter = new StaticSqlExporter();

        String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        String[] sqlArray = dummyAsString.split("\n");
        assertEquals(10, sqlArray.length);

        validation.isTwoDummiesValid(sqlArray);
    }

    @Test
    public void exportListOfDummiesInSqlWithNamingStrategy() throws Exception {
        final IStrategy strategy = PresetStrategies.UNDERSCORED_LOW_CASE.getStrategy();

        List<Dummy> dummies = produceFactory.produce(Dummy.class, 2);
        IExporter exporter = new StaticSqlExporter().withStrategy(strategy);

        String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        String[] sqlArray = dummyAsString.split("\n");
        assertEquals(10, sqlArray.length);

        validation.isTwoDummiesValidithNamingStratery(sqlArray, strategy);
    }
}
