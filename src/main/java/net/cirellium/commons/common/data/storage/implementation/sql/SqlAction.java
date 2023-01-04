package net.cirellium.commons.common.data.storage.implementation.sql;

public enum SqlAction {

    CREATE_TABLE("CREATE TABLE IF NOT EXISTS %s (%s);"),
    INSERT("INSERT INTO %s (%s) VALUES (%s);"),
    SELECT("SELECT %s FROM %s WHERE %s;"),
    UPDATE("UPDATE %s SET %s WHERE %s;"),
    DELETE("DELETE FROM %s WHERE %s;"),
    DROP_TABLE("DROP TABLE IF EXISTS %s;");

    private final String action;

    SqlAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void replace(String... args) {
        for (String arg : args) {
            action.replaceFirst("%s", arg);
        }
    }
}