package commands;

import exceptions.WrongAmountOfElementsException;
import managers.Console;

/**
 * Этот класс используется для вывода списка последних команд
 */
public class HistoryCommand extends AbstractCommand{

    /**
     * Конструктор класса.
     * Создает новый объект команды с указанием имени и описания.
     */
    public HistoryCommand() {
        super("history", "показать список последних команд");
    }

    /**
     * Реализация метода execute интерфейса ICommand.
     * @param argument аргумент, который необходимо проверить на пустоту.
     * @return значение типа boolean. Если аргумент не пустой, возвращает false.
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
