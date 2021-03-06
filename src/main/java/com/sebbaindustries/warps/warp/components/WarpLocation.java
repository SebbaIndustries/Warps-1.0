package com.sebbaindustries.warps.warp.components;

import org.bukkit.Bukkit;
import org.bukkit.World;

/**
 * <b>This class contains warp location and access to it</b><br>
 *
 * @author sebbaindustries
 * @version 1.0
 */
public class WarpLocation {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private World world;

    /**
     * Warp location constructor, sets all variables
     *
     * @param world world
     * @param x     X coordinate
     * @param y     Y coordinate
     * @param z     Z coordinate
     * @param yaw   Yaw coordinate
     * @param pitch Pitch coordinate
     */
    public WarpLocation(final World world, final double x, final double y, final double z, final float yaw, final float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.world = world;
    }

    /**
     * @return World of the warp
     */
    public World getWorld() {
        return world;
    }

    /**
     * Changes the world of the warp
     */
    public void setWorld(World world) {
        this.world = world;
    }

    /**
     * @return X coordinate of the warp
     */
    public double getX() {
        return x;
    }

    /**
     * Changes X coordinate of the warp
     *
     * @param x X Coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return Y coordinate of the warp
     */
    public double getY() {
        return y;
    }

    /**
     * Changes Y coordinate of the warp
     *
     * @param y Y Coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return Z coordinate of the warp
     */
    public double getZ() {
        return z;
    }

    /**
     * Changes Z coordinate of the warp
     *
     * @param z Z Coordinate
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * @return Yaw coordinate of the warp
     */
    public float getYaw() {
        return yaw;
    }

    /**
     * Changes Yaw coordinate of the warp
     *
     * @param yaw Yaw Coordinate
     */
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    /**
     * @return Pitch coordinate of the warp
     */
    public float getPitch() {
        return pitch;
    }

    /**
     * Changes Pitch coordinate of the warp
     *
     * @param pitch Pitch Coordinate
     */
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

}
