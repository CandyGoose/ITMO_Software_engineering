package Server.util;

import Common.util.Request;
import Server.Commands.AbstractCommand;

import java.util.HashMap;
import java.util.Map;


public class CommandManager {

    public static final Map<String, AbstractCommand> AVAILABLE_COMMANDS = new HashMap<>();

    public CommandManager(AbstractCommand addCommand, AbstractCommand clearCommand, AbstractCommand executeScriptCommand, AbstractCommand exitCommand, AbstractCommand helpCommand, AbstractCommand infoCommand, AbstractCommand printDescendingCommand, AbstractCommand printFieldDescendingAnnualTurnoverCommand, AbstractCommand printUniqueEmployeesCountCommand, AbstractCommand removeByIdCommand, AbstractCommand removeFirstCommand, AbstractCommand showCommand, AbstractCommand shuffleCommand, AbstractCommand sortCommand, AbstractCommand updateByIdCommand) {
        AVAILABLE_COMMANDS.put(addCommand.getName(), addCommand);
        AVAILABLE_COMMANDS.put(clearCommand.getName(), clearCommand);
        AVAILABLE_COMMANDS.put(executeScriptCommand.getName(), executeScriptCommand);
        AVAILABLE_COMMANDS.put(exitCommand.getName(), exitCommand);
        AVAILABLE_COMMANDS.put(helpCommand.getName(), helpCommand);
        AVAILABLE_COMMANDS.put(infoCommand.getName(), infoCommand);
        AVAILABLE_COMMANDS.put(printDescendingCommand.getName(), printDescendingCommand);
        AVAILABLE_COMMANDS.put(printFieldDescendingAnnualTurnoverCommand.getName(), printFieldDescendingAnnualTurnoverCommand);
        AVAILABLE_COMMANDS.put(printUniqueEmployeesCountCommand.getName(), printUniqueEmployeesCountCommand);AVAILABLE_COMMANDS.put(showCommand.getName(), showCommand);
        AVAILABLE_COMMANDS.put(removeByIdCommand.getName(), removeByIdCommand);
        AVAILABLE_COMMANDS.put(removeFirstCommand.getName(), removeFirstCommand);
        AVAILABLE_COMMANDS.put(shuffleCommand.getName(), shuffleCommand);
        AVAILABLE_COMMANDS.put(sortCommand.getName(), sortCommand);
        AVAILABLE_COMMANDS.put(updateByIdCommand.getName(), updateByIdCommand);
    }

    public AbstractCommand initCommand(Request request) {
        String commandName = request.getCommandName();
        return AVAILABLE_COMMANDS.get(commandName);
    }
}

