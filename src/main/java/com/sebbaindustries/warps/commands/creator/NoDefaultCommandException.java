package com.sebbaindustries.warps.commands.creator;

/**
 * <b>This class contains command handling exception for missing default command</b><br>
 * @author sebbaindustries
 * @version 1.0
 */
public final class NoDefaultCommandException extends RuntimeException{

    /**
     * Exception constructor, returns with message that show up in the console
     */
    public NoDefaultCommandException() {
        super("There is no default command present in the plugin, fix this shit! -Not Nzd_1");
    }

}
