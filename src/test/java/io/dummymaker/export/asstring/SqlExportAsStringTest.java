package io.dummymaker.export.asstring;

import io.dummymaker.data.Dummy;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.NamingStrategy;
import io.dummymaker.export.impl.SqlExporter;
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

    private IProduceFactory<Dummy> produceFactory = new GenProduceFactory<>(Dummy.class);

    private SqlValidation validation = new SqlValidation();

    @Test
    public void exportSingleDummyInSql() {
        Dummy dummy = produceFactory.produce();
        IExporter<Dummy> exporter = new SqlExporter<>(Dummy.class);

        String dummyAsString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsString);

        String[] sqlArray = dummyAsString.split("\n");
        assertEquals(9, sqlArray.length);

        validation.isSingleDummyValid(sqlArray);
    }

    @Test
    public void exportListOfDummiesInSql() {
        List<Dummy> dummies = produceFactory.produce(2);
        IExporter<Dummy> exporter = new SqlExporter<>(Dummy.class);

        String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        String[] sqlArray = dummyAsString.split("\n");
        assertEquals(10, sqlArray.length);

        validation.isTwoDummiesValid(sqlArray);
    }

    @Test
    public void exportListOfDummiesInSqlWithNamingStrategy() {
        final NamingStrategy strategy = NamingStrategy.UNDERSCORED_LOW_CASE;

        List<Dummy> dummies = produceFactory.produce(2);
        IExporter<Dummy> exporter = new SqlExporter<>(Dummy.class);

        String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        String[] sqlArray = dummyAsString.split("\n");
        assertEquals(10, sqlArray.length);

        validation.isTwoDummiesValid(sqlArray);
    }
}
