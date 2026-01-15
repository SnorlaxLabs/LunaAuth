package de.snorlaxlabs.storage;

import java.sql.Connection;
import java.sql.SQLException;

public class StorageProvider {

    private ConnectionProvider connectionProvider;

    public Connection connection(){
        try {
            return connectionProvider.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
