package commands;

import exceptions.WrongAmountOfElementsException;
import managers.Console;

/**
 * Класс `Exit Command` завершает работу программы
 */
public class ExitCommand extends AbstractCommand {
    public ExitCommand() {
        super("exit", "завершить программу (без сохранения в файл)");
    }

    /**
     * Выводит сообщение на консоль и завершает работу программы
     * 
     * @param argument аргумент, переданный команде
     * @return boolean type
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            Console.printLn("До свидания!");
            System.exit(0);
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.printLn("Использование '" + getName() + "'");
        }
        return false;
    }
}
