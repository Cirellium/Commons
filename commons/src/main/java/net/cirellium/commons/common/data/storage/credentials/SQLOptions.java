package net.cirellium.commons.common.data.storage.credentials;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import net.cirellium.commons.common.data.config.DatabaseOptions;
import net.cirellium.commons.common.file.PluginFile;

public class SQLOptions extends DatabaseOptions {

    public static final String PREFIX = "database.";

    @Getter
    @Setter
    String sqliteFileName;

    @Getter
    @Setter
    int maxPoolSize, minIdle, maxLifetime, connectionTimeout, keepaliveTime;

    @Getter
    @Setter
    Map<String, String> properties = new HashMap<>();

    public SQLOptions(
            int syncInterval,
            int saveInterval,
            String user,
            String address,
            String password,
            String database,
            String tablePrefix,
            String sqliteFileName,
            boolean useSSL,
            int maxPoolSize,
            int minIdle,
            int maxLifetime,
            int connectionTimeout,
            int keepaliveTime) {
        super(syncInterval, saveInterval, user, address, password, database, tablePrefix, useSSL);
        this.sqliteFileName = sqliteFileName;
        this.maxPoolSize = maxPoolSize;
        this.minIdle = minIdle;
        this.maxLifetime = maxLifetime;
        this.connectionTimeout = connectionTimeout;
        this.keepaliveTime = keepaliveTime;
    }

    public static SQLOptions from(PluginFile<?> databaseFile) {
        return new SQLOptions(
                databaseFile.getValueAs(int.class, PREFIX + "sync-interval").orElse(0),
                databaseFile.getValueAs(int.class, PREFIX + "save-interval").orElse(0),
                databaseFile.getValueAs(String.class, PREFIX + "user").orElse("user"),
                databaseFile.getValueAs(String.class, PREFIX + "address").orElse("address"),
                databaseFile.getValueAs(String.class, PREFIX + "password").orElse("password"),
                databaseFile.getValueAs(String.class, PREFIX + "database").orElse("database"),
                databaseFile.getValueAs(String.class, PREFIX + "table-prefix").orElse("table-prefix"),
                databaseFile.getValueAs(String.class, PREFIX + "sqlite-file-name").orElse("sqlite-file-name"),
                databaseFile.getValueAs(boolean.class, PREFIX + "use-ssl").orElse(false),
                databaseFile.getValueAs(int.class, PREFIX + "hikari.max-pool-size").orElse(0),
                databaseFile.getValueAs(int.class, PREFIX + "hikari.min-idle").orElse(0),
                databaseFile.getValueAs(int.class, PREFIX + "hikari.max-lifetime").orElse(0),
                databaseFile.getValueAs(int.class, PREFIX + "hikari.connection-timeout").orElse(0),
                databaseFile.getValueAs(int.class, PREFIX + "hikari.keepalive").orElse(0));
    }

    public static class HikariCredentials {
        @Getter
        @Setter
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