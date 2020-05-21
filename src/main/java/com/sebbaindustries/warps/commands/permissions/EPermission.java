package com.sebbaindustries.warps.commands.permissions;

/**
 * <b>This class contains enums for permissions</b><br>
 * @author sebbaindustries
 * @version 1.0
 */
public enum EPermission {

    /*
    TIER 0 - Access to everything
     */
    ROOT("warps.*"),

    /*
    TIER 1 - Access to groups of commands and features
     */
    COMMANDS("warps.commands.*"),

    /*
    TIER 2 - Access to specific command or feature
     */
    CREATE("warps.commands.set"), // /warp create
    DELETE("warps.commands.delete"), // /warp delete
    MOVE("warps.command.move"), // /warp move
    TELEPORT("warps.command.teleport"),
    MENU("warps.command.menu"),
    RATE("warps.command.rate"),
    DESCRIPTION("warps.command.description"),
    LIST("warps.command.list"),
    LIST_OTHERS("warps.command.list.others"),
    CATEGORY("warps.command.category"),
    ;

    public String permission;

    EPermission(String permission) {
        this.permission = permission;
    }
}
