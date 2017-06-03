package io.model.dummymaker.export.impl;

import io.model.dummymaker.export.ExportType;
import io.model.dummymaker.export.OriginExporter;

import java.io.IOException;
import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public class SqlExporter<T> extends OriginExporter<T> {

    private enum SqlDataType {
        INTEGER("INT"),
        LONG("BIGINT"),
        LOCAL_DATE_TIME("TIMESTAMP"),
        DOUBLE("DOUBLE"),
        STRING("VARCHAR");

        SqlDataType(String value) {
            this.value = value;
        }

        private String value;

        public String getValue() {
            return value;
        }
    }

    private String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `$0` ($1)";
    private String SQL_INSERT = "INSERT INTO `$0` (`$1`) VALUES";

    public SqlExporter(Class<T> primeClass) {
        super(primeClass, ExportType.SQL);
    }

    public SqlExporter(Class<T> primeClass, String path) {
        super(primeClass, path, ExportType.SQL);
    }

    private String objToSql(T t) {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

    @Override
    public void export(T t) {
        try {
            writeLine(objToSql(t));
        }
        catch (IOException e) {
            logger.warning(e.getMessage());
        }
        finally {
            try {
                flush();
            } catch (IOException e) {
                logger.warning(e.getMessage());
            }
        }
    }

    @Override
    public void export(List<T> t) {
        try {
            writeLine("");

            t.forEach(this::objToSql);

            writeLine("");
        }
        catch (IOException e) {
            logger.warning(e.getMessage());
        }
        finally {
            try {
                flush();
            } catch (IOException e) {
                logger.warning(e.getMessage());
            }
        }
    }
}
