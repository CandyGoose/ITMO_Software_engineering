package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

import java.util.ArrayList;

/**
 * Выводит уникальные значения поля employeesCount организаций в системе в порядке убывания
 */
public class PrintUniqueEmployeesCountCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса PrintUniqueEmployeesCountCommand.
     * @param collectionManager менеджер коллекции.
     */
    public PrintUniqueEmployeesCountCommand(CollectionManager collectionManager) {
        super("print_unique_employees_count", "вывести уникальные значения поля employeesCount всех элементов в коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Выводит все уникальные поля employeesCount организаций в системе
     *
     * @param argument строка аргументов команды
     * @return true, если выполнение команды прошло успешно, и false, если произошла ошибка.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            ArrayList<Long> employeesCount = new ArrayList<Long>(collectionManager.getSetEmployeesCount()) {
            };
            if (employeesCount.isEmpty()) Console.printLn("Нет значений.");
            else Console.printLn("Уникальные значения поля employeesCount: " + employeesCount.toString().replaceAll("^\\[|\\]$", ""));
            return true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("Использование аргумента '" + argument + "' в команде '" + getName() + "'");
        }
        return false;
    }
}
