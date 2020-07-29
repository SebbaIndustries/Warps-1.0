package com.sebbaindustries.warps.database;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.warp.Warp;
import com.sebbaindustries.warps.warp.components.WarpLocation;

import java.sql.Connection;
import java.sql.SQLException;

public class DBWarpUtils {

    private static Connection openConn() {
        return Core.gCore.connection.getConn();
    }

    public static void createNewWarp(Warp warp) {
        Connection con = openConn();
        try {
            con.prepareStatement(
                    "INSERT INTO warps (id, owner, name) VALUES (" + warp.getID() + ", '" + warp.getOwner() + "', '" + warp.getName() + "');"
            ).executeUpdate();

            con.prepareStatement(
                    "INSERT INTO warp_info (ID, type, category, description, access) VALUES " +
                            "(" + warp.getID() + ", " + warp.getType().id + ", " + warp.getCategory().id + ", " +
                            "'" + warp.getDescription() + "', " + warp.getAccessibility() + ");"
            ).executeUpdate();

            con.prepareStatement(
                    "INSERT INTO warp_locations (ID, world, x, y, z, pitch, yaw) VALUES " +
                            "(" + warp.getID() + ", '" + warp.getLocation().getWorld().getName() + "', " +
                            "" + warp.getLocation().getX() + ", " +
                            "" + warp.getLocation().getY() + ", " +
                            "" + warp.getLocation().getZ() + ", " +
                            "" + warp.getLocation().getPitch() + ", " +
                            "" + warp.getLocation().getYaw() + ");"
            ).executeUpdate();

            con.prepareStatement(
                    "INSERT INTO warp_visits (ID, visits_json) VALUES (" + warp.getID() + ", NULL);"
            ).executeUpdate();

            con.prepareStatement(
                    "INSERT INTO warps_has_warp_info (warps_id, warp_info_ID) VALUES (" + warp.getID() + ", " + warp.getID() + ");"
            ).executeUpdate();

            con.prepareStatement(
                    "INSERT INTO warps_has_warp_locations (warps_id, warp_locations_ID) VALUES (" + warp.getID() + ", " + warp.getID() + ");"
            ).executeUpdate();

            con.prepareStatement(
                    "INSERT INTO warps_has_warp_visits (warps_id, warp_visits_ID) VALUES (" + warp.getID() + ", " + warp.getID() + ");"
            ).executeUpdate();

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteWarp(Warp warp) {
        Connection con = openConn();
        try {
            con.prepareStatement(
                    "DELETE FROM warps_has_warp_info WHERE warps_id = " +  warp.getID() + ";"
            ).executeUpdate();

            con.prepareStatement(
                    "DELETE FROM warps_has_warp_locations WHERE warps_id = " +  warp.getID() + ";"
            ).executeUpdate();

            con.prepareStatement(
                    "DELETE FROM warps_has_warp_visits WHERE warps_id = " +  warp.getID() + ";"
            ).executeUpdate();

            con.prepareStatement(
                    "DELETE FROM warps WHERE id = " +  warp.getID() + ";"
            ).executeUpdate();

            con.prepareStatement(
                    "DELETE FROM warp_info WHERE id = " +  warp.getID() + ";"
            ).executeUpdate();

            con.prepareStatement(
                    "DELETE FROM warp_locations WHERE id = " +  warp.getID() + ";"
            ).executeUpdate();

            con.prepareStatement(
                    "DELETE FROM warp_visits WHERE id = " +  warp.getID() + ";"
            ).executeUpdate();

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void moveWarp(Warp warp, WarpLocation loc) {
        Connection con = openConn();
        try {
            con.prepareStatement(
                    "UPDATE warp_locations SET " +
                            "x=" + loc.getX() + ", " +
                            "y=" + loc.getY() + ", " +
                            "z=" + loc.getZ() + ", " +
                            "yaw=" + loc.getYaw() + ", " +
                            "pitch=" + loc.getPitch() + ", " +
                            "world='" + loc.getWorld().getName() + "' " +
                            "WHERE ID=" + warp.getID() + ";"
            ).executeUpdate();

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateDescription(Warp warp) {
        Connection con = openConn();
        try {
            con.prepareStatement(
                    "UPDATE warp_info set description='" + warp.getDescription() + "' WHERE ID= " + warp.getID() + ";"
            ).executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateCategory(Warp warp) {
        Connection con = openConn();
        try {
            con.prepareStatement(
                    "UPDATE warp_info set category='" + warp.getCategory().id + "' WHERE ID= " + warp.getID() + ";"
            ).executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateVisits(Warp warp) {
        Connection con = openConn();
        try {
            con.prepareStatement(
                    "UPDATE warp_visits SET visits_json='" + warp.getVisitData().getJSON().toString() + "' WHERE ID=" + warp.getID() + ";"
            ).executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void syncVisits() {
        Connection con = openConn();
        try {
            for (Warp warp : Core.gCore.warpStorage.getWarpHashMap().values()) {
                con.prepareStatement(
                        "UPDATE warp_visits SET visits_json='" + warp.getVisitData().getJSON().toString() + "' WHERE ID=" + warp.getID() + ";"
                ).executeUpdate();
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
