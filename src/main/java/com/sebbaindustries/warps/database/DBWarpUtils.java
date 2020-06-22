package com.sebbaindustries.warps.database;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.warp.Warp;

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
                            "" + warp.getLocation().getX() + ", " +
                            "" + warp.getLocation().getPitch() + ", " +
                            "" + warp.getLocation().getYaw() + ");"
            ).executeUpdate();

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
