package com.sebbaindustries.warps.commands.creator;

/**
 * <b>This class contains command permission exception handling</b><br>
 * @author sebbaindustries
 * @version 1.0
 */
public class NoPermissionSetCommandException extends RuntimeException {

    /**
     * Exception constructor, returns with message that show up in the console
     */
    public NoPermissionSetCommandException() {
        super("There is no permission present in the command, fix this shit! -Not Nzd_1");
    }

}
