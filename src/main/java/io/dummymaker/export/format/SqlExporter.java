package io.dummymaker.export.format;

import io.dummymaker.export.IAbstractExporter;

import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public class SqlExporter<T> extends IAbstractExporter<T> {

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
        super(primeClass);
    }

    @Override
    public String export(T t) {
        return null;
    }

    @Override
    public String export(List<T> t) {

        return null;
    }
}
