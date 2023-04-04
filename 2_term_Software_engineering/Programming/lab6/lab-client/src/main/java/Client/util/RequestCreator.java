package Client.util;

import Client.CommandDispatcher.CommandToSend;
import Client.CommandDispatcher.CommandValidators;
import Client.CommandDispatcher.AvailableCommands;
import Client.util.ScannerManager;
import Common.exception.IncorrectInputInScriptException;
import Common.exception.WrongAmountOfArgumentsException;
import Common.exception.WrongArgException;
import Common.util.Request;
import Common.util.TextWriter;

import java.util.Scanner;

public class RequestCreator {

    private Scanner scanner = new Scanner(System.in);
    private ScannerManager scannerManager = new ScannerManager(scanner);

    public Request createRequestOfCommand(CommandToSend command) throws NullPointerException {
        String name = command.getCommandName();
        Request request;
        if (AvailableCommands.COMMANDS_WITHOUT_ARGS.contains(name)) {
            request = createRequestWithoutArgs(command);
        } else if (AvailableCommands.COMMANDS_WITH_ID_ARG.contains(name)) {
            request = createRequestWithID(command);
        } else if (AvailableCommands.COMMANDS_WITH_ORGANIZATION_ARG.contains(name)) {
            request = createRequestWithOrganization(command);
        } else if (AvailableCommands.COMMANDS_WITH_ORGANIZATION_ID_ARGS.contains(name)) {
            request = createRequestWithOrganizationID(command);
        } else if (AvailableCommands.SCRIPT_ARGUMENT_COMMAND.contains(name)) {
            request = createRequestWithOrganizationID(command);
        } else {
            throw new NullPointerException("Команда не найдена. Напишите 'help' для просмотра всех доступных команд.");
        }
        return request;
    }

    private Request createRequestWithoutArgs(CommandToSend command) {
        try {
            CommandValidators.validateAmountOfArgs(command.getCommandArgs(), 0);
            return new Request(command.getCommandName());
        } catch (WrongAmountOfArgumentsException e) {
            TextWriter.printErr(e.getMessage());
            return null;
        }
    }

    private Request createRequestWithID(CommandToSend command) {
        try {
            CommandValidators.validateAmountOfArgs(command.getCommandArgs(), 1);
            long id = CommandValidators.validateArg(arg -> ((long) arg) > 0,
                    "ID должен быть больше 0",
                    Long::parseLong,
                    command.getCommandArgs()[0]);
            return new Request(command.getCommandName(), id);
        } catch (WrongAmountOfArgumentsException | WrongArgException e) {
            TextWriter.printErr(e.getMessage());
            return null;
        } catch (IllegalArgumentException e) {
            TextWriter.printErr("Неверный тип данных аргумента.");
            return null;
        }
    }

    private Request createRequestWithOrganization(CommandToSend command) {
        try {
            CommandValidators.validateAmountOfArgs(command.getCommandArgs(), 0);
            return new Request(command.getCommandName(), scannerManager.askOrganization());
        } catch (WrongAmountOfArgumentsException | IncorrectInputInScriptException e) {
            TextWriter.printErr(e.getMessage());
            return null;
        }
    }

    private Request createRequestWithOrganizationID(CommandToSend command) {
        try {
            CommandValidators.validateAmountOfArgs(command.getCommandArgs(), 1);
            long id = CommandValidators.validateArg(arg -> ((long) arg) > 0,
                    "ID должен быть больше 0",
                    Long::parseLong,
                    command.getCommandArgs()[0]);
            return new Request(command.getCommandName(), id, scannerManager.askOrganization());
        } catch (WrongAmountOfArgumentsException | WrongArgException | IllegalArgumentException | IncorrectInputInScriptException e) {
            TextWriter.printErr(e.getMessage());
            return null;
        }
    }
}
