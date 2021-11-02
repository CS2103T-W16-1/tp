package donnafin.commons.core;

import donnafin.logic.commands.HelpCommand;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_INDEX = "The client index provided is invalid!";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_USE_HELP_COMMAND = String.format(
            "Invalid command format! Try using the help command. \n%1$s", HelpCommand.MESSAGE_USAGE);

}
