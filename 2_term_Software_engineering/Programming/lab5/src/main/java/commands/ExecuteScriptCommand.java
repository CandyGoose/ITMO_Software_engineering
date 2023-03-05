package commands;

import exceptions.WrongAmountOfElementsException;
import managers.Console;

/**
 * Выполняет скрипт
 */
public class ExecuteScriptCommand extends AbstractCommand {

    public ExecuteScriptCommand() {
        super("execute_script file_name", "считать и исполнить скрипт из указанного файла (в скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме)");
    }

    /**
     * Выполняет скрипт
     * 
     * @param argument аргумент, переданный команде
     * @return boolean type
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (argument.isEmpty()) throw new WrongAmountOfElementsException();
            Console.printLn("Выполнение скрипта '" + argument + "'...");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.printLn("Использование '" + getName() + "'");
        }
        return false;
    }
}
