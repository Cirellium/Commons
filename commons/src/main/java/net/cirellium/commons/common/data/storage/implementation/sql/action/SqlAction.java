package net.cirellium.commons.common.data.storage.implementation.sql.action;

import lombok.Getter;

public enum SqlAction {

    CREATE_TABLE("CREATE TABLE IF NOT EXISTS %s (%s);"),
    INSERT("INSERT INTO %s (%s) VALUES (%s);"),
    SELECT("SELECT %s FROM %s WHERE %s;"),
    UPDATE("UPDATE %s SET %s WHERE %s;"),
    DELETE("DELETE FROM %s WHERE %s;"),
    DROP_TABLE("DROP TABLE IF EXISTS %s;");

    @Getter
    private final String statement;

    SqlAction(String action) {
        this.statement = action;
    }

    public void replace(String... args) {
        for (String arg : args) {
            statement.replaceFirst("%s", arg);
        }
    }
}