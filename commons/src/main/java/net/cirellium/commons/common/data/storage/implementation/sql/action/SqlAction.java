package net.cirellium.commons.common.data.storage.implementation.sql.action;

import lombok.Getter;
import net.cirellium.commons.common.util.SimpleProvider;

/**
 * The {@code SqlAction} interface represents an SQL action that can be
 * performed on a database.
 * It extends the {@code SimpleProvider<String>} interface, which provides a
 * method to retrieve the SQL statement.
 */
public interface SqlAction extends SimpleProvider<String> {

    /**
     * Returns the SQL statement for the action.
     *
     * @return the SQL statement
     */
    String getStatement();

    /**
     * The {@code TypedSqlAction} interface represents an SQL action with a specific
     * type.
     */
    public interface TypedSqlAction extends SqlAction {

        /**
         * The {@code Type} enum represents the type of SQL action, either QUERY or
         * UPDATE.
         */
        public static enum Type {
            QUERY, UPDATE;
        }

        /**
         * Returns the type of the SQL action.
         *
         * @return the type of the SQL action
         */
        Type getType();
    }

    /**
     * Replaces placeholders in the SQL statement with the provided arguments.
     *
     * @param args the arguments to replace the placeholders with
     * @return the SQL statement with replaced placeholders
     */
    @Deprecated
    default String params(String... args) {
        String statement = getStatement();
        for (String arg : args) {
            statement.replaceFirst("%s", arg);
        }
        return statement;
    }

    /**
     * Provides the SQL statement for the action.
     *
     * @return the SQL statement
     */
    @Override
    default String provide() {
        return getStatement();
    }

    /**
     * 
     * The {@code DefaultAction} enum provides default implementations of the {@c
     * de SqlAction} interface.
     * Each enum constant represents a specific SQL action with its corresponding
     * SQL statement and type.
     */
    @Getter
    public enum DefaultSqlAction implements TypedSqlAction {
        CREATE_TABLE(() -> "CREATE TABLE IF NOT EXISTS {table} ({values});", Type.UPDATE),
        DROP_TABLE(() -> "DROP TABLE IF EXISTS {table};", Type.UPDATE),
        INSERT(() -> "INSERT INTO {table} ({columns}) VALUES ({values});", Type.UPDATE),
        SELECT(() -> "SELECT {columns} FROM {table} WHERE {conditions} {order} {limit}", Type.QUERY),
        UPDATE(() -> "UPDATE {table} SET {values} WHERE {conditions};", Type.UPDATE),
        DELETE(() -> "DELETE FROM {table} WHERE {conditions};", Type.UPDATE),

        ;

        private final SqlAction action;
        private final Type type;

        /**
         * Constructs a {@code DefaultAction} with the specified SQL action and type.
         *
         * @param action the SQL action
         * @param type   the type of the SQL action
         */
        DefaultSqlAction(SqlAction action, Type type) {
            this.action = action;
            this.type = type;
        }

        /**
         * Returns the SQL statement for the action.
         *
         * @return the SQL statement
         */
        @Override
        public String getStatement() {
            return action.getStatement();
        }
    }
}