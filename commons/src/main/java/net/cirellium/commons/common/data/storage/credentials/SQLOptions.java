package net.cirellium.commons.common.data.storage.credentials;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import net.cirellium.commons.common.data.config.DatabaseCredentials;
import net.cirellium.commons.common.file.PluginFile;

public record SQLOptions(DatabaseCredentials credentials, String sqliteFileName, int maxPoolSize, int minIdle, int maxLifetime,
        int connectionTimeout, int keepaliveTime, Map<String, String> properties) {

    public static final String PREFIX = "database.";

    public static SQLOptions from(PluginFile<?> databaseFile) {
        return new SQLOptions(
                DatabaseCredentials.from(databaseFile),
                databaseFile.getValue(String.class, PREFIX + "sqlite-file-name"),
                databaseFile.getValue(int.class, PREFIX + "hikari.max-pool-size"),
                databaseFile.getValue(int.class, PREFIX + "hikari.min-idle"),
                databaseFile.getValue(int.class, PREFIX + "hikari.max-lifetime"),
                databaseFile.getValue(int.class, PREFIX + "hikari.connection-timeout"),
                databaseFile.getValue(int.class, PREFIX + "hikari.keepalive"),
                new HashMap<String, String>());
    }

    @Data
    public static class HikariCredentials {
        String user, address, port, password, database, tablePrefix;

        public HikariCredentials(Builder b) {
            this.user = b.user;
            this.address = b.address;
            this.port = b.port;
            this.password = b.password;
            this.database = b.database;
            this.tablePrefix = b.tablePrefix;
        }

        public static class Builder {
            private String user, address, port, password, database, tablePrefix;

            public Builder() {
            }

            public Builder user(String user) {
                this.user = user;
                return this;
            }

            public Builder address(String address) {
                this.address = address;
                return this;
            }

            public Builder port(String port) {
                this.port = port;
                return this;
            }

            public Builder password(String password) {
                this.password = password;
                return this;
            }

            public Builder database(String database) {
                this.database = database;
                return this;
            }

            public Builder tablePrefix(String tablePrefix) {
                this.tablePrefix = tablePrefix;
                return this;
            }

            public HikariCredentials build() {
                return new HikariCredentials(this);
            }
        }

        public static Builder builder() {
            return new Builder();
        }
    }
}