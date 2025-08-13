package net.cirellium.commons.common.data.storage.implementation.sql.query.condition;

public record SqlCondition(String columnName, String operator, String value) {

    @Override
    public String toString() {
        return columnName + " " + operator + " " + value;
    }

    interface Builder {
        Builder columnName(String columnName);
        Builder operator(String operator);
        Builder value(String value);
        SqlCondition build();
    }

    public static Builder builder() {
        return new SqlConditionBuilder();
    }

    public static class SqlConditionBuilder implements Builder {
        private String columnName;
        private String operator;
        private String value;

        public SqlConditionBuilder columnName(String columnName) {
            this.columnName = columnName;
            return this;
        }

        public SqlConditionBuilder operator(String operator) {
            this.operator = operator;
            return this;
        }

        public SqlConditionBuilder value(String value) {
            this.value = value;
            return this;
        }

        public SqlCondition build() {
            return new SqlCondition(columnName, operator, value);
        }
    }
}