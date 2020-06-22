package com.sebbaindustries.warps.utils;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.warp.Warp;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

/**
 * Database setup script, creates all tables and pulls any existing data from them
 * @author sebbaindustries
 * @version 1.0
 */
public class DBSetup {

    /**
     * Async data fetch
     * TODO add lock on the plugin, while async going on
     */
    public static void start() {
        CompletableFuture.runAsync(() -> {
            createTables();
            loadWarps();
        });
    }

    /**
     * Create tables if the don't exist
     */
    private static void createTables() {
        Connection con = Core.gCore.connection.getConn();
        try {

            /*
            -- -----------------------------------------------------
            -- Table `warps`.`warps`
            -- -----------------------------------------------------
             */
            con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS `warps`.`warps` ( " +
                            "`id` INT NOT NULL, " +
                            "`owner` VARCHAR(45) NULL, " +
                            "`name` VARCHAR(45) NULL, " +
                            "PRIMARY KEY (`id`)) " +
                            "ENGINE = InnoDB;"
            ).executeUpdate();

            /*
            -- -----------------------------------------------------
            -- Table `warps`.`warp_locations`
            -- -----------------------------------------------------
             */
            con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS `warps`.`warp_locations` ( " +
                            "`ID` INT NOT NULL, " +
                            "`world` VARCHAR(45) NULL, " +
                            "`x` DOUBLE NULL, " +
                            "`y` DOUBLE NULL, " +
                            "`z` DOUBLE NULL, " +
                            "`pitch` FLOAT NULL, " +
                            "`yaw` FLOAT NULL, " +
                            "PRIMARY KEY (`ID`)) " +
                            "ENGINE = InnoDB;"
            ).executeUpdate();

            /*
            -- -----------------------------------------------------
            -- Table `warps`.`warp_info`
            -- -----------------------------------------------------
             */
            con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS `warps`.`warp_info` ( " +
                            "`ID` INT NOT NULL, " +
                            "`type` INT NULL, " +
                            "`category` VARCHAR(45) NULL, " +
                            "`description` VARCHAR(200) NULL, " +
                            "`access` TINYINT NULL, " +
                            "PRIMARY KEY (`ID`)) " +
                            "ENGINE = InnoDB;"
            ).executeUpdate();

            /*
            -- -----------------------------------------------------
            -- Table `warps`.`warps_has_warp_info`
            -- -----------------------------------------------------
             */
            con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS `warps`.`warps_has_warp_info` ( " +
                    "`warps_id` INT NOT NULL, " +
                    "`warp_info_ID` INT NOT NULL, " +
                    "PRIMARY KEY (`warps_id`, `warp_info_ID`), " +
                    "INDEX `fk_warps_has_warp_info_warp_info1_idx` (`warp_info_ID` ASC) VISIBLE, " +
                    "INDEX `fk_warps_has_warp_info_warps1_idx` (`warps_id` ASC) VISIBLE, " +
                    "CONSTRAINT `fk_warps_has_warp_info_warps1` " +
                    "FOREIGN KEY (`warps_id`) " +
                    "REFERENCES `warps`.`warps` (`id`) " +
                    "ON DELETE NO ACTION " +
                    "ON UPDATE NO ACTION, " +
                    "CONSTRAINT `fk_warps_has_warp_info_warp_info1` " +
                    "FOREIGN KEY (`warp_info_ID`) " +
                    "REFERENCES `warps`.`warp_info` (`ID`) " +
                    "ON DELETE NO ACTION " +
                    "ON UPDATE NO ACTION) " +
                    "ENGINE = InnoDB;"
            ).executeUpdate();

            /*
            -- -----------------------------------------------------
            -- Table `warps`.`warps_has_warp_locations`
            -- -----------------------------------------------------
             */
            con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS `warps`.`warps_has_warp_locations` ( " +
                            "`warps_id` INT NOT NULL, " +
                            "`warp_locations_ID` INT NOT NULL, " +
                            "PRIMARY KEY (`warps_id`, `warp_locations_ID`), " +
                            "INDEX `fk_warps_has_warp_locations_warp_locations1_idx` (`warp_locations_ID` ASC) VISIBLE, " +
                            "INDEX `fk_warps_has_warp_locations_warps1_idx` (`warps_id` ASC) VISIBLE, " +
                            "CONSTRAINT `fk_warps_has_warp_locations_warps1` " +
                            "FOREIGN KEY (`warps_id`) " +
                            "REFERENCES `warps`.`warps` (`id`) " +
                            "ON DELETE NO ACTION " +
                            "ON UPDATE NO ACTION, " +
                            "CONSTRAINT `fk_warps_has_warp_locations_warp_locations1` " +
                            "FOREIGN KEY (`warp_locations_ID`) " +
                            "REFERENCES `warps`.`warp_locations` (`ID`) " +
                            "ON DELETE NO ACTION " +
                            "ON UPDATE NO ACTION) " +
                            "ENGINE = InnoDB;"
            ).executeUpdate();

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * pulls data from database and loads them into hashmap
     */
    private static void loadWarps() {
        Connection con = Core.gCore.connection.getConn();
        try {

            ResultSet set = con.prepareStatement(
                    "SELECT * FROM warps " +
                            "INNER JOIN warp_locations ON warps.warps.id = warp_locations.ID " +
                            "INNER JOIN warp_info ON warps.warps.id = warp_info.ID;"
            ).executeQuery();

            while (set.next()) {

                int id = set.getInt("id");
                if (Core.gCore.warpStorage.nextID <= id) Core.gCore.warpStorage.nextID = id + 1;

                Warp warp = new Warp(
                        id,
                        machType(set.getInt("type")),
                        set.getString("owner"),
                        set.getString("name"),
                        Bukkit.getWorld(set.getString("world")),
                        set.getDouble("x"), set.getDouble("y"), set.getDouble("z"),
                        set.getFloat("yaw"), set.getFloat("pitch")
                );

                warp.setDescription(set.getString("description"));
                warp.setCategory(machCategory(set.getInt("category")));

                boolean access = true;
                if (set.getInt("access") == 0) access = false;
                warp.setAccessibility(access);

                Core.gCore.warpStorage.addWarp(warp);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Identifies type of the warp
     * @param i id of the warp
     * @return Enum of warp type
     */
    private static Warp.Type machType(int i) {
        for (Warp.Type type : Warp.Type.values()) {
            if (type.id == i) return type;
        }
        return Warp.Type.PLAYER;
    }

    /**
     * Identifies type of the warp category
     * @param i id of the category
     * @return Enum of warp category
     */
    private static Warp.Category machCategory(int i) {
        for (Warp.Category category : Warp.Category.values()) {
            if (category.id == i) return category;
        }
        return Warp.Category.UNDEFINED;
    }

}
