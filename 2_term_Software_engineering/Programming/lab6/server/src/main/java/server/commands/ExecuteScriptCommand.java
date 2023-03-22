package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.ResponseOutputer;

/**
 * Выполняет скрипт
 */
public class ExecuteScriptCommand extends AbstractCommand {
    /**
     * Конструктор класса ExecuteScriptCommand.
     */
    public ExecuteScriptCommand() {
        super("execute_script", "<file_name>", "считать и исполнить скрипт из указанного файла (в скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме)");
    }

    /**
     * Команда исполнения скрипта.
     * @param stringArgument аргумент команды.
     * @param objectArgument аргумент команды.
     * @return true, если команда выполнена успешно, false в противном случае.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            ResponseOutputer.appendLn("Выполнение скрипта '" + stringArgument + "'...");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendLn("Применение: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
