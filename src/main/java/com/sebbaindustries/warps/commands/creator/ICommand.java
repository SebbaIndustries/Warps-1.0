package com.sebbaindustries.warps.commands.creator;

import com.sebbaindustries.warps.commands.permissions.EPermission;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <b>This class contains command argument datatype stricture for easier implementation</b><br>
 * @author sebbaindustries
 * @version 1.0
 */
public abstract class ICommand {

    private final String argument;
    private final String usage;
    private Integer minArgs;
    private Integer maxArgs;
    private boolean def = false;
    private boolean playerOnly = false;
    private boolean placeholders = false;
    private final Permissions permissions = new Permissions();

    /**
     * Constructor that creates ICommand instance with all necessary arguments
     * @param argument /command ->ARGUMENT<-
     * @param usage Message sent to the player if he fucks up :D
     * @param minArgs Minimum args, this can be null if you don't want to use them
     */
    protected ICommand(final @NotNull String argument, final @NotNull String usage, final @Nullable Integer minArgs) {
        this.argument = argument;
        this.usage = usage;
        this.minArgs = minArgs;
        this.maxArgs = argument.split(" ").length;
    }

    /**
     * @see Permissions
     * @return permissions class and access to the permissions
     */
    public Permissions permissions() {
        return permissions;
    }

    /**
     * @return Argument for the command
     */
    public String getArgument() {
        return argument;
    }

    /**
     * @return Usage message for the command, use if player fucks up
     */
    public String getUsage() {
        return usage;
    }

    /**
     * @return Minimum arguments necessary for command to work
     */
    public Integer getMinArgs() {
        return minArgs;
    }

    /**
     * @return Maximum arguments for command to work, idk if this will be used. Seems stupid :/
     */
    public Integer getMaxArgs() {
        return maxArgs;
    }

    /**
     * Changes maximum amount of arguments, idk if this will be used. Seems stupid :/
     * @param maxArgs Maximum amount of arguments
     */
    public void setMaxArgs(Integer maxArgs) {
        this.maxArgs = maxArgs;
    }

    /**
     * @return True if command is a default one, false by default
     */
    public boolean isDef() {
        return def;
    }

    /**
     * Changes if command is default or not
     */
    public void setDef() {
        this.def = true;
    }

    /**
     * Changes whether or not the command contains %s placeholders
     */
    public void setPlaceholders() { this.placeholders = true; }

    /**
     * @return true if command contains placeholders
     */
    public boolean getPlaceholders() { return placeholders; }

    /**
     * // TODO add something like this for consoles, ask frosty, maybe for sql stuff?
     * @return If only players can use the command
     */
    public boolean isPlayerOnly() {
        return playerOnly;
    }

    /**
     * Changes if console can execute command
     */
    public void setPlayerOnly() {
        this.playerOnly = true;
    }

    /**
     * Abstract for executing commands
     * @param sender Player or console instance
     * @param args command arguments
     */
    public abstract void execute(final @NotNull CommandSender sender, final String[] args);

    /**
     * @author sebbaindustries
     * @version 1.0
     */
    protected static class Permissions {

        private List<EPermission> permissions = new ArrayList<>();

        /**
         * @return List of all permission command has
         */
        public List<EPermission> getPermissions() {
            return permissions;
        }

        /**
         * Adds permission to the list <br>
         * @see EPermission
         * @param permissions EPermission enums
         */
        public void add(EPermission... permissions) {
            this.permissions.addAll(Arrays.asList(permissions));
        }
    }

}
