package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

import java.util.ArrayList;

/**
 * Выводит уникальные значения поля employeesCount организаций в системе в порядке убывания
 */
public class PrintUniqueEmployeesCountCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    public PrintUniqueEmployeesCountCommand(CollectionManager collectionManager) {
        super("print_unique_employees_count","", "вывести уникальные значения поля employeesCount всех элементов в коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            ArrayList<Long> employeesCount = new ArrayList<>(collectionManager.getSetEmployeesCount()) {
            };
            StringBuilder output = new StringBuilder();
            if (employeesCount.isEmpty()) output.append("Нет значений.");
            else output.append("Уникальные значения поля employeesCount: " + employeesCount.toString().replaceAll("^\\[|\\]$", ""));

            ResponseOutputer.appendLn(output);
            return true;
            } catch (WrongAmountOfElementsException exception) {
                ResponseOutputer.appendLn("Применение: '" + getName() + " " + getUsage() + "'");
            }
            return false;
        }
    }
