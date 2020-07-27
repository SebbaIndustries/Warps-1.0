package com.sebbaindustries.warps.database;

import com.sebbaindustries.warps.Core;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public abstract class ConnectionFactory {

    public abstract Connection getConn() throws SQLException;

    protected HikariDataSource configureDataSource() {
        HikariConfig config = new HikariConfig(
                Core.getPlugin(Core.class).getDataFolder() + "/hikari.properties"
        );
        return new HikariDataSource(config);
    }

    protected Properties readPropertiesFile(String fileName) {
        FileInputStream fis = null;
        Properties prop;
        try {
            try {
                fis = new FileInputStream(fileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            prop = new Properties();
            try {
                prop.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return prop;
    }

}
