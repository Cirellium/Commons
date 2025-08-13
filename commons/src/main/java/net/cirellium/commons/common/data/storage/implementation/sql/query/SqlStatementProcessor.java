package net.cirellium.commons.common.data.storage.implementation.sql.query;

import java.util.stream.Collectors;

import net.cirellium.commons.common.data.storage.implementation.sql.action.SqlAction.TypedSqlAction;
import net.cirellium.commons.common.data.storage.implementation.sql.query.SqlStatement.QueryStatement;
import net.cirellium.commons.common.data.storage.implementation.sql.query.SqlStatement.UpdateStatement;
import net.cirellium.commons.common.data.storage.implementation.sql.query.condition.SqlCondition;
import net.cirellium.commons.common.util.Processor;

public class SqlStatementProcessor implements Processor<SqlStatement, String> {

    @Override
    public String process(SqlStatement statement) {
        if (statement.getAction() instanceof TypedSqlAction typed) {
            return switch (typed.getType()) {
                case QUERY -> sqlQueryProcessor.process((QueryStatement) statement);
                case UPDATE -> sqlUpdateProcessor.process((UpdateStatement) statement);
                default -> throw new IllegalArgumentException("Unknown SQL action type: " + typed.getType());
            };
        }
        throw new IllegalArgumentException("All SQL actions must specify a type");
    }

    Processor<QueryStatement, String> sqlQueryProcessor = (statement) -> {
        String finalQuery = statement.getAction().getStatement();

        finalQuery.replace("{columns}", statement.getColumns().stream().collect(Collectors.joining(", ")));
        finalQuery.replace("{table}", statement.getTable());
        finalQuery.replace("{conditions}", statement.getConditions().stream().map(SqlCondition::toString).collect(Collectors.joining(" AND ")));
        finalQuery.replace("{order}", SqlStatement.ORDER_PROVIDER.provide(statement.getOrder()));
        finalQuery.replace("{limit}", SqlStatement.LIMIT_PROVIDER.provide(statement.getLimit()));

        return finalQuery;
    };

    Processor<UpdateStatement, String> sqlUpdateProcessor = (statement) -> {
        String finalQuery = statement.getAction().getStatement();

        finalQuery.replace("{table}", statement.getTable());
        finalQuery.replace("{values}", statement.getValues().entrySet().stream().map(e -> e.getKey() + " = " + e.getValue()).collect(Collectors.joining(", ")));
        finalQuery.replace("{conditions}", statement.getConditions().stream().map(SqlCondition::toString).collect(Collectors.joining(" AND ")));

        return finalQuery;
    };
}