package com.sebbaindustries.warps.database;

import com.sebbaindustries.warps.Core;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.SQLException;
import java.util.Properties;

public class Connection extends ConnectionFactory {

    public Connection() {
        this.dataSource = configureDataSource();
    }

    private final HikariDataSource dataSource;

    @Override
    public java.sql.Connection getConn() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final String getDatabaseName() {
        Properties properties = readPropertiesFile(Core.getPlugin(Core.class).getDataFolder() + "/hikari.properties");
        return properties.getProperty("dataSource.databaseName");
    }

}
