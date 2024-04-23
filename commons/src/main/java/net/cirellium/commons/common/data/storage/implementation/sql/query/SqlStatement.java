package net.cirellium.commons.common.data.storage.implementation.sql.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import net.cirellium.commons.common.data.storage.implementation.sql.action.SqlAction;
import net.cirellium.commons.common.data.storage.implementation.sql.query.SqlStatement.Builder.QueryBuilder;
import net.cirellium.commons.common.data.storage.implementation.sql.query.SqlStatement.Builder.UpdateBuilder;
import net.cirellium.commons.common.data.storage.implementation.sql.query.condition.SqlCondition;
import net.cirellium.commons.common.util.Provider;

/**
 * Represents an abstract SQL statement.
 */
@Getter
public abstract class SqlStatement {

    public static final Provider<String, Order> ORDER_PROVIDER = (order) -> order == null ? "" : "ORDER BY " + order;
    public static final Provider<String, Integer> LIMIT_PROVIDER = (limit) -> limit == null ? "" : "LIMIT " + limit;

    private final SqlAction action;

    private final String table;
    private final List<String> columns;
    private final Map<String, String> values;
    private final List<SqlCondition> conditions;

    public SqlStatement(SqlAction action, String table, List<String> columns, Map<String, String> values,
            List<SqlCondition> conditions) {
        this.action = action;
        this.table = table;
        this.columns = columns;
        this.values = values;
        this.conditions = conditions;
    }

    public String getFinalStatement() {
        return new SqlStatementProcessor().process(this);
    }

    enum Order {
        ASC, DESC;
    }

    @Getter
    public static class QueryStatement extends SqlStatement {

        private final Order order;
        private final int limit;

        public QueryStatement(SqlAction action, String table, List<String> columns, Map<String, String> values,
                List<SqlCondition> conditions, Order order, int limit, String finalStatement) {
            super(action, table, columns, values, conditions);

            this.order = order;
            this.limit = limit;
        }

        public static Builder<? extends SqlStatement> builder() {
            return new SqlQueryBuilder();
        }
    }

    public static class UpdateStatement extends SqlStatement {

        public UpdateStatement(SqlAction action, String table, List<String> columns, Map<String, String> values,
                List<SqlCondition> conditions, String finalStatement) {
            super(action, table, columns, values, conditions);
        }

        public static Builder<? extends SqlStatement> builder() {
            return new SqlUpdateBuilder();
        }
    }

    public interface Builder<T extends SqlStatement> {

        Builder<T> action(SqlAction action);

        Builder<T> table(String table);

        Builder<T> columns(List<String> columns);

        Builder<T> values(String... values);

        Builder<T> condition(SqlCondition condition);

        T build();

        public static interface QueryBuilder extends Builder<QueryStatement> {
            Builder<QueryStatement> order(Order order);

            Builder<QueryStatement> limit(int limit);

            @Override
            QueryStatement build();
        }

        public static interface UpdateBuilder extends Builder<UpdateStatement> {
            @Override
            UpdateStatement build();
        }
    }

    public static abstract class SqlStatementBuilder<StatementType extends SqlStatement>
            implements SqlStatement.Builder<StatementType> {

        protected SqlAction action;
        protected String table;
        protected List<String> columns;
        protected Map<String, String> values = new HashMap<>();
        protected List<SqlCondition> conditions;

        @Override
        public SqlStatementBuilder<StatementType> action(SqlAction action) {
            this.action = action;
            return this;
        }

        @Override
        public SqlStatementBuilder<StatementType> table(String table) {
            this.table = table;
            return this;
        }

        @Override
        public SqlStatementBuilder<StatementType> columns(List<String> columns) {
            this.columns = columns;
            return this;
        }

        @Override
        public SqlStatementBuilder<StatementType> values(String... values) {
            if (values.length % 2 != 0)
                throw new IllegalArgumentException("Values must be in pairs");

            for (int i = 0; i < values.length; i += 2) {
                this.values.put(values[i], values[i + 1]);
            }
            return this;
        }

        @Override
        public SqlStatementBuilder<StatementType> condition(SqlCondition condition) {
            this.conditions.add(condition);
            return this;
        }

        @Override
        public abstract StatementType build();
    }

    public static class SqlQueryBuilder extends SqlStatementBuilder<QueryStatement> implements QueryBuilder {

        private Order order;
        private int limit;

        @Override
        public Builder<QueryStatement> order(Order order) {
            this.order = order;
            return this;
        }

        @Override
        public Builder<QueryStatement> limit(int limit) {
            this.limit = limit;
            return this;
        }

        @Override
        public QueryStatement build() {
            return new QueryStatement(action, table, columns, values, conditions, order, limit, "");
        }
    }

    public static class SqlUpdateBuilder extends SqlStatementBuilder<UpdateStatement> implements UpdateBuilder {

        @Override
        public UpdateStatement build() {
            return new UpdateStatement(action, table, columns, values, conditions, "");
        }
    }
}