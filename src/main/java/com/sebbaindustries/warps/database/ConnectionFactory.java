package com.sebbaindustries.warps.database;

import com.sebbaindustries.warps.Core;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class ConnectionFactory {

    public abstract Connection getConn() throws SQLException;

    protected HikariDataSource configureDataSource() {
        HikariConfig config = new HikariConfig(
                Core.getPlugin(Core.class).getDataFolder() + "/hikari.properties"
        );
        return new HikariDataSource(config);
    }

}
