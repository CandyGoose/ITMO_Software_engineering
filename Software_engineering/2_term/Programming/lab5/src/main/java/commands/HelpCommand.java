package commands;

import exceptions.WrongAmountOfElementsException;
import managers.Console;

/**
 * Класс `Help Command` отображает справку по доступным командам
 */
public class HelpCommand extends AbstractCommand {

    public HelpCommand() {
        super("help", "вывести справку по доступным командам");
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
