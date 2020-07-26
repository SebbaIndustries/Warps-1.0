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
    TIER 2 - Category permissions
     */
    SHOP("warps.commands.category.shop"),
    PVP("warps.commands.category.pvp"),
    MINIGAME("warps.commands.category.minigame"),
    OTHER("warps.commands.category.other"),
    REALESTATE("warps.commands.category.realestate"),


    /*
    TIER 2 - Access to specific command or feature
     */
    CREATE("warps.commands.set"), // perm za kreacijo lastnih warpow
    DELETE("warps.commands.delete"), // perm za brisanje lastnih warpow
    MOVE("warps.command.move"), // perm za premikanje lastnih warpow
    TELEPORT("warps.command.teleport"), // perm za teleportiranje do warpow
    MENU("warps.command.menu"), // gui perm
    MODIFY("warps.command.modify"), // warp description
    LIST("warps.command.list"), // list za tvoje warpe (chat)
    LIST_OTHERS("warps.command.list.others"), // list za vse warpe (chat)
    CATEGORY("warps.command.category"), // list za kategorijo
    ;

    public String permission;

    EPermission(String permission) {
        this.permission = permission;
    }
}
