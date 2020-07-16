package com.sebbaindustries.warps.database;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.SQLException;

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

}
