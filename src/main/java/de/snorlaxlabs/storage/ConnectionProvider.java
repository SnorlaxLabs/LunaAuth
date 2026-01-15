package de.snorlaxlabs.storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionProvider {

    private final HikariDataSource dataSource;

    /**
     * MariaDB / MySQL Constructor
     */
    public ConnectionProvider(
            String host,
            String user,
            String password,
            int port,
            String database,
            int poolSize,
            boolean useSSL
    ) {
        HikariConfig config = new HikariConfig();

        String params = "?useUnicode=true&characterEncoding=utf8&useSSL=" + useSSL;

        config.setJdbcUrl("jdbc:mariadb://" + host + ":" + port + "/" + database + params);
        config.setUsername(user);
        config.setPassword(password);

        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setPoolName("LunaAuth-MariaDB");

        config.setMaximumPoolSize(Math.max(2, poolSize));
        config.setMinimumIdle(1);

        config.setConnectionTimeout(10_000);
        config.setIdleTimeout(600_000);
        config.setMaxLifetime(1_800_000);

        config.setConnectionInitSql(SQLQuerys.EXTERNAL.INITIAL_DATABASE_QUERY.getSql());

        this.dataSource = new HikariDataSource(config);
    }

    /**
     * H2 Constructor
     */
    public ConnectionProvider() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(
                "jdbc:h2:~/.lunaauth/h2Test;" +
                        "AUTO_SERVER=TRUE;" +
                        "MODE=MySQL"
        );

        config.setUsername("");
        config.setPassword("");

        config.setDriverClassName("org.h2.Driver");
        config.setPoolName("LunaAuth-H2");

        config.setMaximumPoolSize(5);
        config.setMinimumIdle(1);

        config.setConnectionTimeout(10_000);
        config.setIdleTimeout(600_000);
        config.setMaxLifetime(1_800_000);

        config.setConnectionInitSql(SQLQuerys.INTERNAL.INITIAL_DATABASE_QUERY.getSql());

        this.dataSource = new HikariDataSource(config);
    }

    /**
     * Get pooled connection
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Shutdown pool cleanly
     */
    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
