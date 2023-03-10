package commands;

import exceptions.WrongAmountOfElementsException;
import managers.Console;

/**
 * Класс "HelpCommand" отображает справку по доступным командам
 */
public class HelpCommand extends AbstractCommand {

    /**
     * Конструктор класса.
     * Создает новый объект команды с указанием имени и описания.
     */
    public HelpCommand() {
        super("help", "вывести справку по доступным командам");
    }

    /**
     * Выполняет команду "help", выводящую список доступных команд и их описания.
     * @param argument аргумент команды (в данном случае не используется)
     * @return всегда возвращает false
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
        } catch (WrongAmountOfElementsException exception) {
            Console.printError("Использование аргумента '" + argument + "' в команде '" + getName() + "'");
        }
        return false;
    }
}
