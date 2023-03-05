package commands;

import exceptions.WrongAmountOfElementsException;
import managers.Console;

/**
 * Этот класс используется для вывода списка последних команд
 */
public class HistoryCommand extends AbstractCommand{

    public HistoryCommand() {
        super("history", "показать список последних команд");
    }

    /**
     * Выводит информацию об использовании команды
     * 
     * @param argument аргумент, переданный команде
     * @return boolean type
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
        } catch (WrongAmountOfElementsException exception) {
            Console.printLn("Использование '" + getName() + "'");
        }
        return false;
    }
}
